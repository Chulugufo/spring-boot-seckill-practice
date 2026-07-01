package com.example.seckillshop.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "order-topic", consumerGroup = "order-consumer-group")
public class OrderConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("=== 收到 RocketMQ 消息: " + message);
    }
}