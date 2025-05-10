 # 项目介绍与接口文档

## 用前配置

使用前需要先修改 `application.yml` 文件中 `spring.datasource` 的数据库用户名和密码。不需要手动建立数据库和表，程序会使用 `InitDB.sql` 自动创建。

## 1、用户

### 1.1 注册新用户

用于注册新用户  

请求路径：`/user/register`  
请求方式：`POST`  
请求头：  

| Header         | 必选 | 描述                             |
|----------------|----|--------------------------------|
| `Content-Type` | 是  | 请求体内容类型，必须为 `application/json` |
| `Accept`       | 否  | 客户端期望的响应数据类型                   |

请求体：

请求参数格式：`x-www-form-urlencoded`，包含以下字段：

| 字段名        | 类型     | 必选 | 描述                                 |
|------------|--------|----|------------------------------------|
| `username` | string | 是  | 6-20位字符                            |
| `password` | string | 是  | 6-20位字符<br>包含数字、小写字母、大写字母、特殊符号中的三个 |
| `nickname` | string | 是  | 不超过20位字符                           |

请求体示例：  

```json
{
    "username": "testuser",
    "password": "123456@Abcd",
    "nickname": "zhangsan"
}
```

响应数据：

| 名称      | 类型     | 描述                                                                         |
|---------|--------|----------------------------------------------------------------------------|
| code    | number | 0 OK，201 Created，409 Conflict，400 Bad Request<br>500 Internal Server Error |
| message | string | 详细信息                                                                       |
| data    | number | 其他数据                                                                       |

响应体示例：

```json
{
  "code": 0,
  "message": "注册成功",
  "data": null
}
```
```json
{
  "code": 409,
  "message": "用户名重复，注册失败",
  "data": null
}
```

### 1.2 用户登陆

用于用户登陆

请求路径：`/user/login`  
请求方式：`POST`  
请求头：

| Header         | 必选 | 描述                             |
|----------------|----|--------------------------------|
| `Content-Type` | 是  | 请求体内容类型，必须为 `application/json` |
| `Accept`       | 否  | 客户端期望的响应数据类型                   |

请求参数格式：`x-www-form-urlencoded`，包含以下字段：

| 字段名        | 类型     | 必选 | 描述                                 |
|------------|--------|----|------------------------------------|
| `username` | string | 是  | 6-20位字符                            |
| `password` | string | 是  | 6-20位字符<br>包含数字、小写字母、大写字母、特殊符号中的三个 |

请求体示例：

```json
{
    "username": "testuser",
    "password": "123456@Abcd"
}
```

响应数据：

| 名称      | 类型     | 描述    |
|---------|--------|-------|
| state   | number | 0 OK  |
| message | string | 详细信息  |
| data    | string | jwt令牌 |

响应体示例：

```json
{
  "code": 0,
  "message": "登陆成功",
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InpoYW5nc2FuIn0sImV4cCI6MTc0MTE1OTE5OH0.DoD-7cJMcCO9Vsnm5g5c5Usaf1UvuywzrRl7zkAH-YQ"
}
```

用户登陆后，会在响应数据中的 `data` 中返回jwt令牌，在除了注册和登陆其他所有请求中，都需要在请求头 header 中携带jwt令牌，请求头名称为 `Authorization`，值为jwt令牌。  

若令牌无效或未携带令牌，则会返回 `401 Unauthorized`  

### 1.3 获取用户信息

用于获取用户详细信息

请求路径：`/user/getInfo`  
请求方式：`GET`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数：无

响应数据类型：`application/json`

| 名称            | 类型     | 描述                    |
|---------------|--------|-----------------------|
| code          | number | 0 OK，401 Unauthorized |
| message       | string | 详细信息                  |
| data          | object | 用户信息                  |
| \|-id         | number | 用户id                  |
| \|-username   | string | 用户名                   |
| \|-nickname   | string | 昵称                    |
| \|-userPicUrl | string | 头像                    |
| \|-createTime | int    | 创建时间                  |

响应体示例：

