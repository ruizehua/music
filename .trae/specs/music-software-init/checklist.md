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

- [x] **规范 1.13**: requirements-history.md 需求变更历史记录文件存在
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

- [ ] **规范 3.1**: MusicFile 实体类存在且字段完整
  - id, file_name, file_path, file_size
  - duration, format, created_at, updated_at

- [ ] **规范 3.2**: MusicRepository 接口存在
  - 包含 CRUD 方法声明

- [ ] **规范 3.3**: MusicService 类存在
  - 文件上传方法
  - 元数据提取方法
  - 获取音乐列表方法

- [ ] **规范 3.4**: MusicController REST API 控制器存在
  - GET /music 端点
  - POST /music/upload 端点
  - DELETE /music/{id} 端点
  - GET /music/{id}/stream 端点

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

- [ ] **规范 5.1**: Music 数据类存在
  - id, fileName, filePath, fileSize
  - duration, format

- [ ] **规范 5.2**: MusicApi 接口存在
  - getMusicList() 方法
  - getMusicStream(id) 方法
  - uploadMusic() 方法

- [ ] **规范 5.3**: MusicRepository 类存在

- [ ] **规范 5.4**: MusicViewModel 类存在

- [ ] **规范 5.5**: 主界面组件存在
  - 音乐列表显示
  - 播放控制

- [ ] **规范 5.6**: 音乐播放器服务存在
  - ExoPlayer 集成

---

## Phase 6: 存储和资源目录

- [x] **规范 6.1**: server/music-storage/songs/ 目录存在
- [x] **规范 6.2**: server/music-storage/temp/ 目录存在
- [x] **规范 6.3**: Android 资源目录存在
  - app/src/main/res/drawable
  - app/src/main/res/layout
  - app/src/main/res/values

---

## Phase 7: 文档

- [x] **规范 7.1**: README.md 存在且包含
  - 项目说明
  - 环境要求（JDK 17+, Android SDK 26+）
  - 构建说明
  - 运行说明
  - 文档索引

---

## Code Quality Checklist

- [ ] **质量 1**: 所有 Java 类遵循 Java 编码规范
- [ ] **质量 2**: 所有 Kotlin 类遵循 Kotlin 编码规范
- [ ] **质量 3**: 包命名符合规范（com.music.server, com.music.client）
- [ ] **质量 4**: 配置文件无敏感信息（密码、密钥等）
- [ ] **质量 5**: 所有 API 端点返回适当的 HTTP 状态码

---

## Verification Summary

**已完成检查项**: 25 / 33
**通过检查项**: 25 / 33
**待检查项**: 8 / 33

### 待完成项

**Phase 3: 服务端核心代码**
- MusicFile 实体类
- MusicRepository 接口
- MusicService 类
- MusicController REST API 控制器

**Phase 5: 客户端核心代码**
- Music 数据类
- MusicApi 接口
- MusicRepository 类
- MusicViewModel 类和 UI 组件

---

## Notes

- 每次完成代码实现后，返回此清单进行自检
- 标记为 `[x]` 表示该项已通过验证
- 标记为 `[ ]` 表示该项待实现或待验证
- 如有任何检查项失败，创建新的修复任务
