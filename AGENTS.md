# AI 行为规范 - 音乐软件项目

> 本文件定义了 AI 助手在本项目中的行为准则、代码规范和禁止事项。
> 所有 AI 工具在处理本项目时必须遵循本规范。

---

## 项目概述

### 项目类型
音乐软件，采用前后端分离架构：
- **后端**：Java（Spring Boot）
- **客户端**：Android（Kotlin）
- **目标**：为用户提供音乐播放、发现、管理的一站式体验

### 核心功能
1. 用户管理与认证
2. 在线音乐播放
3. 本地音乐导入
4. 播放列表管理
5. 音乐发现与推荐
6. 搜索功能（歌曲、歌手、歌词）

---

## AI 行为准则

### 1. 任务执行原则
- **理解优先**：在编写代码前，先理解需求和现有代码结构
- **增量修改**：优先进行小而安全的改动，避免大规模重构除非必要
- **测试验证**：完成功能后运行相关测试确保正确性
- **保持一致**：遵循项目现有的代码风格和架构模式

### 2. 决策优先级
1. 遵循现有代码风格和架构
2. 优先使用项目已有的工具和库
3. 考虑向后兼容性和 API 稳定性
4. 性能与可维护性的平衡

### 3. 交流与反馈
- 明确说明代码改动的原因和影响
- 提供多个方案时，说明各方案优缺点
- 遇到不确定的问题时，主动询问确认
- 复杂变更前先获得用户确认

---

## 代码风格规范

### Java（后端 Spring Boot）

#### 命名规范
```java
// 类名：UpperCamelCase
public class MusicService { }

// 接口名：以 I 开头或使用后缀 -er
public interface MusicRepository { }
public interface MusicService { }

// 方法名：lowerCamelCase
public void playMusic(Long musicId) { }

// 常量：UPPER_SNAKE_CASE
public static final int MAX_PLAYLIST_SIZE = 1000;

// 成员变量：lowerCamelCase
private String userName;

// 包名：小写
package com.music.player.service;
```

#### 代码结构
```java
@RestController
@RequestMapping("/api/v1/music")
public class MusicController {
    
    private final MusicService musicService;
    
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getMusic(@PathVariable Long id) {
        return musicService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
```

#### 最佳实践
- 使用构造函数注入依赖
- 避免使用 `@Autowired` 字段注入
- 优先使用不可变对象（Immutability）
- 异常处理使用自定义业务异常类
- 使用 `@Valid` 进行参数校验
- 接口返回统一使用 `ResponseEntity`

### Kotlin（Android 客户端）

#### 命名规范
```kotlin
// 类名：UpperCamelCase
class MusicPlayer { }

// 函数名：lowerCamelCase
fun playMusic(musicId: Long) { }

// 变量名：lowerCamelCase
val userName: String = ""

// 常量：UPPER_SNAKE_CASE
const val MAX_RETRY_COUNT = 3

// 包名：小写
package com.music.player.ui
```

#### 代码结构
```kotlin
class MusicViewModel(
    private val musicRepository: MusicRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<MusicUiState>(MusicUiState.Loading)
    val uiState: StateFlow<MusicUiState> = _uiState
    
    fun loadMusic(musicId: Long) {
        viewModelScope.launch {
            _uiState.value = MusicUiState.Loading
            musicRepository.getMusic(musicId)
                .onSuccess { _uiState.value = MusicUiState.Success(it) }
                .onFailure { _uiState.value = MusicUiState.Error(it.message) }
        }
    }
}
```

#### 最佳实践
- 优先使用 Kotlin 特性和协程
- 使用数据类表示数据模型
- 使用 sealed class 表示 UI 状态
- 避免使用 `!!` 操作符
- 使用 `let`, `run`, `also` 等作用域函数
- 依赖注入使用 Hilt
- 使用 Jetpack Compose 构建 UI

---

## 提交信息规范（Conventional Commits）

### 格式
```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

### Type 类型
| Type | 说明 |
|------|------|
| `feat` | 新功能 |
| `fix` | Bug 修复 |
| `docs` | 文档更新 |
| `style` | 代码格式（不影响功能） |
| `refactor` | 重构（不是新功能也不是修复） |
| `perf` | 性能优化 |
| `test` | 测试相关 |
| `chore` | 构建/工具变更 |
| `ci` | CI 配置 |
| `revert` | 回退 |

### Scope 范围
| Scope | 说明 |
|-------|------|
| `api` | API 相关 |
| `auth` | 认证授权 |
| `music` | 音乐播放相关 |
| `playlist` | 播放列表相关 |
| `user` | 用户管理 |
| `ui` | 用户界面 |
| `db` | 数据库相关 |
| `android` | Android 客户端 |
| `backend` | Java 后端 |

### 示例
```
feat(music): 添加音乐播放进度显示

