package com.example.seckillshop.mq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendOrderMessage(Long orderId, Long goodsId, Long userId) {
        String message = "订单ID: " + orderId + ", 商品ID: " + goodsId + ", 用户ID: " + userId;
        rocketMQTemplate.convertAndSend("order-topic", message);
        System.out.println("=== 已发送消息到 RocketMQ: " + message);
    }
}