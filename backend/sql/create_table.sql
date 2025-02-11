# 数据库初始化

-- 创建库
create database if not exists nx_api;

-- 切换库
use nx_api;

-- 接口信息表
create table if not exists api_info
(
    id              bigint auto_increment comment 'id' primary key,
    name            varchar(256)                       not null comment '接口名称',
    description     varchar(256)                       not null comment '接口描述',
    url             varchar(512)                       null comment '接口地址',
    request_header  text                               null comment '请求头',
    response_header text                               null comment '响应头',
    status          tinyint  default 0                 not null comment '接口状态 0-关闭 1-开启',
    method          varchar(256)                       not null comment '请求类型',
    user_id         bigint                             not null comment '创建人id',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted      tinyint  default 0                 not null comment '逻辑删除 默认 - 0'
) comment '接口信息表';

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    account      varchar(256)                           not null comment '账号',
    password     varchar(512)                           not null comment '密码',
    username     varchar(256)                           null comment '用户昵称',
    access_key   varchar(512)                           not null comment 'accessKey',
    secret_key   varchar(512)                           not null comment 'secretKey',
    user_avatar  varchar(1024)                          null comment '用户头像',
    user_profile varchar(512)                           null comment '用户简介',
    user_role    varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted   tinyint      default 0                 not null comment '是否删除'
) comment '用户';

-- 用户接口关系表
create table if not exists user_api_info
(
    id          bigint auto_increment comment '主键' primary key,
    user_id     bigint                             not null comment '调用用户id',
    api_info_id bigint                             not null comment '调用用户id',
    total_num   int                                not null comment '总调用次数',
    left_num    int                                not null comment '剩余调用次数',
    status      tinyint  default 0                 not null comment '0-正常，1-禁用',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  tinyint  default 0                 not null comment '逻辑删除 默认 - 0'
) comment '用户接口关系表';