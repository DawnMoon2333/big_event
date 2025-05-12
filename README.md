# 大事件博客管理系统

## 系统介绍

大事件博客管理系统是一个基于Spring Boot和Vue3开发的个人博客内容管理系统，用户可以在该系统中注册、登录、管理个人文章和文章分类。系统提供了完善的用户管理、文章管理和文件上传功能，实现了用户个性化内容管理的全流程支持。

### 系统功能

- **用户管理**：包括用户注册、登录、信息查询、基本信息修改、头像更新和密码修改等功能
- **文章分类管理**：支持添加、查询、更新和删除文章分类
- **文章管理**：支持文章的创建、编辑、发布、查询和删除
- **文件上传**：支持图片等文件的上传功能，用于更新用户头像和文章封面

### 系统架构

系统采用前后端分离架构：
- 前端：基于Vue3开发的Web应用
- 后端：基于Spring Boot的RESTful API服务

后端系统架构主要分为以下几层：
- **控制器层（Controller）**：处理HTTP请求，参数校验，响应结果
- **服务层（Service）**：处理业务逻辑
- **持久层（Mapper）**：数据访问与操作
- **拦截器（Interceptor）**：处理请求拦截，如登录验证
- **工具类（Utils）**：提供JWT、密码加密等功能支持

## 技术栈

### 后端技术
- **核心框架**：Spring Boot
- **持久层框架**：MyBatis
- **数据库**：MySQL
- **缓存**：Redis
- **认证授权**：JWT（JSON Web Token）
- **参数校验**：Spring Validation
- **文件存储**：华为云对象存储服务
- **分页插件**：PageHelper

### 前端技术
- **框架**：Vue3
- **状态管理**：Vuex/Pinia
- **路由**：Vue Router
- **HTTP客户端**：Axios
- **UI组件库**：Element Plus

## 系统安装与配置

### 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 配置说明

使用前需要先参照 [application-secret.yml.example](./src/main/resources/application-secret.yml.example)
 创建 `./src/main/resources/application-secret.yml`，配置以下信息：
- 数据库连接信息
- Redis连接信息
- 密码加密盐值
- 华为云对象存储配置

不需要手动建立数据库和表，程序会使用[InitDB.sql](./src/main/resources/InitDB.sql) 自动创建。

## API文档

系统提供了完整的RESTful API，主要接口包括：

- 用户管理相关接口
- 文章分类管理接口
- 文章管理接口
- 文件上传接口

详细API文档参考项目中的 [SystemArchitecture.md](./SystemArchitecture.md)。
