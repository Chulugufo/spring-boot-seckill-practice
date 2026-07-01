# 秒杀系统练习
主要程序在 /src/main/java/com/example/seckillshop下

## 技术栈
Spring Boot + MyBatis + MySQL + Redis + RocketMQ

## 功能
- 商品列表查询（Redis 缓存）
- 秒杀下单（分布式锁）
- RocketMQ 消息发送与消费
- AOP 日志切面
- 登录拦截器

## 接口
| GET /goods/list | 商品列表 |

| GET /goods/{id} | 商品详情 |

| POST /seckill/order?goodsId=1&userId=1001 | 秒杀下单 |

| GET /hello | 健康检查 |

## 启动
1. 启动 MySQL、Redis、RocketMQ
2. 启动项目
3. 访问 http://localhost:8888/hello
