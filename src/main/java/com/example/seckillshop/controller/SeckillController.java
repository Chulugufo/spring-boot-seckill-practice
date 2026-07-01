package com.example.seckillshop.controller;

import com.example.seckillshop.entity.Orders;
import com.example.seckillshop.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/order")
    public String createOrder(@RequestParam Long goodsId, @RequestParam Long userId) {
        try {
            Orders order = seckillService.seckill(goodsId, userId);
            return "秒杀成功！订单ID: " + order.getId();
        } catch (RuntimeException e) {
            return "秒杀失败：" + e.getMessage();
        }
    }
}