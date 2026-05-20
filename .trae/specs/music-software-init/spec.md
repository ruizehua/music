# 音乐软件项目规范

## Why

创建一个个人使用的音乐播放系统，包含 Java 后端服务和 Android 客户端应用，实现音乐文件的存储、管理和播放功能。系统采用客户端-服务端架构，音乐文件存储在服务端本地，SQLite 数据库仅存储文件路径和元数据信息。

## What Changes

- 创建 Java 后端服务（Spring Boot）
- 创建 Android 客户端应用
- 设计 SQLite 数据库结构（仅存储文件路径和元数据）
- 配置服务端音乐文件存储目录
- 实现音乐上传、查询、播放 API
- 实现客户端音乐浏览、搜索、播放功能
- 实现歌单管理、收藏功能
- 实现后台播放、锁屏播放、离线缓存功能

## Impact

### Affected Specs

- 文件管理规范
- API 接口规范
- 数据库设计规范
- 客户端架构规范

### Affected Code

- Java 后端代码（Spring Boot）
- Android 客户端代码
- 数据库访问层
- 文件存储系统

---

## ADDED Requirements

### Requirement: 核心播放控制

系统 SHALL 支持基本的音乐播放控制功能。

#### Scenario: 播放控制
- **WHEN** 用户点击播放按钮
- **THEN** 开始播放当前歌曲
- **AND** 显示播放状态

#### Scenario: 暂停控制
- **WHEN** 用户点击暂停按钮
- **THEN** 暂停当前播放的歌曲
- **AND** 保持播放进度

#### Scenario: 切歌控制
- **WHEN** 用户点击上一首/下一首按钮
- **THEN** 切换到上一首或下一首歌曲
- **AND** 开始播放新歌曲

#### Scenario: 进度控制
- **WHEN** 用户拖动进度条
- **THEN** 跳转到指定播放位置
- **AND** 继续播放

#### Scenario: 播放模式
- **WHEN** 用户切换播放模式
- **THEN** 支持顺序播放、单曲循环、随机播放三种模式

### Requirement: 后台与锁屏播放

系统 SHALL 支持后台播放和锁屏控制。

#### Scenario: 后台播放
- **WHEN** APP 切换到后台
- **THEN** 继续播放音乐
- **AND** 通知栏显示播放状态

#### Scenario: 锁屏播放
- **WHEN** 手机锁屏
- **THEN** 锁屏页显示播放控件
- **AND** 支持切歌、暂停、查看进度

#### Scenario: 耳机控制
- **WHEN** 插入耳机
- **THEN** 自动继续播放
- **WHEN** 拔出耳机
- **THEN** 自动暂停播放

### Requirement: 音频缓存与离线播放

系统 SHALL 支持音频缓存和离线播放。

#### Scenario: 自动缓存
- **WHEN** 播放服务端歌曲
- **THEN** 自动缓存到本地
- **AND** 优先使用缓存播放

#### Scenario: 离线播放
- **WHEN** 无网络连接
- **THEN** 能够播放已缓存的歌曲
- **AND** 显示离线状态提示

### Requirement: 音乐库管理

系统 SHALL 支持音乐库的管理功能。

#### Scenario: 歌曲列表展示
- **WHEN** 用户进入音乐库页面
- **THEN** 显示所有歌曲列表
- **AND** 支持分页加载

#### Scenario: 分类筛选
- **WHEN** 用户选择分类方式
- **THEN** 按歌手、专辑、文件名分类显示
- **AND** 支持快速切换

#### Scenario: 歌曲搜索
- **WHEN** 用户输入搜索关键词
- **THEN** 实时过滤歌曲列表
- **AND** 支持按歌名、歌手搜索

#### Scenario: 歌单管理
- **WHEN** 用户创建歌单
- **THEN** 创建新的歌单记录
- **AND** 支持添加/移除歌曲
- **AND** 支持歌单重命名

#### Scenario: 收藏功能
- **WHEN** 用户收藏歌曲
- **THEN** 添加到收藏列表
- **AND** 支持取消收藏

### Requirement: 基础播放适配

系统 SHALL 支持基础的播放适配功能。

#### Scenario: 音量调节
- **WHEN** 用户调节音量
- **THEN** 同步调节播放音量
- **AND** 显示音量级别

#### Scenario: 均衡器预设
- **WHEN** 用户选择均衡器预设
- **THEN** 应用对应的音效设置
- **AND** 支持流行、摇滚、古典、原声等预设

#### Scenario: 蓝牙设备控制
- **WHEN** 连接蓝牙设备
- **THEN** 支持蓝牙设备控制切歌、暂停

