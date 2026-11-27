# EzShop 简易商店

### 介绍

由 Hoshiikunn (星井ほしいくん) 开发的 “简易商店” **个人练手**项目。
项目由 **后台管理系统** 和 **前台系统** 组成。

> **当前状态**：v0.0.7 已完成。本版重点开发审计日志，完成登录日志与操作日志闭环。
>
> **新计划**：计划开发 云OSS 文件上传 (v0.0.8)。

### 仓库

- 后端仓库：[Hoshiikunn/EzShop](https://github.com/Hoshiikunn/EzShop)
- 前端仓库：[Hoshiikunn/EzShop-web](https://github.com/Hoshiikunn/EzShop-web)

### 技术栈

基础环境：JDK 8 · Maven 3.6+ · Spring Boot **2.7.18** (JDK 8 的最终稳定版)

| 领域       | 选型                                                                     | 版本      |
|------------|--------------------------------------------------------------------------|-----------|
| Web 框架   | spring-boot-starter-web                                                  | 2.7.18    |
| 单元测试   | spring-boot-starter-test                                                 | 2.7.18    |
| 编程增强   | lombok                                                                   | 随 parent |
| 参数校验   | spring-boot-starter-validation                                           | 随 parent |
| ORM        | mybatis-plus-boot-starter + mybatis-plus-jsqlparser-4.9 (JDK 8 兼容变体) | 3.5.17    |
| 数据库驱动 | mysql-connector-j                                                        | 随 parent |
| 连接池     | druid-spring-boot-starter                                                | 1.2.28    |
| 缓存       | spring-boot-starter-data-redis                                           | 随 parent |
| 接口文档   | knife4j-openapi3-**spring**-boot-starter                                 | 4.5.0     |
| 配置提示   | spring-boot-configuration-processor                                      | 随 parent |
| 文件存储   | 云对象存储 OSS (阿里云 / 腾讯云，渠道待定)                               | 待定      |
| 工具库     | hutool-all                                                               | 5.8.48    |
| 认证授权   | Apache Shiro (JWT 无状态 + Redis 白名单方案)                             | 1.13.0    |

### 贡献者

| 成员                            | 角色                  | 主页                                                |
|---------------------------------|-----------------------|-----------------------------------------------------|
| **Hoshiikunn** (星井ほしいくん) | 项目发起人 / 主要开发 | [GitHub @Hoshiikunn](https://github.com/Hoshiikunn) |
