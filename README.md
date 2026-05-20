# 音乐软件项目

> 个人音乐播放系统，包含 Java 后端服务和 Android 客户端应用。

---

## 📋 项目概述

本项目是一个个人使用的音乐播放系统，采用客户端-服务端架构：

- **后端**: Java Spring Boot 3.x
- **客户端**: Android Kotlin + Jetpack Compose
- **管理后台**: Vue 3 + Element Plus
- **数据库**: SQLite（仅存储文件路径和元数据）
- **音乐存储**: 服务端本地文件系统

## 🎵 核心功能

### P0 - 核心必选功能（MVP）
- ✅ 核心播放控制（播放/暂停、上一首/下一首、进度条拖动、播放模式切换）
- ✅ 后台与锁屏播放（APP切后台继续播放、锁屏页显示播放控件、耳机控制）
- ✅ 音频缓存与离线播放（自动缓存、无网络播放）
- ✅ 音乐库管理（歌曲列表、分类筛选、搜索、歌单管理、收藏功能）
- ✅ 基础播放适配（音量调节、均衡器预设、蓝牙设备控制）
- ✅ 管理后台 - 音乐文件管理（上传、编辑、删除、预览）
- ✅ 管理后台 - 登录与权限（管理员登录、权限控制）

### P1 - 进阶常用功能
- 🚧 歌词展示（逐行滚动、精准同步、桌面悬浮歌词）
- 🚧 辅助播放功能（睡眠定时、播放历史、自动续播）
- 🚧 便捷操作能力（桌面小组件、全局搜索、歌曲信息编辑）
- 🚧 视觉与个性化（专辑封面、深色/浅色模式、自定义主题色）
- 🚧 跨端同步（歌单、收藏、播放进度、播放历史）
- 🚧 管理后台 - 歌单管理（创建、编辑、删除、导出）
- 🚧 管理后台 - 用户管理（创建、编辑、禁用、密码重置）
- 🚧 管理后台 - 系统配置（存储配置、播放配置、安全配置）
- 🚧 管理后台 - 数据统计（概览仪表盘、播放统计、用户统计）

### P2 - 锦上添花功能
- ⏳ 播放数据统计（听歌时长、高频播放、月度/年度报告）
- ⏳ 进阶音效（重低音增强、环绕音效、人声增强、自定义均衡器）
- ⏳ 本地音乐融合（扫描本地音频、本地歌曲播放）
- ⏳ 歌单导入导出（歌单分享、批量导入、备份）
- ⏳ 音频格式兼容（FLAC、WAV 无损格式支持）
- ⏳ 多用户管理（子账号、共享曲库、独立数据）

## 🛠️ 技术栈

### 后端技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 语言 | Java | 21 |
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

### 管理后台技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 语言 | TypeScript | 5.x |
| 框架 | Vue | 3.x |
| 构建工具 | Vite | 5.x |
| UI 组件库 | Element Plus | 2.x |
| 状态管理 | Pinia | 2.x |
| HTTP 请求 | Axios | 1.x |
| 图表库 | ECharts | 5.x |

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
├── admin-web/                 # Vue 管理后台
│   ├── src/                   # Vue 源码
│   │   ├── views/             # 页面组件
│   │   ├── components/        # 公共组件
│   │   ├── api/               # API 封装
│   │   ├── store/             # 状态管理
│   │   └── router/            # 路由配置
│   ├── package.json           # npm 配置
│   └── vite.config.ts         # Vite 配置
├── AGENTS.md                  # AI 行为规范
├── CLAUDE.md                  # Claude 专用规范
├── .cursorrules               # Cursor IDE 规则
├── README.md                  # 项目说明
├── 需求规范.md                 # 需求规范
├── requirements-history.md    # 需求变更历史
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

### 管理后台启动

```bash
cd admin-web
pnpm install
pnpm dev
```

管理后台将在 http://localhost:5173 启动。

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