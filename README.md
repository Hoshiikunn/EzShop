# EzShop 简易商店

### 介绍
由 Hoshiikunn (星井ほしいくん) 开发的 “简易商店” <b>个人练手</b>项目 <br>
是以 “后台管理系统” 和 “前台系统” 组合的单体项目 <br>
后台管理系统主要由公司员工操作商品上下架，商品发货，处理退货申请，统计等 <br>
前台系统主要由顾客操作浏览商品，下单支付商品，评价商品等 <br>

> 当前状态：**脚手架阶段**（技术选型已定稿，业务代码开发中）

### 技术栈

基础环境：JDK 8 · Maven 3.6+ · Spring Boot **2.7.18**（JDK 8 的最终稳定版）

| 领域 | 选型 | 版本 |
|---|---|---|
| Web 框架 | spring-boot-starter-web | 2.7.18 |
| 单元测试 | spring-boot-starter-test | 2.7.18 |
| 编程增强 | lombok | 随 parent |
| 参数校验 | spring-boot-starter-validation | 随 parent |
| ORM | mybatis-plus-boot-starter + mybatis-plus-jsqlparser | 3.5.17 |
| 数据库驱动 | mysql-connector-j | 随 parent |
| 连接池 | druid-spring-boot-starter | 1.2.28 |
| 缓存 | spring-boot-starter-data-redis | 随 parent |
| 接口文档 | knife4j-openapi3-**spring**-boot-starter | 4.5.0 |
| 配置提示 | spring-boot-configuration-processor | 随 parent |
| 富文本净化 | jsoup | 1.23.2 |
| IP 归属地 | ip2region | 3.3.7 |
| 文件存储 | 云对象存储 OSS（阿里云 / 腾讯云，渠道待定） | 待定 |
| 认证授权 | Apache Shiro（shiro-spring-boot-web-starter） | 1.13.0 |

### 贡献者

| 成员 | 角色 | 主页 |
|---|---|---|
| **Hoshiikunn**（星井ほしいくん） | 项目发起人 / 主要开发 | [GitHub @Hoshiikunn](https://github.com/Hoshiikunn) |
