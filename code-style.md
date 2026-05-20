# 代码风格指南

> 本文件定义了音乐软件项目的代码风格规范、命名规范、配置说明和注释规范。

---

## 一、Java 代码风格（后端）

### 1.1 命名规范

| 类型 | 规则 | 示例 |
|------|------|------|
| 类名 | UpperCamelCase | `MusicService`, `MusicController` |
| 接口名 | UpperCamelCase，可加 I 前缀 | `MusicRepository`, `IMusicService` |
| 方法名 | lowerCamelCase | `getMusicById()`, `uploadFile()` |
| 变量名 | lowerCamelCase | `filePath`, `musicList` |
| 常量名 | UPPER_SNAKE_CASE | `MAX_FILE_SIZE`, `DEFAULT_PAGE_SIZE` |
| 包名 | 全小写，用点分隔 | `com.music.server.controller` |
| 文件名 | UpperCamelCase | `MusicServerApplication.java` |

### 1.2 代码结构

```java
@RestController
@RequestMapping("/api/v1/music")
public class MusicController {
    
    private final MusicService musicService;
    
    // 构造函数注入
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

### 1.3 最佳实践

- ✅ 使用构造函数注入依赖，避免 `@Autowired` 字段注入
- ✅ 优先使用不可变对象（final 修饰）
- ✅ 异常处理使用自定义业务异常类
- ✅ 使用 `@Valid` 进行参数校验
- ✅ 接口返回统一使用 `ResponseEntity`
- ✅ 方法长度不超过 50 行
- ✅ 类长度不超过 500 行

---

## 二、Kotlin 代码风格（客户端）

### 2.1 命名规范

| 类型 | 规则 | 示例 |
|------|------|------|
| 类名 | UpperCamelCase | `MusicViewModel`, `MusicListScreen` |
| 函数名 | lowerCamelCase | `loadMusic()`, `playSong()` |
| 变量名 | lowerCamelCase | `musicList`, `currentPosition` |
| 常量名 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT`, `BASE_URL` |
| 包名 | 全小写，用点分隔 | `com.music.client.ui` |
| 文件名 | UpperCamelCase | `MainActivity.kt` |

### 2.2 代码结构

```kotlin
class MusicViewModel(
    private val musicRepository: MusicRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<MusicUiState>(MusicUiState.Loading)
    val uiState: StateFlow<MusicUiState> = _uiState
    
    fun loadMusicList() {
        viewModelScope.launch {
            _uiState.value = MusicUiState.Loading
            musicRepository.getMusicList()
                .onSuccess { _uiState.value = MusicUiState.Success(it) }
                .onFailure { _uiState.value = MusicUiState.Error(it.message) }
        }
    }
}
```

### 2.3 最佳实践

- ✅ 优先使用 Kotlin 特性（data class, sealed class）
- ✅ 使用协程进行异步操作
- ✅ 避免使用 `!!` 操作符
- ✅ 使用 `let`, `run`, `also` 等作用域函数
- ✅ 依赖注入使用 Hilt
- ✅ 使用 Jetpack Compose 构建 UI

---

## 三、注释规范

### 3.1 Java 注释

```java
/**
 * 获取音乐列表
 * 
 * @param page 页码，从 0 开始
 * @param size 每页数量
 * @return 分页音乐列表
 */
public ResponseEntity<Page<MusicDTO>> getMusicList(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size
) {
    // 实现逻辑
}
```

### 3.2 Kotlin 注释

```kotlin
/**
 * 加载音乐列表
 * 
 * @param refresh 是否刷新缓存
 */
suspend fun loadMusicList(refresh: Boolean = false): Result<List<Music>> {
    // 实现逻辑
}
```

### 3.3 注释原则

- ✅ 公共方法必须有文档注释
- ✅ 复杂逻辑必须有单行注释
- ✅ 注释说明"为什么"而不是"做什么"
- ✅ 保持注释与代码同步更新

---

## 四、Git 提交规范

### 4.1 提交信息格式

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

### 4.2 Type 类型

| Type | 说明 |
|------|------|
| feat | 新功能 |
| fix | Bug 修复 |
| docs | 文档更新 |
| style | 代码格式 |
| refactor | 重构 |
| perf | 性能优化 |
| test | 测试相关 |
| chore | 构建/工具变更 |

### 4.3 示例

```
feat(music): 添加音乐播放进度显示

- 集成 MediaSession 显示播放进度
- 添加进度拖动功能

Closes #123
```

---

> **最后更新**：2026-05-20
> **版本**：1.0.0