```json
{
  "code": 0,
  "message": "获取用户信息成功",
  "data": {
    "id": 1,
    "username": "zhangsan",
    "nickname": "zhangsan",
    "userPicBase64": null,
    "createTime": "2025-03-05T11:59:20",
    "school": null,
    "departments": null,
    "major": null
  }
}
```

### 1.4 更新用户基本信息

用于更新用户的密码和昵称

请求路径：`/user/updateBasic`  
请求方式：`POST`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`JSON`，包含以下字段：

| 字段名        | 类型     | 必选 | 描述                                 |
|------------|--------|----|------------------------------------|
| `id`       | number | 是  | 用户id                               |
| `username` | string | 是  | 6-20位字符                            |
| `nickname` | string | 是  | 6-20位字符                            |

请求体示例：  

```json
{
    "id": 1,
    "username": "zhangsan",
    "nickname": "wangwu"
}
```

响应数据类型：`application/json`

| 名称            | 类型     | 描述                    |
|---------------|--------|-----------------------|
| code          | number | 0 OK，401 Unauthorized |
| message       | string | 详细信息                  |
| data          | object | 用户信息                  |
| \|-id         | number | 用户id                  |
| \|-username   | string | 用户名                   |
| \|-nickname   | string | 昵称                    |
| \|-userPicUrl | string | 头像                    |
| \|-createTime | int    | 创建时间                  |

响应体示例：

```json
{
  "code": 0,
  "message": "更新用户信息成功",
  "data": {
    "id": 1,
    "username": "zhangsan",
    "nickname": "wangwu",
    "userPicBase64": null,
    "createTime": null,
    "school": null,
    "departments": null,
    "major": null
  }
}
```

### 1.5 更新用户头像

用于更新用户的头像

请求路径：`/user/updatePic`  
请求方式：`PATCH`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`queryString`，包含以下字段：

| 参数名      | 类型     | 必选 | 描述        |
|----------|--------|----|-----------|
| `picUrl` | string | 是  | 用户头像url地址 |


请求数据示例：

```
picUrl=https://xxx.xxx.xxx/xx/xxxxxx.png
```

相应数据类型：`application/json`

| 名称               | 类型     | 描述        |
|------------------|--------|-----------|
| code             | number | 0-成功，1-失败 |
| message          | string | 详细信息      |
| data             | object | 返回的数据     |

响应体示例：

```json
{
  "code": 0,
  "message": "更新用户头像成功",
  "data": null
}
```

### 1.6 更新用户密码

用于更新用户的密码

请求路径：`/user/updatePwd`  
请求方式：`PATCH`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`application/json`，包含以下字段：

| 参数名      | 类型     | 必选 | 描述    |
|----------|--------|----|-------|
| `oldPwd` | string | 是  | 用户旧密码 |
| `newPwd` | string | 是  | 用户新密码 | 
| `rePwd`  | string | 是  | 重复新密码 |

请求数据示例：

```
{
  "oldPwd": "123123",
  "newPwd": "123123",
  "rePwd": "123123"
}
```

响应数据类型：`application/json`

| 名称        | 类型     | 描述        |
|-----------|--------|-----------|
| `code`    | number | 0-成功，1-失败 |
| `message` | string | 详细信息      |
| `data`    | object | 返回的数据     |

响应体示例：

```json
{
  "code": 0,
  "message": "更新用户密码成功",
  "data": null
}
```
```json
{
  "code": 1,
  "message": "旧密码错误",
  "data": null
}
```
```json
{
  "code": 1,
  "message": "新密码不一致",
  "data": null
}
```
```json
{
  "code": 1,
  "message": "密码不符合要求",
  "data": null
}
```

### 更新用户其他信息

用于更新用户的学校、院系和专业

## 2、文章分类

### 2.1 新增文章分类

用于新增文章分类

请求路径：`/category`  
请求方式：`POST`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`application/json`，包含以下字段：

| 参数名             | 类型     | 必选 | 描述   |
|-----------------|--------|----|------|
| `categoryName`  | string | 是  | 分类名称 |
| `categoryAlias` | string | 是  | 分类别名 |


请求数据示例：

```
{
  "categoryName": "测试分类",
  "categoryAlias": "test"
}
```

响应数据类型：`application/json`

