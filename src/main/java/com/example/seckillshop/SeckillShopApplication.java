package com.example.seckillshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.seckillshop.mapper")
public class SeckillShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillShopApplication.class, args);
    }
}