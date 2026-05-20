# 测试策略与计划

> 本文档定义项目的测试策略、测试类型、覆盖率目标。

---

## 一、测试金字塔

```
        E2E (端到端测试)
            ↓ 少量
    Integration (集成测试)
            ↓ 适中
       Unit (单元测试)
            ↓ 大量
```

---

## 二、测试类型

### 2.1 单元测试 (Unit Tests)

**目标**：测试单个类/函数的逻辑正确性。

**范围**：
- Service 层
- Repository 层
- 工具类
- 纯逻辑函数

**工具**：
- **后端**: JUnit 5 + Mockito
- **Android**: JUnit + Mockk
- **管理后台**: Vitest

**覆盖率目标**：
- 核心业务逻辑：≥ 70%
- 工具类：≥ 80%

---

### 2.2 集成测试 (Integration Tests)

**目标**：测试模块间的交互。

**范围**：
- API 接口测试 (Controller → Service → Repository)
- 数据库操作测试
- 文件存储测试

**工具**：
- Spring Boot Test + TestContainers (可选)
- 真实 SQLite 测试数据库

---

### 2.3 E2E 测试 (End-to-End Tests)

**目标**：从用户角度测试完整流程。

**范围**：
- 核心用户流程（上传音乐、播放音乐、创建歌单）
- 管理后台核心流程

**工具**：
- **Android**: Espresso
- **管理后台**: Playwright / Cypress

---

## 三、Mock 策略

### 3.1 何时使用 Mock

- 外部依赖（数据库、网络、文件系统）
- 耗时的操作
- 非确定性的代码

### 3.2 何时不使用 Mock

- 纯逻辑函数
- 简单的 CRUD
- 可以使用测试数据库的场景

---

## 四、测试环境

### 4.1 测试数据库

- 使用内存 SQLite (H2 或 SQLite 内存模式)
- 每个测试类前清理数据
- 使用 Flyway/Liquibase 管理测试数据

### 4.2 测试配置

- `application-test.yml` 配置
- 禁用外部服务（如不需要）

---

## 五、测试计划

| 阶段 | 活动 |
|------|------|
| **MVP 阶段** | 核心功能单元测试 + 关键集成测试 |
| **迭代 1** | 扩展单元测试 + API 集成测试 |
| **迭代 2** | E2E 测试 + 性能测试 |

---

## 六、测试运行命令

### 后端
```bash
mvn test                    # 运行所有测试
mvn test -Dtest=*ServiceTest # 只运行 Service 测试
mvn verify                  # 运行测试 + 覆盖率检查
```

### Android
```bash
./gradlew test              # 本地单元测试
./gradlew connectedAndroidTest # 设备测试
```

### 管理后台
```bash
npm run test               # Vitest 单元测试
npm run test:e2e          # E2E 测试
```

---

## 七、质量门禁

**合并 PR 前必须满足**：
- 所有测试通过
- 测试覆盖率不降低
- 无代码质量问题（SonarQube/SpotBugs）

---

> **最后更新**：2026-05-21
> **版本**：1.0.0