| 名称        | 类型     | 描述        |
|-----------|--------|-----------|
| `code`    | number | 0-成功，1-失败 |
| `message` | string | 详细信息      |
| `data`    | object | 返回的数据     |


响应体示例：

```
{
  "code": 0,
  "message": "新增文章分类成功",
  "data": null
}
```

### 2.2 获取分类列表

用于获取 **当前登陆的用户所创建的** 文章分类列表

请求路径：`/category`  
请求方式：`GET`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数：无

响应数据类型：`application/json`

| 名称               | 类型     | 描述        |
|------------------|--------|-----------|
| code             | number | 0-成功，1-失败 |
| message          | string | 详细信息      |
| data             | array  | 返回的数据     |
| \|-id            | number | 分类id      |
| \|-categoryName  | string | 分类名称      |
| \|-categoryAlias | string | 分类别名      |
| \|-createTime    | string | 创建时间      |

响应体示例：

```
{
    "code": 0,
    "message": "获取文章分类列表成功",
    "data": [
        {
            "id": 1,
            "categoryName": "测试",
            "categoryAlias": "test",
            "createUser": 1,
            "createTime": "2025-05-05 19:58:25"
        },
        {
            "id": 3,
            "categoryName": "测试2",
            "categoryAlias": "test2",
            "createUser": 1,
            "createTime": "2025-05-05 20:54:20"
        },
        {
            "id": 4,
            "categoryName": "测试3",
            "categoryAlias": "test3",
            "createUser": 1,
            "createTime": "2025-05-05 22:52:43"
        }
    ]
}
```

### 2.3 获取文章分类详情

用于获取某一文章分类的详情，不允许获取非本用户创建的文章分类的详情

请求路径：`/category/detail`  
请求方式：`GET`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`queryString`，包含以下字段：

| 参数名  | 类型   | 必选 | 描述     |
|------|------|----|--------|
| `id` | number | 是  | 文章分类id |


请求数据示例：

```
id=6
```

响应数据类型：`application/json`

| 名称               | 类型     | 描述        |
|------------------|--------|-----------|
| code             | number | 0-成功，1-失败 |
| message          | string | 详细信息      |
| data             | object | 返回的数据     |
| \|-id            | number | 分类id      |
| \|-categoryName  | string | 分类名称      |
| \|-categoryAlias | string | 分类别名      |
| \|-createTime    | string | 创建时间      |

响应体示例：

```
{
    "code": 0,
    "message": "获取文章分类详情成功",
    "data": {
        "id": 1,
        "categoryName": "测试",
        "categoryAlias": "test",
        "createUser": 1,
        "createTime": "2025-05-06 14:28:04"
    }
}
```

### 2.4 更新文章分类

用于更新文章分类的信息，不允许更新非本用户创建的文章分类的详情

请求路径：`/category`  
请求方式：`PUT`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`application/json`，包含以下字段：

| 参数名             | 类型     | 必选 | 描述   |
|-----------------|--------|----|------|
| `id`            | number | 是  | 分类id |
| `categoryName`  | string | 是  | 分类名称 |
| `categoryAlias` | string | 是  | 分类别名 |


请求数据示例：

```
{
    "id": 2,
    "categoryName": "测试2",
    "categoryAlias": "test2_updated"
}
```

响应数据类型：`application/json`

| 名称               | 类型     | 描述        |
|------------------|--------|-----------|
| code             | number | 0-成功，1-失败 |
| message          | string | 详细信息      |
| data             | string | 被更新的分类id  |


响应体示例：

```
{
    "code": 0,
    "message": "更新文章分类详情成功",
    "data": 2
}
```


### 2.5 删除文章分类

用于删除某一文章分类，不允许删除非本用户创建的文章分类

请求路径：`/category`  
请求方式：`DELETE`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`queryString`，包含以下字段：

| 参数名 | 类型     | 必选 | 描述       |
|----|--------|----|----------|
| id | number | 是  | 要删除的分类id |


请求数据示例：

```
id=6
```

响应数据类型：`application/json`

| 名称               | 类型     | 描述        |
|------------------|--------|-----------|
| code             | number | 0-成功，1-失败 |
| message          | string | 详细信息      |
| data             | string | 被删除的分类id  |


