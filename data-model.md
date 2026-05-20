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
┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
│   music_files   │         │    playlists    │         │   favorites     │
├─────────────────┤         ├─────────────────┤         ├─────────────────┤
│ id (PK)         │         │ id (PK)         │         │ id (PK)         │
│ file_name       │         │ name            │         │ song_id (FK)    │
│ file_path       │         │ description     │         │ created_at      │
│ file_size       │         │ created_at      │         └────────┬────────┘
│ duration        │         │ updated_at      │                  │
│ format          │         └────────┬────────┘                  │
│ artist          │                  │ 1:N                      │
│ album           │                  ▼                          │
│ title           │         ┌─────────────────────┐             │
│ created_at      │         │  playlist_songs    │             │
│ updated_at      │         ├─────────────────────┤             │
└────────┬────────┘         │ playlist_id (FK)   │             │
         │                  │ song_id (FK)       │             │
         │ N:M              │ position           │             │
         ├──────────────────┴─────────────────────┘             │
         │                                                    │
         └─────────────────────────────────────────────────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ play_history    │
                  ├─────────────────┤
                  │ id (PK)         │
                  │ song_id (FK)    │
                  │ play_time       │
                  │ created_at      │
                  └─────────────────┘
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
| artist | TEXT | NULL | 艺术家名称 |
| album | TEXT | NULL | 专辑名称 |
| title | TEXT | NULL | 歌曲标题 |
| created_at | TEXT | NOT NULL | 创建时间（ISO 8601） |
| updated_at | TEXT | NOT NULL | 更新时间（ISO 8601） |

**索引**:
- `idx_file_path` - 唯一索引，加速文件路径查询
- `idx_created_at` - 普通索引，加速按时间排序查询
- `idx_artist` - 普通索引，加速按艺术家查询
- `idx_album` - 普通索引，加速按专辑查询

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
- `idx_playlist_name` - 普通索引，加速名称查询

### 3.3 playlist_songs 表

**功能**: 播放列表与音乐的关联关系

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| playlist_id | INTEGER | NOT NULL, FOREIGN KEY | 播放列表 ID |
| song_id | INTEGER | NOT NULL, FOREIGN KEY | 音乐 ID |
| position | INTEGER | NOT NULL DEFAULT 0 | 在播放列表中的位置 |

**约束**:
- PRIMARY KEY (playlist_id, song_id) - 复合主键
- FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE
- FOREIGN KEY (song_id) REFERENCES music_files(id) ON DELETE CASCADE

### 3.4 favorites 表

**功能**: 用户收藏的歌曲

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 主键，自增 |
| song_id | INTEGER | NOT NULL, FOREIGN KEY, UNIQUE | 音乐 ID |
| created_at | TEXT | NOT NULL | 创建时间（ISO 8601） |

**约束**:
- FOREIGN KEY (song_id) REFERENCES music_files(id) ON DELETE CASCADE

**索引**:
- `idx_favorite_song_id` - 唯一索引，防止重复收藏

### 3.5 play_history 表

**功能**: 播放历史记录

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 主键，自增 |
| song_id | INTEGER | NOT NULL, FOREIGN KEY | 音乐 ID |
| play_time | INTEGER | NOT NULL DEFAULT 0 | 播放时长（秒） |
| created_at | TEXT | NOT NULL | 播放时间（ISO 8601） |

**约束**:
- FOREIGN KEY (song_id) REFERENCES music_files(id) ON DELETE CASCADE

**索引**:
- `idx_history_song_id` - 普通索引，加速查询
- `idx_history_created_at` - 普通索引，按时间排序

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
    private String artist;
    private String album;
    private String title;
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
    private List<MusicFile> songs;
    // getters and setters
}
```

### 4.3 PlaylistSong 实体

```java
public class PlaylistSong {
    private Long playlistId;
    private Long songId;
    private Integer position;
    // getters and setters
}
```

### 4.4 Favorite 实体

```java
public class Favorite {
    private Long id;
    private Long songId;
    private String createdAt;
    // getters and setters
}
```

### 4.5 PlayHistory 实体

```java
public class PlayHistory {
    private Long id;
    private Long songId;
    private Integer playTime;
    private String createdAt;
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
| artist | String | 艺术家 |
| album | String | 专辑 |
| title | String | 歌曲标题 |
| createdAt | String | 创建时间 |
| updatedAt | String | 更新时间 |

### 5.2 PlaylistDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 播放列表 ID |
| name | String | 名称 |
| description | String | 描述 |
| songCount | Integer | 歌曲数量 |
| createdAt | String | 创建时间 |
| updatedAt | String | 更新时间 |

### 5.3 PlaylistDetailDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 播放列表 ID |
| name | String | 名称 |
| description | String | 描述 |
| songs | List\<MusicDTO\> | 歌曲列表 |
| createdAt | String | 创建时间 |
| updatedAt | String | 更新时间 |

### 5.4 FavoriteDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 收藏 ID |
| songId | Long | 歌曲 ID |
| song | MusicDTO | 歌曲信息 |
| addedAt | String | 添加时间 |

### 5.5 PlayHistoryDTO

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 历史记录 ID |
| songId | Long | 歌曲 ID |
| song | MusicDTO | 歌曲信息 |
| playTime | Integer | 播放时长（秒） |
| playedAt | String | 播放时间 |

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
| artist | 可选，最大长度 255 |
| album | 可选，最大长度 255 |
| title | 可选，最大长度 255 |

### 6.2 播放列表校验

| 字段 | 校验规则 |
|------|----------|
| name | 非空，最大长度 100 |
| description | 可选，最大长度 500 |

---

> **最后更新**：2026-05-20
> **版本**：1.0.0