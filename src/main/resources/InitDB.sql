CREATE DATABASE IF NOT EXISTS big_event;
USE big_event;

CREATE TABLE IF NOT EXISTS user (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，自增',
    username VARCHAR(255) NOT NULL COMMENT '用户名',
    password TEXT NOT NULL COMMENT '密码',
    nickname VARCHAR(255) NOT NULL COMMENT '昵称',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    user_pic_url TEXT COMMENT '用户头像'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS category (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，自增',
    category_name VARCHAR(32) NOT NULL COMMENT '分类名',
    category_alias VARCHAR(32) NOT NULL COMMENT '分类别名',
    create_user INT UNSIGNED NOT NULL  COMMENT '创建人id',
    create_time DATETIME NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类表';

CREATE TABLE IF NOT EXISTS article (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，自增',
    category_id INT UNSIGNED COMMENT '文章分类id',
    create_user INT UNSIGNED COMMENT '文章作者id',
    title VARCHAR(64) COMMENT '标题',
    content TEXT COMMENT '文章内容',
    cover_pic_url TEXT COMMENT '文章封面url',
    state VARCHAR(1) COMMENT '状态，0-草稿，1-已发布',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES category(id),
    CONSTRAINT fk_article_user FOREIGN KEY (create_user) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

CREATE TABLE IF NOT EXISTS upload_files (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，自增',
    upload_user INT UNSIGNED COMMENT '上传者id',
    file_url TEXT COMMENT '链接地址',
    description TEXT COMMENT '描述',
    create_time DATETIME NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传表';