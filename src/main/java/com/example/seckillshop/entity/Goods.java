package com.example.seckillshop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Goods {
    private Long id;
    private String name;
    private Integer stock;
    private BigDecimal price;
    private LocalDateTime createTime;

    // getter 和 setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}