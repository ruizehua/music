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

## 二、Spring Boot 开发规范

### 2.1 项目结构规范

```
server/
├── src/main/java/com/music/server/
│   ├── controller/     # REST 控制器（对外暴露 API）
│   ├── service/        # 业务逻辑层
│   ├── repository/     # 数据访问层（基于 Spring Data JPA）
│   ├── model/          # 数据库实体类
│   ├── dto/            # 数据传输对象（请求/响应结构）
│   ├── config/         # 配置类（数据源、跨域、Swagger 等）
│   ├── exception/      # 异常处理（全局异常处理器）
│   └── MusicServerApplication.java
└── src/main/resources/
    └── application.yml
```

### 2.2 Controller 层规范

```java
@RestController
@RequestMapping("/api/v1/music")
@RequiredArgsConstructor
public class MusicController {
    
    private final MusicService musicService;
    
    @GetMapping
    public ResponseEntity<Page<MusicDTO>> list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(musicService.findAll(page, size));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getById(@PathVariable Long id) {
        return musicService.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("Music not found"));
    }
}
```

### 2.3 Service 层规范

```java
@Service
@RequiredArgsConstructor
public class MusicService {
    
    private final MusicRepository musicRepository;
    private final MusicFileStorage storage;
    
    @Transactional
    public MusicDTO create(MusicCreateRequest request) {
        // 业务逻辑
        Music music = Music.builder()
            .fileName(request.getFileName())
            .build();
        return MusicDTO.fromEntity(musicRepository.save(music));
    }
}
```

### 2.4 DTO 规范

```java
public record MusicDTO(
    Long id,
    String fileName,
    String filePath,
    Long fileSize,
    Integer duration,
    String format,
    String createdAt
) {
    public static MusicDTO fromEntity(Music music) {
        return new MusicDTO(
            music.getId(),
            music.getFileName(),
            music.getFilePath(),
            music.getFileSize(),
            music.getDuration(),
            music.getFormat(),
            music.getCreatedAt()
        );
    }
}
```

