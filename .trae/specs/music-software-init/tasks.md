# Tasks - 音乐软件初始化

本任务列表用于创建音乐软件项目的基础结构和规范文件。

---

## Phase 1: 项目规范和规则文件

- [x] **Task 1.1**: 创建 AGENTS.md 文件
  - 定义 AI 助手行为规范
  - 规范代码风格要求
  - 定义提交信息格式
  - 创建时间：预计 5 分钟

- [x] **Task 1.2**: 创建 CLAUDE.md 文件
  - Claude Code 专用项目说明
  - 包含 AGENTS.md 的引用
  - 创建时间：预计 3 分钟

- [x] **Task 1.3**: 创建 .cursorrules 文件（可选）
  - Cursor IDE 项目规则
  - 创建时间：预计 3 分钟

- [x] **Task 1.4**: 创建 glossary.md 术语表
  - 定义项目关键术语和领域语言

- [x] **Task 1.5**: 创建 non-functional.md 非功能需求
  - 性能指标、安全要求、可用性等

- [x] **Task 1.6**: 创建 design.md 技术设计方案
  - 架构决策、技术栈选型、模块划分

- [x] **Task 1.7**: 创建 api.md API 接口规范
  - 接口列表、请求/响应结构、错误码

- [x] **Task 1.8**: 创建 data-model.md 数据模型定义
  - 实体、字段、关系、索引设计

- [x] **Task 1.9**: 创建 code-style.md 代码风格指南
  - 命名规范、代码结构、注释规范

- [x] **Task 1.10**: 创建 contribution.md 贡献指南
  - 分支策略、提交规范、代码评审

- [x] **Task 1.11**: 创建 deployment.md 部署文档
  - 部署架构、环境说明、CI/CD 流程

- [x] **Task 1.12**: 创建 security.md 安全策略
  - 认证授权、数据加密、漏洞防护

- [x] **Task 1.13**: 创建 architecture.md 架构文档
  - C4 模型图、系统架构图

- [x] **Task 1.14**: 创建 ui-ux.md UI/UX 文档
  - 页面结构、设计系统、交互规范

- [x] **Task 1.15**: 创建 decisions.md 决策记录
  - 技术决策记录

- [x] **Task 1.16**: 创建 test-plan.md 测试计划
  - 测试策略、测试类型

- [x] **Task 1.17**: 创建 roadmap.md 产品路线图
  - 版本计划、里程碑

- [x] **Task 1.18**: 创建 changelog.md 变更日志
  - 需求变更历史记录

---

## Phase 2: 服务端项目结构（Java Spring Boot）

- [x] **Task 2.1**: 创建服务端基础项目结构
  - 创建 server/ 目录
  - 创建标准 Maven 项目结构
  - 创建时间：预计 10 分钟

- [x] **Task 2.2**: 创建 pom.xml 配置文件
  - 添加 Spring Boot 依赖
  - 添加 SQLite JDBC 驱动
  - 添加音乐元数据提取库（JAudioTagger）
  - 创建时间：预计 10 分钟

- [x] **Task 2.3**: 创建服务端主应用类
  - MusicServerApplication.java
  - Spring Boot 启动配置
  - 创建时间：预计 5 分钟

- [x] **Task 2.4**: 创建 application.properties
  - 服务端口配置（8080）
  - 数据库连接配置
  - 文件存储路径配置
  - 创建时间：预计 5 分钟

---

## Phase 3: 服务端核心代码

- [x] **Task 3.1**: 创建数据模型
  - MusicFile 实体类
  - Playlist 实体类
  - PlaylistSong 实体类
  - Favorite 实体类
  - PlayHistory 实体类
  - 数据库表结构映射
  - 创建时间：预计 20 分钟

- [x] **Task 3.2**: 创建数据库访问层
  - MusicFileRepository 接口
  - PlaylistRepository 接口
  - PlaylistSongRepository 接口
  - FavoriteRepository 接口
  - PlayHistoryRepository 接口
  - SQLite 数据库初始化
  - 创建时间：预计 20 分钟

- [x] **Task 3.3**: 创建业务逻辑层
  - MusicService 服务类
  - PlaylistService 服务类
  - FavoriteService 服务类
  - PlayHistoryService 服务类
  - AdminAuthService 服务类
  - 文件上传逻辑
  - 元数据提取逻辑（JAudioTagger）
  - 创建时间：预计 40 分钟

- [x] **Task 3.4**: 创建 REST API 控制器
  - MusicController (客户端 API)
  - PlaylistController (客户端 API)
  - FavoriteController (客户端 API)
  - PlayHistoryController (客户端 API)
  - AdminAuthController (管理后台 API)
  - AdminMusicController (管理后台 API)
  - AdminPlaylistController (管理后台 API)
  - AdminStatsController (管理后台 API)
  - GlobalExceptionHandler (全局异常处理)
  - 路由：GET /music, POST /music/upload, DELETE /music/{id}
  - 创建时间：预计 45 分钟

---

## Phase 4: 客户端项目结构（Android）

- [x] **Task 4.1**: 创建 Android 客户端基础项目结构
  - 创建 client/ 目录
  - 创建标准 Android 项目结构
  - 创建时间：预计 10 分钟

