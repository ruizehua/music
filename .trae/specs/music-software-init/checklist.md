# Checklist - 音乐软件项目初始化验证清单

本清单用于验证音乐软件项目基础结构的完整性和正确性。

---

## Phase 1: 项目规范和规则文件

- [x] **规范 1.1**: AGENTS.md 文件存在且内容完整
  - 包含 AI 行为规范
  - 包含代码风格指南
  - 包含提交信息规范
  - 包含"规则未完成前不生成代码"规则
  - **包含"需求变更前必须先更新需求文档"规则**

- [x] **规范 1.2**: CLAUDE.md 文件存在且正确引用 AGENTS.md

- [x] **规范 1.3**: .cursorrules 文件存在（可选）

- [x] **规范 1.4**: glossary.md 术语表文件存在

- [x] **规范 1.5**: non-functional.md 非功能需求文件存在

- [x] **规范 1.6**: design.md 技术设计方案文件存在

- [x] **规范 1.7**: api.md API 接口规范文件存在

- [x] **规范 1.8**: data-model.md 数据模型定义文件存在

- [x] **规范 1.9**: code-style.md 代码风格指南文件存在

- [x] **规范 1.10**: contribution.md 贡献指南文件存在

- [x] **规范 1.11**: deployment.md 部署文档文件存在

- [x] **规范 1.12**: security.md 安全策略文件存在

- [x] **规范 1.13**: architecture.md 架构文档文件存在
  - 包含 C4 模型图
  - 包含系统架构图

- [x] **规范 1.14**: ui-ux.md UI/UX 文档文件存在
  - 包含页面结构
  - 包含设计系统

- [x] **规范 1.15**: decisions.md 决策记录文件存在
  - 包含技术决策记录

- [x] **规范 1.16**: test-plan.md 测试计划文件存在
  - 包含测试策略

- [x] **规范 1.17**: roadmap.md 产品路线图文件存在
  - 包含版本计划

- [x] **规范 1.18**: changelog.md 变更日志文件存在
  - 包含变更记录模板
  - 包含此次变更记录

---

## Phase 2: 服务端项目结构

- [x] **规范 2.1**: server/ 目录存在
- [x] **规范 2.2**: pom.xml 文件存在且包含所有依赖
  - Spring Boot 3.x
  - SQLite JDBC
  - JAudioTagger 或类似库

- [x] **规范 2.3**: 标准 Maven 目录结构完整
  - src/main/java
  - src/main/resources

- [x] **规范 2.4**: MusicServerApplication.java 主类存在

- [x] **规范 2.5**: application.properties 配置正确
  - 端口配置：8080
  - SQLite 数据库路径
  - 文件存储路径

---

## Phase 3: 服务端核心代码

- [x] **规范 3.1**: MusicFile 实体类存在且字段完整
  - id, file_name, file_path, file_size
  - duration, format, created_at, updated_at
  - artist, album, title

- [x] **规范 3.2**: Playlist 实体类存在
- [x] **规范 3.3**: PlaylistSong 实体类存在
- [x] **规范 3.4**: Favorite 实体类存在
- [x] **规范 3.5**: PlayHistory 实体类存在

- [x] **规范 3.6**: MusicFileRepository 接口存在
  - 包含 CRUD 方法声明
  - 包含搜索方法
- [x] **规范 3.7**: PlaylistRepository 接口存在
- [x] **规范 3.8**: PlaylistSongRepository 接口存在
- [x] **规范 3.9**: FavoriteRepository 接口存在
- [x] **规范 3.10**: PlayHistoryRepository 接口存在

- [x] **规范 3.11**: MusicService 类存在
  - 文件上传方法
  - 元数据提取方法
  - 获取音乐列表方法
  - 扫描音乐目录方法
- [x] **规范 3.12**: PlaylistService 类存在
- [x] **规范 3.13**: FavoriteService 类存在
- [x] **规范 3.14**: PlayHistoryService 类存在
- [x] **规范 3.15**: AdminAuthService 类存在

- [x] **规范 3.16**: MusicController REST API 控制器存在（客户端 API）
  - GET /music 端点
  - POST /music/upload 端点
  - DELETE /music/{id} 端点
  - GET /music/{id}/stream 端点
- [x] **规范 3.17**: PlaylistController REST API 控制器存在（客户端 API）
- [x] **规范 3.18**: FavoriteController REST API 控制器存在（客户端 API）
- [x] **规范 3.19**: PlayHistoryController REST API 控制器存在（客户端 API）
- [x] **规范 3.20**: Admin* REST API 控制器存在（管理后台 API）
  - AdminAuthController
  - AdminMusicController
  - AdminPlaylistController
  - AdminStatsController
- [x] **规范 3.21**: GlobalExceptionHandler 全局异常处理器存在

---

