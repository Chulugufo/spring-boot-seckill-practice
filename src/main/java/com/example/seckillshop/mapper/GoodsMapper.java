package com.example.seckillshop.mapper;

import com.example.seckillshop.entity.Goods;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

public interface GoodsMapper {

    @Select("SELECT * FROM goods")
    List<Goods> findAll();

    @Select("SELECT * FROM goods WHERE id = #{id}")
    Goods findById(Long id);

    @Update("UPDATE goods SET stock = stock - 1 WHERE id = #{goodsId} AND stock > 0")
    int reduceStock(Long goodsId);
}