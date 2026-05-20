# 音乐软件项目

> 个人音乐播放系统，包含 Java 后端服务和 Android 客户端应用。

---

## 📋 项目概述

本项目是一个个人使用的音乐播放系统，采用客户端-服务端架构：

- **后端**: Java Spring Boot 3.x
- **客户端**: Android Kotlin + Jetpack Compose
- **数据库**: SQLite（仅存储文件路径和元数据）
- **音乐存储**: 服务端本地文件系统

## 🎵 核心功能

- ✅ 音乐文件上传与管理
- ✅ 音乐元数据提取
- ✅ 音乐列表展示
- ✅ 音乐流式播放
- ✅ 播放列表管理
- ✅ 音乐搜索功能

## 🛠️ 技术栈

### 后端技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 语言 | Java | 17 |
| 框架 | Spring Boot | 3.2.x |
| 数据库 | SQLite | 3.x |
| ORM | Spring Data JPA | 3.2.x |
| 元数据提取 | JAudioTagger | 2.0.x |
| 构建工具 | Maven | 3.9.x |

### 客户端技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 语言 | Kotlin | 1.9.x |
| UI 框架 | Jetpack Compose | 1.6.x |
| 媒体播放 | Media3 / ExoPlayer | 1.3.x |
| 网络请求 | Retrofit | 2.9.x |
| 状态管理 | ViewModel + StateFlow | 2.6.x |
| 构建工具 | Gradle | 8.6 |

## 📁 项目结构

```
music/
├── server/                    # Java 后端
│   ├── src/main/java/         # Java 源码
│   ├── src/main/resources/    # 配置文件
│   ├── music-storage/         # 音乐文件存储
│   └── pom.xml               # Maven 配置
├── client/                    # Android 客户端
│   ├── app/                   # Android 应用
│   ├── build.gradle.kts       # Gradle 配置
│   └── settings.gradle.kts   # 设置文件
├── AGENTS.md                  # AI 行为规范
├── CLAUDE.md                  # Claude 专用规范
├── .cursorrules               # Cursor IDE 规则
├── README.md                  # 项目说明
├── glossary.md                # 术语表
├── spec.md                    # 需求规格
├── non-functional.md          # 非功能需求
├── design.md                  # 技术设计
├── api.md                     # API 接口规范
├── data-model.md              # 数据模型
├── code-style.md              # 代码风格指南
├── contribution.md            # 贡献指南
├── deployment.md              # 部署文档
├── security.md                # 安全策略
└── .trae/specs/               # Trae 规范目录
```

## 🚀 快速开始

### 后端启动

```bash
cd server
mvn clean install
mvn spring-boot:run
```

服务将在 http://localhost:8080 启动。

### 客户端运行

在 Android Studio 中打开 `client` 目录，配置运行即可。

## 📖 API 文档

详细的 API 接口规范请查看 [api.md](api.md)。

## 📝 规范文档

| 文档 | 说明 |
|------|------|
| [AGENTS.md](AGENTS.md) | **AI 行为规范** - 必须遵守的核心规则 |
| [requirements-history.md](requirements-history.md) | **需求变更历史记录** - 所有需求变更必须先在此记录 |
| [glossary.md](glossary.md) | 术语表 |
| [spec.md](.trae/specs/music-software-init/spec.md) | 需求规格 |
| [non-functional.md](non-functional.md) | 非功能需求 |
| [design.md](design.md) | 技术设计 |
| [api.md](api.md) | API 接口规范 |
| [data-model.md](data-model.md) | 数据模型 |
| [code-style.md](code-style.md) | 代码风格指南 |
| [contribution.md](contribution.md) | 贡献指南 |
| [deployment.md](deployment.md) | 部署文档 |
| [security.md](security.md) | 安全策略 |

## 🔒 安全

详细的安全策略请查看 [security.md](security.md)。

## 📄 许可证

MIT License

---

> **最后更新**：2026-05-20
> **版本**：1.0.0