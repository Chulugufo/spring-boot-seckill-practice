package com.example.seckillshop.service;

import com.example.seckillshop.entity.Goods;
import java.util.List;

public interface GoodsService {
    List<Goods> findAll();
    Goods findById(Long id);
}