### Requirement: 歌词展示

系统 SHALL 支持歌词展示功能。

#### Scenario: 逐行滚动歌词
- **WHEN** 播放歌曲
- **THEN** 逐行滚动显示歌词
- **AND** 高亮当前播放行

#### Scenario: 歌词同步
- **WHEN** 播放进度变化
- **THEN** 歌词与播放进度精准同步

#### Scenario: 悬浮歌词
- **WHEN** 用户开启悬浮歌词
- **THEN** 在桌面显示悬浮歌词窗口
- **AND** 支持拖动位置

### Requirement: 辅助播放功能

系统 SHALL 支持辅助播放功能。

#### Scenario: 睡眠定时
- **WHEN** 用户设置睡眠定时
- **THEN** 定时到期自动停止播放
- **AND** 支持 10 分钟/30 分钟/1 小时等时长

#### Scenario: 播放历史
- **WHEN** 用户播放歌曲
- **THEN** 记录播放历史
- **AND** 支持查看和管理历史记录

#### Scenario: 自动续播
- **WHEN** 启动 APP
- **THEN** 自动续播上次暂停的歌曲
- **AND** 恢复播放进度

### Requirement: 跨端同步

系统 SHALL 支持跨端数据同步。

#### Scenario: 歌单同步
- **WHEN** 在任一设备修改歌单
- **THEN** 自动同步到其他设备
- **AND** 保持数据一致性

#### Scenario: 收藏同步
- **WHEN** 在任一设备收藏歌曲
- **THEN** 自动同步到其他设备
- **AND** 保持收藏状态一致

#### Scenario: 播放进度同步
- **WHEN** 在任一设备播放歌曲
- **THEN** 播放进度同步到其他设备
- **AND** 支持续播

### Requirement: 音乐文件管理

系统 SHALL 支持音乐文件的上传、删除和元数据管理。

#### Scenario: 上传音乐文件
- **WHEN** 用户通过客户端上传音乐文件
- **THEN** 服务端将文件保存到本地存储目录
- **AND** 提取音乐文件元数据（歌名、艺术家、专辑、时长等）
- **AND** 将文件路径和元数据存储到 SQLite 数据库
- **AND** 返回上传成功状态和音乐信息

#### Scenario: 获取音乐列表
- **WHEN** 客户端请求音乐列表
- **THEN** 服务端从 SQLite 数据库查询所有音乐记录
- **AND** 返回包含文件路径和元数据的音乐列表

#### Scenario: 播放音乐
- **WHEN** 用户请求播放音乐
- **THEN** 服务端根据文件路径读取音乐文件
- **AND** 以流媒体方式返回音频数据
- **AND** 支持断点续传

### Requirement: 音乐元数据管理

系统 SHALL 存储和管理音乐文件的基本元数据信息。

#### Scenario: 音乐元数据提取
- **WHEN** 上传新的音乐文件
- **THEN** 自动提取以下元数据：
  - 文件名（不含扩展名）作为默认歌名
  - 文件路径
  - 文件大小
  - 文件创建时间
  - 文件修改时间
  - 音乐时长（如果可获取）
  - 音频格式（Mp3、WAV 等）

### Requirement: 数据库设计

数据库 SHALL 仅存储音乐文件的路径和元数据信息，不存储音频文件本身。

#### Scenario: 数据库表结构

数据库包含以下表：

**music_files 表**

| 字段 | 类型 | 描述 |
|------|------|------|
| id | INTEGER | 主键，自增 |
| file_name | TEXT | 文件名（不含路径） |
| file_path | TEXT | 文件完整路径 |
| file_size | INTEGER | 文件大小（字节） |
| duration | INTEGER | 音乐时长（秒） |
| format | TEXT | 音频格式 |
| artist | TEXT | 艺术家 |
| album | TEXT | 专辑 |
| title | TEXT | 歌曲标题 |
| created_at | TEXT | 创建时间（ISO 8601） |
| updated_at | TEXT | 更新时间（ISO 8601） |

**playlists 表**

| 字段 | 类型 | 描述 |
|------|------|------|
| id | INTEGER | 主键，自增 |
| name | TEXT | 歌单名称 |
| description | TEXT | 歌单描述 |
| created_at | TEXT | 创建时间 |
| updated_at | TEXT | 更新时间 |

**playlist_songs 表**

| 字段 | 类型 | 描述 |
|------|------|------|
| playlist_id | INTEGER | 歌单 ID |
| song_id | INTEGER | 歌曲 ID |
| position | INTEGER | 排序位置 |

**favorites 表**