- 集成 MediaSession 显示播放进度
- 添加进度拖动功能
- 显示当前播放时间和总时长

Closes #123
```

```
fix(playlist): 修复播放列表删除歌曲后索引越界问题

当删除当前播放歌曲时，正确更新播放索引。
```

```
docs(api): 更新音乐搜索接口文档

添加请求示例和响应字段说明。
```

---

## 禁止事项

### 1. 禁止的行为
- ❌ **不要**修改用户明确标记为"不要修改"的文件
- ❌ **不要**提交包含敏感信息（API Key、Token、密码等）的代码
- ❌ **不要**进行未经确认的大规模重构（超过 10 个文件）
- ❌ **不要**忽略编译错误或测试失败
- ❌ **不要**硬编码配置值，应使用配置文件或环境变量
- ❌ **不要**直接操作数据库，应通过 ORM 或 Repository
- ❌ **不要**在主分支直接提交，应通过 Pull Request
- ❌ **不要**在规范规则未完成前生成代码，必须等待用户确认规则文档已完成
- ❌ **绝对不要**在未先更新需求文档的情况下修改或新增需求！必须严格按照以下流程：
  1. 先更新 [requirements-history.md](requirements-history.md) 记录此次变更
  2. 同步更新相关的需求文档（如 [spec.md](.trae/specs/music-software-init/spec.md)）
  3. **然后才可以**继续生成代码实现功能
- ❌ **不要**在提交后忘记推送到远程仓库！**每次 git commit 后必须立即执行 git push**，确保代码及时同步到 GitHub

### 2. 安全红线
- ❌ **绝对禁止**在代码中硬编码密码、密钥
- ❌ **绝对禁止**使用不安全的加密算法
- ❌ **绝对禁止**将用户隐私数据记录到日志
- ❌ **绝对禁止**跳过安全审计的代码提交

### 3. 性能红线
- ❌ **不要**在主线程执行 I/O 操作（Android）
- ❌ **不要**创建不必要的对象导致内存泄漏
- ❌ **不要**使用 SELECT * 查询
- ❌ **不要**忽略数据库索引设计

---

## 技术栈说明

### 后端技术栈
- **框架**：Spring Boot 3.x
- **语言**：Java 17+
- **ORM**：Spring Data JPA / MyBatis
- **数据库**：MySQL
- **缓存**：Redis
- **安全**：Spring Security + JWT
- **构建**：Maven
- **API**：RESTful API

### Android 客户端技术栈
- **语言**：Kotlin 1.9+
- **最小版本**：Android API 24 (Android 7.0)
- **目标版本**：Android API 34
- **UI**：Jetpack Compose
- **架构**：MVVM + Clean Architecture
- **DI**：Hilt
- **网络**：Retrofit + OkHttp
- **本地存储**：Room
- **异步**：Kotlin Coroutines + Flow
- **媒体**：Media3 / ExoPlayer
- **构建**：Gradle (Kotlin DSL)

### 开发工具
- **后端 IDE**：IntelliJ IDEA
- **Android IDE**：Android Studio
- **版本控制**：Git
- **API 测试**：Postman / Insomnia

---

## 项目结构

### 后端结构
```
backend/
├── src/main/java/com/music/
│   ├── controller/     # REST 控制器
│   ├── service/        # 业务逻辑
│   ├── repository/     # 数据访问
│   ├── model/          # 实体类
│   ├── dto/            # 数据传输对象
│   ├── config/         # 配置类
│   ├── security/       # 安全相关
│   └── exception/      # 异常处理
├── src/main/resources/
│   └── application.yml # 配置文件
└── pom.xml
```

### Android 结构
```
app/
├── src/main/java/com/music/player/
│   ├── data/           # 数据层
│   │   ├── remote/     # 远程数据源
│   │   ├── local/      # 本地数据源
│   │   └── repository/ # 仓库实现
│   ├── domain/         # 领域层
│   │   ├── model/      # 领域模型
│   │   └── repository/ # 仓库接口
│   ├── ui/             # UI 层
│   │   ├── screens/    # 页面
│   │   ├── components/ # 组件
│   │   └── viewmodel/  # ViewModel
│   ├── di/             # 依赖注入
│   └── util/           # 工具类
└── build.gradle.kts
```

---

## 验收标准

### 代码质量
- ✅ 所有代码通过静态分析检查
- ✅ 单元测试覆盖核心业务逻辑
- ✅ 无硬编码的敏感信息
- ✅ 遵循命名规范和代码结构

### 功能实现
- ✅ 实现的功能符合需求规范
- ✅ 通过功能验收清单中的测试场景
- ✅ 无明显的功能缺陷

### 文档维护
- ✅ API 接口有完整的文档说明
- ✅ 关键代码有必要的注释
- ✅ 更新相关规范文档（如需要）

---

> **最后更新**：2026-05-20
> **版本**：1.0.0