响应体示例：

```
{
    "code": 0,
    "message": "删除文章分类详情成功",
    "data": 6
}
```

## 3、文章

### 3.1 新增文章

用于新增一篇文章

请求路径：`/article`  
请求方式：`POST`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`application/json`，包含以下字段：

| 参数名           | 类型     | 必选 | 描述            |
|---------------|--------|----|---------------|
| `title`       | string | 是  | 标题            |
| `categoryId`  | number | 是  | 文章分类id        |
| `content`     | string | 是  | 内容            |
| `coverPicUrl` | string | 否  | 封面图片url       |
| `state`       | number | 是  | 状态，0-草稿，1-已发布 |

请求数据示例：

```
{
    "title": "测试标题",
    "categoryId": 2,
    "content": "内容内容内容内容内容内容内容内容内容",
    "coverPicUrl": "https://www.baidu.com/",
    "state": 1
}
```

响应数据类型：`application/json`

| 名称               | 类型     | 描述        |
|------------------|--------|-----------|
| code             | number | 0-成功，1-失败 |
| message          | string | 详细信息      |
| data             | string | 返回的数据     |


响应体示例：

```
{
    "code": 0,
    "message": "新增文章成功",
    "data": null
}
```

### 3.2 获取文章列表（条件分页）

用于根据条件进行查询，并且可以实现翻页

请求路径：`/article`  
请求方式：`GET`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`queryString`，包含以下字段：

| 参数名     | 类型     | 必选 | 描述   |
|---------|--------|----|------|
| pageNum | number | 是  | 当前页码 |
| pageSize | number | 是 | 每页条数 |
| categoryId | number | 否 | 要筛选的分类id |
| state | number | 否 | 要筛选的状态，0-草稿，1-已发布 |

请求数据示例：

```
pageNum=3&pageSize=2
```

```
pageNum=2&pageSize=5&categoryId=1&state=1
```

响应数据类型：`application/json`

| 名称             | 类型     | 描述            |
|----------------|--------|---------------|
| code           | number | 0-成功，1-失败     |
| message        | string | 详细信息          |
| data           | object | 返回的数据         |
| \| count       | number | 查询到的文章总数      |
| \| items       | array  | 文章列表          |
| └─ id          | number | 文章id          |
| \| categoryId  | number | 分类id          |
| \| createUser  | number | 作者id          |
| \| title       | string | 标题            |
| \| content     | string | 内容            |
| \| coverPicUrl | string | 封面图片url       |
| \| state       | number | 状态，0-草稿，1-已发布 |
| \| createTime  | string | 创建时间          |
| \| updateTime  | string | 更新时间          |

响应体示例（pageNum=3&pageSize=2）：

```
{
    "code": 0,
    "message": "成功获取文章列表",
    "data": {
        "count": 15,
        "lists": [
            {
                "id": 5,
                "categoryId": 1,
                "createUser": 3,
                "title": "测试标题",
                "content": "内容内容内容内容内容内容内容内容内容",
                "coverPicUrl": "https://www.baidu.com/",
                "state": 1,
                "createTime": "2025-05-08T23:31:45",
                "updateTime": "2025-05-08T23:31:45"
            },
            {
                "id": 6,
                "categoryId": 1,
                "createUser": 3,
                "title": "测试标题",
                "content": "内容内容内容内容内容内容内容内容内容",
                "coverPicUrl": "https://www.baidu.com/",
                "state": 1,
                "createTime": "2025-05-08T23:31:46",
                "updateTime": "2025-05-08T23:31:46"
            }
        ]
    }
}
```

### 3.3 获取文章详情

用于获取某一文章的详情

请求路径：`/article/detail`  
请求方式：`GET`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`queryString`，包含以下字段：

| 参数名  | 类型     | 必选 | 描述     |
|------|--------|----|--------|
| `id` | number | 是  | 文章分类id |

请求数据示例：

```
id=2
```

响应数据类型：`application/json`

