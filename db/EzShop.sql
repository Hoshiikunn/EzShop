/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2026/11/13 15:06:12                          */
/*==============================================================*/


drop index idx_pid on sys_menu;

drop table if exists sys_menu;

drop index uk_role_key on sys_role;

drop table if exists sys_role;

drop index uk_role_menu on sys_role_menu;

drop table if exists sys_role_menu;

drop index idx_status on sys_user;

drop index uk_email on sys_user;

drop index uk_mobile on sys_user;

drop index uk_username on sys_user;

drop table if exists sys_user;

drop index idx_role_id on sys_user_role;

drop index uk_user_role on sys_user_role;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   id                   bigint not null comment '菜单ID',
   pid                  bigint not null default 0 comment '父菜单ID',
   menu_name            varchar(50) not null comment '菜单名称',
   menu_type            char(1) not null comment '菜单类型 M-目录 C-菜单 F-按钮',
   path                 varchar(255) comment '路由地址',
   component            varchar(255) comment '组件路径',
   perms                varchar(255) comment '权限标识',
   icon                 varchar(50) comment '菜单图标',
   sort                 int not null default 0 comment '排序',
   visible              tinyint not null default 1 comment '可见性 0-隐藏 1-显示',
   open_style           tinyint not null default 0 comment '打开方式 0-内部打开 1-外部打开',
   status               tinyint not null default 1 comment '状态 0-禁用 1-启用',
   creator              bigint comment '创建者',
   create_time          datetime comment '创建时间',
   updater              bigint comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table sys_menu comment '系统菜单表';

/*==============================================================*/
/* Index: idx_pid                                               */
/*==============================================================*/
create index idx_pid on sys_menu
(
   pid
);

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   bigint not null comment '角色ID',
   role_name            varchar(30) not null comment '角色名称',
   role_key             varchar(100) not null comment '角色权限字符',
   sort                 int not null default 0 comment '排序',
   status               tinyint not null default 1 comment '状态 0-禁用 1-启用',
   remark               varchar(255) comment '备注',
   creator              bigint comment '创建者',
   create_time          datetime comment '创建时间',
   updater              bigint comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table sys_role comment '系统角色表';

/*==============================================================*/
/* Index: uk_role_key                                           */
/*==============================================================*/
create unique index uk_role_key on sys_role
(
   role_key
);

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   id                   bigint not null comment '主键ID',
   role_id              bigint not null comment '角色ID',
   menu_id              bigint not null comment '菜单ID',
   creator              bigint comment '创建者',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table sys_role_menu comment '角色菜单关联表';

/*==============================================================*/
/* Index: uk_role_menu                                          */
/*==============================================================*/
create unique index uk_role_menu on sys_role_menu
(
   role_id,
   menu_id
);

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   bigint not null comment '用户ID',
   username             varchar(64) not null comment '用户名',
   password             varchar(100) not null comment '密码',
   real_name            varchar(32) not null comment '真实姓名',
   avatar_url           varchar(255) comment '头像',
   gender               tinyint not null default 1 comment '性别 0-女 1-男',
   mobile               varchar(64) comment '手机号',
   email                varchar(128) comment '邮箱',
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

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   bigint not null comment '主键ID',
   user_id              bigint not null comment '用户ID',
   role_id              bigint not null comment '角色ID',
   creator              bigint comment '创建者',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table sys_user_role comment '用户角色关联表';

/*==============================================================*/
/* Index: uk_user_role                                          */
/*==============================================================*/
create unique index uk_user_role on sys_user_role
(
   user_id,
   role_id
);

/*==============================================================*/
/* Index: idx_role_id                                           */
/*==============================================================*/
create index idx_role_id on sys_user_role
(
   role_id
);

