package com.example.seckillshop.service;

import com.example.seckillshop.entity.Goods;
import com.example.seckillshop.entity.Orders;
import com.example.seckillshop.mapper.GoodsMapper;
import com.example.seckillshop.mapper.OrdersMapper;
import com.example.seckillshop.mq.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;

@Service
public class SeckillService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderProducer orderProducer;

    // 秒杀下单的方法，用了事务保证数据一致性
    @Transactional
    public Orders seckill(Long goodsId, Long userId) {
        // 用 Redis 做分布式锁，防止多个用户同时抢同一个商品
        // key 是 lock:seckill:商品ID，value 是用户ID
        String lockKey = "lock:seckill:" + goodsId;
        String lockValue = userId.toString();

        // setIfAbsent：如果 key 不存在就设置，存在就返回 false
        // 设置过期时间 5 秒，防止死锁
        Boolean locked = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, lockValue, 5, TimeUnit.SECONDS);

        // 如果没拿到锁，说明有人在抢，提示用户稍后再试
        if (!Boolean.TRUE.equals(locked)) {
            throw new RuntimeException("系统繁忙，请稍后再试");
        }

        try {
            // 先查一下商品存不存在
            Goods goods = goodsMapper.findById(goodsId);
            if (goods == null) {
                throw new RuntimeException("商品不存在");
            }

            // 减库存
            int result = goodsMapper.reduceStock(goodsId);
            if (result == 0) {
                throw new RuntimeException("库存不足");
            }

            // 创建订单，状态为 0 表示待支付
            Orders order = new Orders();
            order.setUserId(userId);
            order.setGoodsId(goodsId);
            order.setQuantity(1);
            order.setTotalPrice(goods.getPrice());
            order.setStatus(0);
            ordersMapper.insert(order);

            // 下单成功后发个消息到 RocketMQ，通知其他系统
            // 打印日志
            orderProducer.sendOrderMessage(order.getId(), goodsId, userId);

            return order;
        } finally {
            // 释放锁的时候要判断是不是自己加的锁
            Object owner = redisTemplate.opsForValue().get(lockKey);
            if (lockValue.equals(owner)) {
                redisTemplate.delete(lockKey);
            }
        }
    }
}