# API 接口规范

> 本文件定义了音乐软件项目的 RESTful API 接口规范，包含接口列表、请求/响应结构、错误码等。

---

## 一、基础信息

### 1.1 基础 URL
```
http://{server-ip}:8080/api/v1
```

### 1.2 版本控制
- 当前版本：v1
- 版本路径：`/api/v1`

### 1.3 认证方式
- 使用 JWT Token 认证
- Token 放在请求头：`Authorization: Bearer <token>`

---

## 二、接口列表

### 2.1 音乐管理接口

| HTTP 方法 | 端点 | 描述 | 是否需要认证 |
|-----------|------|------|--------------|
| GET | `/music` | 获取音乐列表 | 否 |
| GET | `/music/{id}` | 获取单个音乐信息 | 否 |
| GET | `/music/{id}/stream` | 流式播放音乐 | 否 |
| POST | `/music/upload` | 上传音乐文件 | 是 |
| DELETE | `/music/{id}` | 删除音乐文件 | 是 |

### 2.2 播放列表接口

| HTTP 方法 | 端点 | 描述 | 是否需要认证 |
|-----------|------|------|--------------|
| GET | `/playlists` | 获取播放列表 | 是 |
| GET | `/playlists/{id}` | 获取单个播放列表 | 是 |
| POST | `/playlists` | 创建播放列表 | 是 |
| PUT | `/playlists/{id}` | 更新播放列表 | 是 |
| DELETE | `/playlists/{id}` | 删除播放列表 | 是 |
| POST | `/playlists/{id}/music/{musicId}` | 添加音乐到播放列表 | 是 |
| DELETE | `/playlists/{id}/music/{musicId}` | 从播放列表移除音乐 | 是 |

---

## 三、接口详细说明

### 3.1 GET /music - 获取音乐列表

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认 0 |
| size | int | 否 | 每页数量，默认 20 |
| keyword | string | 否 | 搜索关键词 |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": [
      {
        "id": 1,
        "fileName": "song.mp3",
        "filePath": "/music-storage/songs/song.mp3",
        "fileSize": 3200000,
        "duration": 180,
        "format": "mp3",
        "createdAt": "2024-01-01T12:00:00",
        "updatedAt": "2024-01-01T12:00:00"
      }
    ],
    "totalElements": 100,
    "totalPages": 5,
    "currentPage": 0
  }
}
```

### 3.2 GET /music/{id} - 获取单个音乐信息

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 音乐 ID |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "fileName": "song.mp3",
    "filePath": "/music-storage/songs/song.mp3",
    "fileSize": 3200000,
    "duration": 180,
    "format": "mp3",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  }
}
```

**失败响应** (404 Not Found):

```json
{
  "code": 404,
  "message": "Music not found",
  "data": null
}
```

### 3.3 GET /music/{id}/stream - 流式播放音乐

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 音乐 ID |

**成功响应**: 返回音频文件流

**失败响应** (404 Not Found):

```json
{
  "code": 404,
  "message": "Music not found",
  "data": null
}
```

### 3.4 POST /music/upload - 上传音乐文件

**请求体**: `multipart/form-data`

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | file | 是 | 音乐文件 |

**成功响应** (201 Created):

```json
{
  "code": 201,
  "message": "Upload success",
  "data": {
    "id": 1,
    "fileName": "song.mp3",
    "filePath": "/music-storage/songs/song.mp3",
    "fileSize": 3200000,
    "duration": 180,
    "format": "mp3",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  }
}
```

**失败响应** (400 Bad Request):

```json
{
  "code": 400,
  "message": "File is required",
  "data": null
}
```

### 3.5 DELETE /music/{id} - 删除音乐文件

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 音乐 ID |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Delete success",
  "data": null
}
```

**失败响应** (404 Not Found):

```json
{
  "code": 404,
  "message": "Music not found",
  "data": null
}
```

---

## 四、错误码定义

| 错误码 | 含义 | HTTP 状态码 |
|--------|------|-------------|
| 200 | 成功 | 200 |
| 201 | 创建成功 | 201 |
| 400 | 请求参数错误 | 400 |
| 401 | 未认证 | 401 |
| 403 | 无权限 | 403 |
| 404 | 资源不存在 | 404 |
| 500 | 服务器内部错误 | 500 |

---

## 五、数据结构定义

### 5.1 MusicDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | long | 音乐 ID |
| fileName | string | 文件名 |
| filePath | string | 文件路径 |
| fileSize | long | 文件大小（字节） |
| duration | int | 时长（秒） |
| format | string | 格式 |
| createdAt | string | 创建时间 |
| updatedAt | string | 更新时间 |

---

> **最后更新**：2026-05-20
> **版本**：1.0.0