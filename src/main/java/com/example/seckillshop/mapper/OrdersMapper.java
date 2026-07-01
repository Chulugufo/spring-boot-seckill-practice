package com.example.seckillshop.mapper;

import com.example.seckillshop.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface OrdersMapper {

    @Insert("INSERT INTO orders(user_id, goods_id, quantity, total_price, status) VALUES(#{userId}, #{goodsId}, #{quantity}, #{totalPrice}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Orders order);
}