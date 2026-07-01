package com.example.seckillshop.controller;

import com.example.seckillshop.entity.Goods;
import com.example.seckillshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public List<Goods> list() {
        return goodsService.findAll();
    }

    @GetMapping("/{id}")
    public Goods detail(@PathVariable Long id) {
        return goodsService.findById(id);
    }
}