### 2.5 异常处理规范

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
    }
}
```

---

## 三、分层架构规范

### 3.1 架构层次

| 层级 | 职责 | 说明 |
|------|------|------|
| Controller | 接收请求、返回响应 | 不包含业务逻辑 |
| Service | 业务逻辑处理 | 事务边界 |
| Repository | 数据访问 | 与数据库交互 |
| Model | 数据模型 | 数据库实体 |
| DTO | 数据传输 | 请求/响应结构 |

### 3.2 依赖原则

- ✅ Controller 只依赖 Service
- ✅ Service 只依赖 Repository 和其他 Service
- ✅ Repository 只依赖 Model
- ✅ DTO 用于层间数据传递
- ❌ 禁止跨层直接依赖

### 3.3 事务管理

- ✅ 在 Service 层使用 `@Transactional`
- ✅ 避免在 Controller 层开启事务
- ✅ 合理设置事务传播级别

---

## 四、Java 设计规范

### 4.1 SOLID 原则

| 原则 | 说明 |
|------|------|
| 单一职责 | 一个类只负责一个功能 |
| 开闭原则 | 对扩展开放，对修改关闭 |
| 里氏替换 | 子类可以替换父类 |
| 接口隔离 | 使用细粒度接口 |
| 依赖倒置 | 依赖抽象而非具体实现 |

### 4.2 常用设计模式

| 模式 | 应用场景 |
|------|----------|
| 工厂模式 | 对象创建复杂时 |
| 单例模式 | 全局唯一实例 |
| 策略模式 | 算法可替换时 |
| 模板方法 | 固定流程有变化点 |
| 观察者模式 | 事件通知场景 |

### 4.3 代码质量规范

- ✅ 方法参数不超过 5 个
- ✅ 避免深度嵌套（不超过 3 层）
- ✅ 使用 Optional 处理空值
- ✅ 避免魔法数字，使用常量
- ✅ 资源使用 try-with-resources

---

## 五、数据库表设计规范

### 5.1 命名规范

| 类型 | 规则 | 示例 |
|------|------|------|
| 表名 | 小写，下划线分隔 | `music_files`, `playlists` |
| 字段名 | 小写，下划线分隔 | `file_name`, `created_at` |
| 主键 | 使用 `id`，自增 | `id INTEGER PRIMARY KEY AUTOINCREMENT` |
| 外键 | 表名_id | `playlist_id`, `music_id` |

### 5.2 字段设计

| 字段类型 | 用途 |
|----------|------|
| INTEGER | 数值类型（ID、数量等） |
| TEXT | 文本（名称、描述等） |
| REAL | 浮点数（评分等） |
| BLOB | 二进制数据（较少使用） |
| BOOLEAN | 布尔值 |

### 5.3 约束规范

- ✅ 主键非空唯一
- ✅ 外键引用完整性
- ✅ 必要字段非空约束
- ✅ 合理的索引设计
- ✅ 使用事务保证数据一致性

### 5.4 表结构示例

```sql
CREATE TABLE IF NOT EXISTS music_files (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_name TEXT NOT NULL,
    file_path TEXT NOT NULL UNIQUE,
    file_size INTEGER NOT NULL,
    duration INTEGER,
    format TEXT NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_music_files_file_path ON music_files(file_path);
CREATE INDEX idx_music_files_created_at ON music_files(created_at);
```

---

## 六、Android Kotlin 代码风格（客户端）

### 6.1 命名规范

| 类型 | 规则 | 示例 |
|------|------|------|
| 类名 | UpperCamelCase | `MusicViewModel`, `MusicListScreen` |
| 函数名 | lowerCamelCase | `loadMusic()`, `playSong()` |
| 变量名 | lowerCamelCase | `musicList`, `currentPosition` |
| 常量名 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT`, `BASE_URL` |
| 包名 | 全小写，用点分隔 | `com.music.client.ui` |
| 文件名 | UpperCamelCase | `MainActivity.kt` |

### 6.2 代码结构

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

### 6.3 最佳实践

- ✅ 优先使用 Kotlin 特性（data class, sealed class）
- ✅ 使用协程进行异步操作
- ✅ 避免使用 `!!` 操作符
- ✅ 使用 `let`, `run`, `also` 等作用域函数
- ✅ 依赖注入使用 Hilt
- ✅ 使用 Jetpack Compose 构建 UI

### 6.4 Android 架构规范

```
client/app/src/main/java/com/music/client/
├── data/           # 数据层
│   ├── remote/     # 远程数据源（Retrofit）
│   ├── local/      # 本地数据源（Room）
│   └── repository/ # 仓库实现
├── domain/         # 领域层
│   ├── model/      # 领域模型
│   └── repository/ # 仓库接口
├── ui/             # UI 层
│   ├── screens/    # 页面组件
│   ├── components/ # UI 组件
│   └── viewmodel/  # ViewModel
├── di/             # 依赖注入（Hilt）
└── util/           # 工具类
```

---

## 七、前端开发规范（通用）

### 7.1 JavaScript/TypeScript 规范

| 类型 | 规则 | 示例 |
|------|------|------|
| 变量 | camelCase | `const musicList = []` |
| 常量 | UPPER_CAMEL_CASE | `const MAX_RETRY = 3` |
| 函数 | camelCase | `function fetchMusic() {}` |
| 类 | PascalCase | `class MusicPlayer {}` |
| 文件 | kebab-case | `music-player.js` |

### 7.2 代码风格

```javascript
// 使用 const/let，避免 var
const music = { id: 1, title: 'Song' };

// 箭头函数
const playMusic = async (id) => {
  const result = await fetch(`/api/music/${id}`);
  return result.json();
};

// 解构赋值
const { title, artist } = music;
```

---

## 八、注释规范

### 8.1 Java 注释

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

### 8.2 Kotlin 注释

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

### 8.3 注释原则

- ✅ 公共方法必须有文档注释
- ✅ 复杂逻辑必须有单行注释
- ✅ 注释说明"为什么"而不是"做什么"
- ✅ 保持注释与代码同步更新

---

> **最后更新**：2026-05-20
> **版本**：1.0.0