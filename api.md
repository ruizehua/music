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
| GET | `/music/search` | 搜索音乐 | 否 |

### 2.2 播放列表接口

| HTTP 方法 | 端点 | 描述 | 是否需要认证 |
|-----------|------|------|--------------|
| GET | `/playlists` | 获取播放列表 | 是 |
| GET | `/playlists/{id}` | 获取单个播放列表 | 是 |
| POST | `/playlists` | 创建播放列表 | 是 |
| PUT | `/playlists/{id}` | 更新播放列表 | 是 |
| DELETE | `/playlists/{id}` | 删除播放列表 | 是 |
| POST | `/playlists/{id}/songs` | 添加歌曲到播放列表 | 是 |
| DELETE | `/playlists/{id}/songs/{songId}` | 从播放列表移除歌曲 | 是 |

### 2.3 收藏接口

| HTTP 方法 | 端点 | 描述 | 是否需要认证 |
|-----------|------|------|--------------|
| GET | `/favorites` | 获取收藏列表 | 是 |
| POST | `/favorites/{songId}` | 添加收藏 | 是 |
| DELETE | `/favorites/{songId}` | 取消收藏 | 是 |

### 2.4 播放历史接口

| HTTP 方法 | 端点 | 描述 | 是否需要认证 |
|-----------|------|------|--------------|
| GET | `/history` | 获取播放历史 | 是 |
| DELETE | `/history` | 清空播放历史 | 是 |

---

## 三、接口详细说明

### 3.1 GET /music - 获取音乐列表

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认 0 |
| size | int | 否 | 每页数量，默认 20 |
| keyword | string | 否 | 搜索关键词 |
| artist | string | 否 | 按艺术家筛选 |
| album | string | 否 | 按专辑筛选 |

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
        "artist": "Artist Name",
        "album": "Album Name",
        "title": "Song Title",
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
    "artist": "Artist Name",
    "album": "Album Name",
    "title": "Song Title",
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

**成功响应**: 返回音频文件流（Content-Type: audio/mpeg）

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
    "artist": "Artist Name",
    "album": "Album Name",
    "title": "Song Title",
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

### 3.6 GET /music/search - 搜索音乐

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| keyword | string | 是 | 搜索关键词 |
| page | int | 否 | 页码，默认 0 |
| size | int | 否 | 每页数量，默认 20 |

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
        "title": "Song Title",
        "artist": "Artist Name",
        "album": "Album Name"
      }
    ],
    "totalElements": 10,
    "totalPages": 1,
    "currentPage": 0
  }
}
```

### 3.7 GET /playlists - 获取播放列表

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "My Playlist",
      "description": "My favorite songs",
      "songCount": 10,
      "createdAt": "2024-01-01T12:00:00",
      "updatedAt": "2024-01-01T12:00:00"
    }
  ]
}
```

### 3.8 GET /playlists/{id} - 获取单个播放列表

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 播放列表 ID |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "My Playlist",
    "description": "My favorite songs",
    "songs": [
      {
        "id": 1,
        "title": "Song Title",
        "artist": "Artist Name",
        "duration": 180
      }
    ],
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  }
}
```

### 3.9 POST /playlists - 创建播放列表

**请求体**:

```json
{
  "name": "My Playlist",
  "description": "My favorite songs"
}
```

**成功响应** (201 Created):

```json
{
  "code": 201,
  "message": "Create success",
  "data": {
    "id": 1,
    "name": "My Playlist",
    "description": "My favorite songs",
    "songCount": 0,
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  }
}
```

### 3.10 PUT /playlists/{id} - 更新播放列表

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 播放列表 ID |

**请求体**:

```json
{
  "name": "Updated Playlist",
  "description": "Updated description"
}
```

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Update success",
  "data": {
    "id": 1,
    "name": "Updated Playlist",
    "description": "Updated description",
    "songCount": 10,
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  }
}
```

### 3.11 DELETE /playlists/{id} - 删除播放列表

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 播放列表 ID |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Delete success",
  "data": null
}
```

### 3.12 POST /playlists/{id}/songs - 添加歌曲到播放列表

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 播放列表 ID |

**请求体**:

```json
{
  "songId": 1
}
```

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Add success",
  "data": null
}
```

### 3.13 DELETE /playlists/{id}/songs/{songId} - 从播放列表移除歌曲

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | long | 是 | 播放列表 ID |
| songId | long | 是 | 歌曲 ID |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Remove success",
  "data": null
}
```

### 3.14 GET /favorites - 获取收藏列表

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "Song Title",
      "artist": "Artist Name",
      "album": "Album Name",
      "duration": 180,
      "addedAt": "2024-01-01T12:00:00"
    }
  ]
}
```

### 3.15 POST /favorites/{songId} - 添加收藏

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| songId | long | 是 | 歌曲 ID |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Add success",
  "data": null
}
```

### 3.16 DELETE /favorites/{songId} - 取消收藏

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| songId | long | 是 | 歌曲 ID |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Remove success",
  "data": null
}
```

### 3.17 GET /history - 获取播放历史

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认 0 |
| size | int | 否 | 每页数量，默认 20 |

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": [
      {
        "id": 1,
        "songId": 1,
        "title": "Song Title",
        "artist": "Artist Name",
        "playedAt": "2024-01-01T12:00:00"
      }
    ],
    "totalElements": 100,
    "totalPages": 5,
    "currentPage": 0
  }
}
```

### 3.18 DELETE /history - 清空播放历史

**成功响应** (200 OK):

```json
{
  "code": 200,
  "message": "Clear success",
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
| artist | string | 艺术家 |
| album | string | 专辑 |
| title | string | 歌曲标题 |
| createdAt | string | 创建时间 |
| updatedAt | string | 更新时间 |

### 5.2 PlaylistDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | long | 播放列表 ID |
| name | string | 播放列表名称 |
| description | string | 播放列表描述 |
| songCount | int | 歌曲数量 |
| createdAt | string | 创建时间 |
| updatedAt | string | 更新时间 |

### 5.3 FavoriteDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | long | 收藏 ID |
| songId | long | 歌曲 ID |
| addedAt | string | 添加时间 |

### 5.4 PlayHistoryDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | long | 历史记录 ID |
| songId | long | 歌曲 ID |
| playedAt | string | 播放时间 |

---

> **最后更新**：2026-05-20
> **版本**：1.0.0