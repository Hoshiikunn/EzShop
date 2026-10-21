/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2026/10/21 18:16:47                          */
/*==============================================================*/


drop index idx_status on sys_user;

drop index uk_email on sys_user;

drop index uk_mobile on sys_user;

drop index uk_username on sys_user;

drop table if exists sys_user;

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   bigint not null comment '用户ID',
   username             varchar(20) not null comment '用户名',
   password             varchar(100) not null comment '密码',
   real_name            varchar(32) comment '真实姓名',
   avatar_url           varchar(255) comment '头像',
   gender               tinyint not null default 1 comment '性别 0-女 1-男',
   mobile               varchar(20) comment '电话号码',
   email                varchar(100) comment '邮箱',
   status               tinyint not null default 1 comment '状态 0-禁用 1-启用',
   is_super_admin       tinyint not null default 0 comment '是否超级管理员 0-否 1-是',
   last_login_ip        varchar(45) comment '最后登录IP地址',
   last_login_time      datetime comment '最后登录时间',
   is_deleted           tinyint not null default 0 comment '删除标记 0-未删除 1-已删除',
   delete_time          datetime comment '删除时间',
   version              int not null default 1 comment '乐观锁版本号',
   creator              bigint comment '创建者',
   create_time          datetime comment '创建时间',
   updater              bigint comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table sys_user comment '系统用户表';

/*==============================================================*/
/* Index: uk_username                                           */
/*==============================================================*/
create unique index uk_username on sys_user
(
   username
);

/*==============================================================*/
/* Index: uk_mobile                                             */
/*==============================================================*/
create unique index uk_mobile on sys_user
(
   mobile
);

/*==============================================================*/
/* Index: uk_email                                              */
/*==============================================================*/
create unique index uk_email on sys_user
(
   email
);

/*==============================================================*/
/* Index: idx_status                                            */
/*==============================================================*/
create index idx_status on sys_user
(
   status
);