| 字段 | 类型 | 描述 |
|------|------|------|
| id | INTEGER | 主键，自增 |
| song_id | INTEGER | 歌曲 ID |
| created_at | TEXT | 创建时间 |

**play_history 表**

| 字段 | 类型 | 描述 |
|------|------|------|
| id | INTEGER | 主键，自增 |
| song_id | INTEGER | 歌曲 ID |
| play_time | INTEGER | 播放时长（秒） |
| created_at | TEXT | 播放时间 |

### Requirement: 服务端音乐存储

服务端 SHALL 将上传的音乐文件存储在指定的本地目录中。

#### Scenario: 存储目录结构

```
/music-storage
├── music/              # 音乐文件存储目录
│   ├── songs/          # 按歌曲存储
│   └── temp/           # 临时文件目录
```

---

## MODIFIED Requirements

### Requirement: 项目结构

项目 SHALL 采用标准的客户端-服务端分离架构。

#### Scenario: 目录结构

**服务端（Server）**

```
server/
├── src/
│   └── main/
│       ├── java/com/music/server/
│       │   ├── controller/      # REST API 控制器
│       │   ├── service/         # 业务逻辑层
│       │   ├── repository/       # 数据访问层
│       │   ├── model/           # 数据模型
│       │   ├── dto/             # 数据传输对象
│       │   ├── config/          # 配置类
│       │   ├── exception/       # 异常处理
│       │   └── MusicServerApplication.java
│       └── resources/
│           └── application.yml
├── pom.xml
└── music-storage/              # 音乐文件存储目录
    └── songs/
```

**客户端（Android Client）**

```
client/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/music/client/
│           │   ├── ui/          # 界面层（Compose）
│           │   ├── viewmodel/   # 视图模型
│           │   ├── data/        # 数据层
│           │   ├── domain/      # 领域层
│           │   ├── di/          # 依赖注入
│           │   ├── util/        # 工具类
│           │   └── MusicClientApp.kt
│           └── res/             # 资源文件
├── build.gradle.kts
└── settings.gradle.kts
```

---

## REMOVED Requirements

无

---

## Technical Requirements

### 服务端技术栈

- Java 21
- Spring Boot 3.2.x
- SQLite3
- Maven 3.9.x
- JAudioTagger（音乐元数据提取）

### 客户端技术栈

- Kotlin 1.9.x
- Android API 24+（Android 7.0）
- Jetpack Compose 1.6.x
- MVVM + Clean Architecture
- Retrofit 2.9.x（网络请求）
- ExoPlayer / Media3 1.3.x（音乐播放）
- Room（本地数据库）
- Hilt（依赖注入）
- Gradle 8.6

### 管理后台技术栈

- Vue 3.x
- Vite 5.x
- Element Plus 2.x
- Vue Router 4.x
- Pinia 2.x（状态管理）
- Axios 1.x（HTTP 请求）
- ECharts 5.x（数据可视化）
- TypeScript 5.x

### API 设计

**基础 URL**: `http://{server-ip}:8080/api/v1`

| 方法 | 端点 | 描述 |
|------|------|------|
| GET | /music | 获取所有音乐列表（支持分页） |
| GET | /music/{id} | 获取指定音乐信息 |
| GET | /music/{id}/stream | 流式播放音乐 |
| POST | /music/upload | 上传音乐文件 |
| DELETE | /music/{id} | 删除音乐文件 |
| GET | /music/search | 搜索音乐 |
| GET | /playlists | 获取所有歌单 |
| POST | /playlists | 创建歌单 |
| GET | /playlists/{id} | 获取歌单详情 |
| PUT | /playlists/{id} | 更新歌单 |
| DELETE | /playlists/{id} | 删除歌单 |
| POST | /playlists/{id}/songs | 添加歌曲到歌单 |
| DELETE | /playlists/{id}/songs/{songId} | 从歌单移除歌曲 |
| GET | /favorites | 获取收藏列表 |
| POST | /favorites/{songId} | 添加收藏 |
| DELETE | /favorites/{songId} | 取消收藏 |
| GET | /history | 获取播放历史 |

---

## Acceptance Criteria

1. 服务端能够正常启动并监听 8080 端口
2. SQLite 数据库能够正常创建和读写
3. 音乐文件能够上传到指定目录
4. 音乐元数据能够正确提取和存储
5. Android 客户端能够显示音乐列表
6. Android 客户端能够播放远程音乐
7. 项目能够通过 Maven 和 Gradle 正常构建
8. 支持后台播放和锁屏控制
9. 支持音频缓存和离线播放
10. 支持歌单管理和收藏功能
11. 支持歌词展示和同步