# 数据模型定义

> 本文件定义了音乐软件项目的数据模型，包括实体、字段、关系、索引设计和数据库选型。

---

## 一、数据库选型

| 项目 | 说明 |
|------|------|
| 数据库类型 | SQLite |
| 版本 | 3.x |
| 存储位置 | 服务端本地文件 |
| 数据库文件 | `music-server.db` |

**选择理由**: SQLite 是轻量级嵌入式数据库，无需独立服务进程，适合个人使用场景，部署简单，性能满足需求。

---

## 二、实体关系图

```
┌─────────────────┐         ┌─────────────────┐
│   music_files   │         │    playlists    │
├─────────────────┤         ├─────────────────┤
│ id (PK)         │         │ id (PK)         │
│ file_name       │         │ name            │
│ file_path       │         │ description     │
│ file_size       │         │ created_at      │
│ duration        │         │ updated_at      │
│ format          │         └─────────────────┘
│ created_at      │                 │
│ updated_at      │                 │ 1:N
└────────┬────────┘                 │
         │                          ▼
         │ N:M            ┌─────────────────────┐
         └───────────────►│ playlist_music      │
                          ├─────────────────────┤
                          │ playlist_id (FK)    │
                          │ music_id (FK)       │
                          │ position            │
                          └─────────────────────┘
```

---

## 三、表结构定义

### 3.1 music_files 表

**功能**: 存储音乐文件信息

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 主键，自增 |
| file_name | TEXT | NOT NULL | 文件名（不含路径） |
| file_path | TEXT | NOT NULL UNIQUE | 文件完整路径 |
| file_size | INTEGER | NOT NULL | 文件大小（字节） |
| duration | INTEGER | NULL | 音乐时长（秒） |
| format | TEXT | NOT NULL | 音频格式（mp3, wav, flac 等） |
| created_at | TEXT | NOT NULL | 创建时间（ISO 8601） |
| updated_at | TEXT | NOT NULL | 更新时间（ISO 8601） |

**索引**:
- `idx_file_path` - 唯一索引，加速文件路径查询
- `idx_created_at` - 普通索引，加速按时间排序查询

### 3.2 playlists 表

**功能**: 存储播放列表信息

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 主键，自增 |
| name | TEXT | NOT NULL | 播放列表名称 |
| description | TEXT | NULL | 播放列表描述 |
| created_at | TEXT | NOT NULL | 创建时间（ISO 8601） |
| updated_at | TEXT | NOT NULL | 更新时间（ISO 8601） |

**索引**:
- `idx_name` - 普通索引，加速名称查询

### 3.3 playlist_music 表

**功能**: 播放列表与音乐的关联关系

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| playlist_id | INTEGER | NOT NULL, FOREIGN KEY | 播放列表 ID |
| music_id | INTEGER | NOT NULL, FOREIGN KEY | 音乐 ID |
| position | INTEGER | NOT NULL DEFAULT 0 | 在播放列表中的位置 |

**约束**:
- PRIMARY KEY (playlist_id, music_id) - 复合主键
- FOREIGN KEY (playlist_id) REFERENCES playlists(id)
- FOREIGN KEY (music_id) REFERENCES music_files(id)

---

## 四、实体类定义

### 4.1 MusicFile 实体

```java
public class MusicFile {
    private Long id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Integer duration;
    private String format;
    private String createdAt;
    private String updatedAt;
    // getters and setters
}
```

### 4.2 Playlist 实体

```java
public class Playlist {
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
    private List<MusicFile> musicFiles;
    // getters and setters
}
```

---

## 五、数据传输对象（DTO）

### 5.1 MusicDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 音乐 ID |
| fileName | String | 文件名 |
| filePath | String | 文件路径 |
| fileSize | Long | 文件大小 |
| duration | Integer | 时长（秒） |
| format | String | 格式 |
| createdAt | String | 创建时间 |
| updatedAt | String | 更新时间 |

### 5.2 PlaylistDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 播放列表 ID |
| name | String | 名称 |
| description | String | 描述 |
| musicCount | Integer | 音乐数量 |
| createdAt | String | 创建时间 |

---

## 六、数据校验规则

### 6.1 音乐文件校验

| 字段 | 校验规则 |
|------|----------|
| fileName | 非空，最大长度 255 |
| filePath | 非空，唯一 |
| fileSize | 非空，大于 0 |
| duration | 可选，大于等于 0 |
| format | 非空，允许值：mp3, wav, flac, aac, ogg |

### 6.2 播放列表校验

| 字段 | 校验规则 |
|------|----------|
| name | 非空，最大长度 100 |
| description | 可选，最大长度 500 |

---

> **最后更新**：2026-05-20
> **版本**：1.0.0