| 名称             | 类型     | 描述            |
|----------------|--------|---------------|
| code           | number | 0-成功，1-失败     |
| message        | string | 详细信息          |
| data           | object | 返回的数据         |
| \| id          | number | 文章id          |
| \| categoryId  | number | 分类id          |
| \| createUser  | number | 作者id          |
| \| title       | string | 标题            |
| \| content     | string | 内容            |
| \| coverPicUrl | string | 封面图片url       |
| \| state       | number | 状态，0-草稿，1-已发布 |
| \| createTime  | string | 创建时间          |
| \| updateTime  | string | 更新时间          |


响应体示例：

```
{
    "code": 0,
    "message": "成功获取文章详情",
    "data": {
        "id": 2,
        "categoryId": 1,
        "createUser": 3,
        "title": "测试标题",
        "content": "内容内容内容内容内容内容内容内容内容",
        "coverPicUrl": "https://www.baidu.com/",
        "state": 1,
        "createTime": "2025-05-08T23:31:43",
        "updateTime": "2025-05-08T23:31:43"
    }
}
```

```json
{
    "code": 1,
    "message": "文章不存在",
    "data": null
}
```

### 3.4 更新文章详情

用于更新文章的分类、标题、内容、封面、状态

请求路径：`/article`  
请求方式：`PATCH`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`application/json`，包含以下字段：

| 参数名         | 类型     | 必选 | 描述            |
|-------------|--------|----|---------------|
| id          | number | 是  | 要更新的文章id      |
| categoryId  | number | 否  | 分类id          |
| title       | string | 否  | 标题            |
| content     | string | 否  | 内容            |
| coverPicUrl | string | 否  | 封面图片url       |
| state       | number | 否  | 状态，0-草稿，1-已发布 |

请求数据示例：

```
{
    "id": 3,
    "categoryId": 3,
    "title": "更新后的标题"
}
```

响应数据类型：`application/json`

| 名称      | 类型     | 描述        |
|---------|--------|-----------|
| code    | number | 0-成功，1-失败 |
| message | string | 详细信息      |
| data    | string | 返回的数据     |


响应体示例：

```
{
    "code": 0,
    "message": "更新文章成功",
    "data": null
}
```

### 3.5 删除文章

用于删除自己的文章

请求路径：`/article`  
请求方式：`DELETE`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`queryString`，包含以下字段：

| 参数名 | 类型     | 必选 | 描述       |
|-----|--------|----|----------|
| id  | number | 是  | 要删除的文章id |

请求数据示例：

```
id=3
```

响应数据类型：`application/json`

| 名称      | 类型     | 描述        |
|---------|--------|-----------|
| code    | number | 0-成功，1-失败 |
| message | string | 详细信息      |
| data    | string | 返回的数据     |


响应体示例：

```
{
    "code": 0,
    "message": "删除文章成功",
    "data": null
}
```

## 4、文件上传

### 4.1 单文件上传

用于

请求路径：`/upload`  
请求方式：`POST`  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`multipart/form-data`，包含以下字段：

| 参数名         | 类型     | 必选 | 描述     |
|-------------|--------|----|--------|
| description | string | 是  | 对文件的描述 |
| file        | file   | 是  | 要上传的文件 |

响应数据类型：`application/json`

| 名称      | 类型     | 描述        |
|---------|--------|-----------|
| code    | number | 0-成功，1-失败 |
| message | string | 详细信息      |
| data    | string | 存储url     |


响应体示例：

```

```












## template

用于

请求路径：`/article`  
请求方式：``  
请求头：

| Header          | 必选 | 描述                             |
|-----------------|----|--------------------------------|
| `Authorization` | 是  | jwt令牌                          |

请求参数格式：`queryString`，包含以下字段：

| 参数名 | 类型     | 必选 | 描述       |
|----|--------|----|----------|
|  |  | 是  |  |

请求参数格式：`application/json`，包含以下字段：

| 参数名             | 类型     | 必选 | 描述   |
|-----------------|--------|----|------|
| ``            |  | 是  |  |

请求数据示例：

```

```

响应数据类型：`application/json`

| 名称      | 类型     | 描述        |
|---------|--------|-----------|
| code    | number | 0-成功，1-失败 |
| message | string | 详细信息      |
| data    | string | 返回的数据     |


响应体示例：

```

```