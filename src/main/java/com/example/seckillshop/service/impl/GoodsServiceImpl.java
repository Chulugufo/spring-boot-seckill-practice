package com.example.seckillshop.service.impl;

import com.example.seckillshop.entity.Goods;
import com.example.seckillshop.mapper.GoodsMapper;
import com.example.seckillshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String GOODS_LIST_KEY = "goods:list";
    private static final String GOODS_DETAIL_KEY = "goods:detail:";

    @Override
    public List<Goods> findAll() {
        // 1.先从 Redis 中取
        Object cache = redisTemplate.opsForValue().get(GOODS_LIST_KEY);
        if (cache instanceof List) {
            return (List<Goods>) cache;
        }

        // 2.缓存中没有，从数据库查
        List<Goods> goodsList = goodsMapper.findAll();

        // 3.查到后存入Redis，有效期60秒
        redisTemplate.opsForValue().set(GOODS_LIST_KEY, goodsList, 60, TimeUnit.SECONDS);

        return goodsList;
    }

    @Override
    public Goods findById(Long id) {
        // 1.先从Redis中取
        String key = GOODS_DETAIL_KEY + id;
        Object cache = redisTemplate.opsForValue().get(key);
        if (cache instanceof Goods) {
            return (Goods) cache;
        }

        // 2.缓存中没有，从数据库查
        Goods goods = goodsMapper.findById(id);

        // 3.查到后存入Redis，有效期60秒
        if (goods != null) {
            redisTemplate.opsForValue().set(key, goods, 60, TimeUnit.SECONDS);
        }

        return goods;
    }
}