- [x] **Task 4.2**: 创建 Gradle 配置文件
  - settings.gradle
  - build.gradle（项目级）
  - app/build.gradle（应用级）
  - 创建时间：预计 15 分钟

- [x] **Task 4.3**: 创建 AndroidManifest.xml
  - 网络权限声明
  - 存储权限声明
  - 创建时间：预计 5 分钟

---

## Phase 5: 客户端核心代码

- [x] **Task 5.1**: 创建数据模型
  - Music 数据类
  - Playlist 数据类
  - 创建时间：预计 10 分钟

- [x] **Task 5.2**: 创建网络层
  - Retrofit API 接口
  - 网络客户端配置
  - 创建时间：预计 15 分钟

- [x] **Task 5.3**: 创建数据仓库
  - MusicRepository 类
  - PlaylistRepository 类
  - 创建时间：预计 15 分钟

- [x] **Task 5.4**: 创建 ViewModel
  - MusicViewModel
  - PlaylistViewModel
  - PlayerViewModel
  - 创建时间：预计 15 分钟

- [x] **Task 5.5**: 创建 UI 界面
  - 首页
  - 音乐列表界面
  - 播放界面
  - 歌单界面
  - 收藏界面
  - 设置界面
  - 创建时间：预计 40 分钟

- [x] **Task 5.6**: 创建音乐播放服务
  - ExoPlayer 集成
  - 音乐播放器服务
  - 后台播放
  - 锁屏控件
  - 创建时间：预计 30 分钟

---

## Phase 6: 管理后台项目（Vue 3）

- [x] **Task 6.1**: 创建管理后台项目结构
  - 创建 admin-web/ 目录
  - 创建 Vite + Vue 3 项目
  - 创建时间：预计 15 分钟

- [x] **Task 6.2**: 配置依赖
  - Vue 3
  - Vue Router
  - Pinia
  - Element Plus
  - Axios
  - ECharts
  - TypeScript
  - 创建时间：预计 15 分钟

- [x] **Task 6.3**: 创建页面和组件
  - 登录页
  - 仪表盘
  - 音乐管理页（列表、上传、编辑）
  - 歌单管理页
  - 系统配置页
  - 创建时间：预计 60 分钟

- [x] **Task 6.4**: 创建 API 层
  - API 封装
  - Token 管理
  - 创建时间：预计 15 分钟

- [x] **Task 6.5**: 创建状态管理
  - Pinia stores
  - 创建时间：预计 15 分钟

---

## Phase 7: 存储和资源目录

- [x] **Task 7.1**: 创建服务端音乐存储目录
  - server/music-storage/songs/
  - server/music-storage/temp/
  - 创建时间：预计 2 分钟

- [x] **Task 7.2**: 创建客户端资源目录
  - drawable/, layout/, values/
  - 创建时间：预计 5 分钟

---

## Phase 8: 文档和 README

- [x] **Task 8.1**: 创建 README.md
  - 项目说明
  - 环境要求
  - 构建说明
  - 使用说明
  - 创建时间：预计 10 分钟

---

## Task Dependencies

```
Phase 1 (Task 1.1-1.18)
    ↓
Phase 2 (Task 2.1-2.4) 依赖于 Phase 1
    ↓
Phase 3 (Task 3.1-3.4) 依赖于 Phase 2
    ↓
Phase 4 (Task 4.1-4.3) 依赖于 Phase 1
    ↓
Phase 5 (Task 5.1-5.6) 依赖于 Phase 4
    ↓
Phase 6 (Task 6.1-6.5) 依赖于 Phase 1
    ↓
Phase 7 (Task 7.1-7.2) 依赖于 Phase 2, Phase 4
    ↓
Phase 8 (Task 8.1) 依赖于所有 Phase
```

---

## Summary

- **总任务数**: 40+
- **预计总时间**: 约 8-10 小时
- **关键路径**: Phase 1 → Phase 2 → Phase 3 → Phase 4 → Phase 5 / Phase 6
- **并行化可能性**: Phase 3 (服务端)、Phase 5 (Android)、Phase 6 (管理后台) 可以并行进行

---

## Next Steps

完成本任务后，将进入实际的代码实现阶段。按照 Phase 顺序逐步实现：
1. 首先完成 Phase 1-3（服务端）
2. 同时可以并行进行 Phase 4-5（Android 客户端）和 Phase 6（管理后台）
3. 最后进行测试和文档完善

---

## Phase 9: 环境配置与验证 (2026-05-30)

- [x] **Task 9.1**: 验证 Java 环境
  - Java 21.0.11 已安装
  - JBR (JetBrains Runtime) 在 `E:\language\Android\jbr`
  - 创建时间：已完成

- [x] **Task 9.2**: 验证 Gradle 环境
  - Gradle 8.6 已安装 (`E:\tools\gradle\gradle-8.6`)
  - 创建时间：已完成

- [x] **Task 9.3**: 验证 Android SDK
  - SDK 路径：`C:\Users\RAE\AppData\Local\Android\Sdk`
  - build-tools: 36.1.0, 37.0.0
  - platforms: android-36.1, android-37.0
  - 创建时间：已完成

- [x] **Task 9.4**: 更新 Android 项目配置
  - `client/app/build.gradle.kts`
  - compileSdk = 37, targetSdk = 37
  - 创建时间：已完成

