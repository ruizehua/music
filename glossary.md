# 音乐软件项目术语表

> 本文件定义了项目中使用的关键术语和领域语言，确保团队成员之间的沟通一致，消除歧义。

---

## A

### API（Application Programming Interface）
应用程序编程接口。本项目中指后端提供的 RESTful API，用于客户端与服务端之间的数据交互。

### Authentication（认证）
验证用户身份的过程。本项目采用 JWT（JSON Web Token）进行用户认证。

---

## B

### Backend（后端）
指服务端应用，使用 Java Spring Boot 开发，负责处理业务逻辑、数据存储和 API 提供。

### Build（构建）
将源代码编译、打包成可执行文件的过程。后端使用 Maven，客户端使用 Gradle。

---

## C

### Client（客户端）
指 Android 移动应用，使用 Kotlin 开发，提供用户界面和交互功能。

### Coroutine（协程）
Kotlin 中的轻量级并发编程工具，用于异步操作和非阻塞代码。

---

## D

### Database（数据库）
存储应用数据的系统。本项目使用 SQLite 存储音乐文件路径和元数据。

### DTO（Data Transfer Object）
数据传输对象。用于在不同层之间传递数据，通常用于 API 请求和响应。

---

## E

### Entity（实体）
数据库表对应的 Java/Kotlin 类，表示业务领域中的对象。

### ExoPlayer
Google 提供的 Android 媒体播放器库，用于音乐播放功能。

---

## F

### Feature（功能）
应用提供的具体能力，如音乐播放、播放列表管理等。

### File Storage（文件存储）
音乐文件的存储位置，服务端本地文件系统。

---

## G

### Gradle
Android 项目的构建工具，用于编译、测试和打包应用。

---

## J

### JWT（JSON Web Token）
用于安全传输信息的开放标准，用于用户认证和授权。

---

## M

### Media3
Google 的媒体库套件，包含 ExoPlayer，用于音频和视频播放。

### Metadata（元数据）
描述数据的数据。在本项目中指音乐文件的信息：歌名、艺术家、专辑、时长等。

### Model（模型）
表示数据结构的类，包括实体类和数据传输对象。

### Maven
Java 项目的构建工具，用于管理依赖和构建过程。

### MVVM（Model-View-ViewModel）
一种软件架构模式，将 UI 层与业务逻辑分离。

---

## P

### Playlist（播放列表）
用户创建的歌曲集合，用于组织和管理音乐。

### POJO（Plain Old Java Object）
普通 Java 对象，不依赖特定框架的简单类。

---

## R

### Repository（仓库）
数据访问层，负责与数据库交互，提供数据查询和操作方法。

### Retrofit
Android 中用于网络请求的库，简化 REST API 调用。

---

## S

### Service（服务）
业务逻辑层，处理核心业务规则和数据处理。

### SQLite
轻量级关系型数据库，本项目中用于存储音乐元数据和文件路径。

### Spring Boot
Java 后端框架，用于快速构建 RESTful 服务。

### StateFlow
Kotlin 协程中的数据流，用于响应式状态管理。

---

## U

### UI（User Interface）
用户界面，用户与应用交互的视觉部分。

### URI（Uniform Resource Identifier）
统一资源标识符，用于标识资源的位置。

---

## V

### ViewModel
MVVM 架构中的视图模型，管理 UI 状态和业务逻辑。

---

## 附录：缩写对照表

| 缩写 | 全称 | 中文含义 |
|------|------|----------|
| API | Application Programming Interface | 应用程序编程接口 |
| CI/CD | Continuous Integration/Continuous Deployment | 持续集成/持续部署 |
| DTO | Data Transfer Object | 数据传输对象 |
| JWT | JSON Web Token | JSON Web 令牌 |
| MVVM | Model-View-ViewModel | 模型-视图-视图模型 |
| ORM | Object-Relational Mapping | 对象关系映射 |
| POJO | Plain Old Java Object | 普通 Java 对象 |
| REST | Representational State Transfer | 表述性状态传递 |
| URI | Uniform Resource Identifier | 统一资源标识符 |

---

> **最后更新**：2026-05-20
> **版本**：1.0.0