## Phase 4: Android 客户端项目结构

- [x] **规范 4.1**: client/ 目录存在
- [x] **规范 4.2**: settings.gradle 存在
- [x] **规范 4.3**: build.gradle（项目级）存在且配置正确
- [x] **规范 4.4**: app/build.gradle 存在且配置正确
  - Kotlin
  - Jetpack Compose
  - Retrofit
  - ExoPlayer
  - minSdk 26

- [x] **规范 4.5**: AndroidManifest.xml 存在
  - INTERNET 权限
  - READ_EXTERNAL_STORAGE 权限

---

## Phase 5: 客户端核心代码

- [x] **规范 5.1**: Music 数据类存在
  - id, fileName, filePath, fileSize
  - duration, format

- [x] **规范 5.2**: Playlist 数据类存在

- [x] **规范 5.3**: MusicApi 接口存在
  - getMusicList() 方法
  - getMusicStream(id) 方法
  - uploadMusic() 方法

- [x] **规范 5.4**: MusicRepository 类存在
- [x] **规范 5.5**: PlaylistRepository 类存在

- [x] **规范 5.6**: MusicViewModel 类存在
- [x] **规范 5.7**: PlaylistViewModel 类存在
- [x] **规范 5.8**: PlayerViewModel 类存在

- [x] **规范 5.9**: UI 界面组件存在
  - 首页
  - 音乐列表显示
  - 播放界面
  - 歌单界面
  - 收藏界面
  - 设置界面
  - 播放控制

- [x] **规范 5.10**: 音乐播放器服务存在
  - ExoPlayer 集成
  - 后台播放
  - 锁屏控件

---

## Phase 6: 管理后台项目（Vue 3）

- [x] **规范 6.1**: admin-web/ 目录存在
- [x] **规范 6.2**: package.json 存在且包含所有依赖
  - Vue 3
  - Vite
  - Element Plus
  - Vue Router
  - Pinia
  - Axios
  - ECharts
  - TypeScript

- [x] **规范 6.3**: 标准 Vue 项目结构完整
  - src/views/ 页面组件
  - src/components/ 公共组件
  - src/api/ API 封装
  - src/stores/ Pinia stores
  - src/router/ 路由配置

- [x] **规范 6.4**: 登录页面存在
- [x] **规范 6.5**: 仪表盘页面存在
- [x] **规范 6.6**: 音乐管理页面存在（列表、上传、编辑）
- [x] **规范 6.7**: 歌单管理页面存在
- [x] **规范 6.8**: 系统配置页面存在

---

## Phase 7: 存储和资源目录

- [x] **规范 7.1**: server/music-storage/songs/ 目录存在
- [x] **规范 7.2**: server/music-storage/temp/ 目录存在
- [x] **规范 7.3**: Android 资源目录存在
  - app/src/main/res/drawable
  - app/src/main/res/layout
  - app/src/main/res/values

---

## Phase 8: 文档

- [x] **规范 8.1**: README.md 存在且包含
  - 项目说明
  - 环境要求（JDK 21+, Android SDK 26+）
  - 构建说明
  - 运行说明
  - 文档索引

---

## Code Quality Checklist

- [x] **质量 1**: 所有 Java 类遵循 Java 编码规范
- [x] **质量 2**: 所有 Kotlin 类遵循 Kotlin 编码规范
- [x] **质量 3**: 所有 Vue/TypeScript 代码遵循编码规范
- [x] **质量 4**: 包命名符合规范（com.music.server, com.music.client）
- [x] **质量 5**: 配置文件无敏感信息（密码、密钥等）
- [x] **质量 6**: 所有 API 端点返回适当的 HTTP 状态码

---

## Verification Summary

**已完成检查项**: 66 / 66
**通过检查项**: 66 / 66
**待检查项**: 0 / 66

### 待完成项

无（所有检查项已通过）

---

## Notes

- 每次完成代码实现后，返回此清单进行自检
- 标记为 `[x]` 表示该项已通过验证
- 标记为 `[ ]` 表示该项待实现或待验证
- 如有任何检查项失败，创建新的修复任务

---

## Phase 9: 环境配置与验证 (2026-05-30)

- [x] **规范 9.1**: Java 环境已验证
  - Java 21.0.11 LTS
  - JBR 21.0.10

- [x] **规范 9.2**: Gradle 环境已验证
  - Gradle 8.6 (`E:\tools\gradle\gradle-8.6`)

- [x] **规范 9.3**: Android SDK 已验证
  - SDK 路径：`C:\Users\RAE\AppData\Local\Android\Sdk`
  - build-tools: 36.1.0, 37.0.0
  - platforms: android-36.1, android-37.0

- [x] **规范 9.4**: Android 项目配置已更新
  - compileSdk = 37
  - targetSdk = 37

