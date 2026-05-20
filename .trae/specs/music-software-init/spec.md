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
| created_at | TEXT | 创建时间（ISO 8601） |
| updated_at | TEXT | 更新时间（ISO 8601） |

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
│       │   └── MusicServerApplication.java
│       └── resources/
│           └── application.properties
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
│           │   ├── ui/          # 界面层
│           │   ├── viewmodel/   # 视图模型
│           │   ├── model/       # 数据模型
│           │   ├── repository/  # 数据仓库
│           │   └── MusicClientApp.java
│           └── res/             # 资源文件
├── build.gradle
└── settings.gradle
```

---

## REMOVED Requirements

无

---

## Technical Requirements

### 服务端技术栈

- Java 17+
- Spring Boot 3.x
- SQLite3
- Maven
- 音乐元数据提取库（如 JAudioTagger）

### 客户端技术栈

- Kotlin
- Android API 26+（Android 8.0）
- Jetpack Compose
- MVVM 架构
- Retrofit（网络请求）
- ExoPlayer（音乐播放）
- Gradle

### API 设计

**基础 URL**: `http://{server-ip}:8080/api`

| 方法 | 端点 | 描述 |
|------|------|------|
| GET | /music | 获取所有音乐列表 |
| GET | /music/{id} | 获取指定音乐信息 |
| GET | /music/{id}/stream | 流式播放音乐 |
| POST | /music/upload | 上传音乐文件 |
| DELETE | /music/{id} | 删除音乐文件 |

---

## Acceptance Criteria

1. 服务端能够正常启动并监听 8080 端口
2. SQLite 数据库能够正常创建和读写
3. 音乐文件能够上传到指定目录
4. 音乐元数据能够正确提取和存储
5. Android 客户端能够显示音乐列表
6. Android 客户端能够播放远程音乐
7. 项目能够通过 Maven 和 Gradle 正常构建
