# 开发贡献指南

> 本文件定义了音乐软件项目的开发约定，包括分支策略、提交信息规范、代码评审准则和 PR 流程。

---

## 一、分支策略

### 1.1 分支命名规范

| 分支类型 | 命名格式 | 示例 |
|----------|----------|------|
| 主分支 | `main` | main |
| 开发分支 | `develop` | develop |
| 功能分支 | `feature/xxx` | feature/music-upload |
| Bug 修复分支 | `fix/xxx` | fix/playlist-index-bug |
| 发布分支 | `release/xxx` | release/v1.0.0 |
| 热修复分支 | `hotfix/xxx` | hotfix/critical-security |

### 1.2 分支流程

```
main (生产环境)
    ↑
release/* (发布准备)
    ↑
develop (开发主线)
    ↑
feature/* / fix/* (功能开发/Bug修复)
```

### 1.3 分支管理规则

- ✅ 功能开发从 `develop` 分支拉取
- ✅ 完成后合并回 `develop`
- ✅ 发布前从 `develop` 创建 `release` 分支
- ✅ `release` 分支测试通过后合并到 `main`
- ✅ 紧急修复从 `main` 创建 `hotfix` 分支

---

## 二、代码提交规范

### 2.1 提交信息格式

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

### 2.2 Type 类型

| Type | 说明 |
|------|------|
| `feat` | 新功能 |
| `fix` | Bug 修复 |
| `docs` | 文档更新 |
| `style` | 代码格式（不影响功能） |
| `refactor` | 重构 |
| `perf` | 性能优化 |
| `test` | 测试相关 |
| `chore` | 构建/工具变更 |
| `ci` | CI 配置 |
| `revert` | 回退 |

### 2.3 Scope 范围

| Scope | 说明 |
|-------|------|
| api | API 相关 |
| auth | 认证授权 |
| music | 音乐播放相关 |
| playlist | 播放列表相关 |
| ui | 用户界面 |
| db | 数据库相关 |
| android | Android 客户端 |
| backend | Java 后端 |

### 2.4 提交示例

```
feat(music): 添加音乐播放进度显示

- 集成 MediaSession 显示播放进度
- 添加进度拖动功能
- 显示当前播放时间和总时长

Closes #123
```

---

## 三、代码评审准则

### 3.1 PR 提交要求

- ✅ PR 标题清晰描述变更内容
- ✅ PR 描述包含变更原因和影响范围
- ✅ 关联相关的 Issue
- ✅ 所有测试通过
- ✅ 代码格式符合规范

### 3.2 代码审查要点

| 检查项 | 说明 |
|--------|------|
| 代码正确性 | 实现是否符合需求 |
| 代码质量 | 可读性、可维护性 |
| 性能 | 是否有性能问题 |
| 安全性 | 是否有安全风险 |
| 测试覆盖 | 测试是否充分 |

### 3.3 PR 合并规则

- ✅ 至少 1 位 reviewer 批准
- ✅ 无未解决的评论
- ✅ CI 构建通过
- ✅ 无冲突

---

## 四、开发环境设置

### 4.1 后端环境

```bash
# 克隆项目
git clone <repository-url>
cd server

# 构建项目
mvn clean install

# 运行服务
mvn spring-boot:run
```

### 4.2 客户端环境

```bash
# 克隆项目
git clone <repository-url>
cd client

# 构建项目
./gradlew build

# 运行应用（Android Studio）
# 在 Android Studio 中打开项目，选择运行配置
```

---

## 五、Issue 处理流程

### 5.1 Issue 分类

| 标签 | 说明 |
|------|------|
| bug | Bug 报告 |
| feature | 新功能请求 |
| enhancement | 功能改进 |
| documentation | 文档更新 |
| question | 问题咨询 |

### 5.2 Issue 生命周期

1. **新建** → 分配负责人
2. **进行中** → 开发实现
3. **审核中** → PR 代码审查
4. **已解决** → 合并到 develop
5. **已关闭** → 发布后关闭

---

> **最后更新**：2026-05-20
> **版本**：1.0.0