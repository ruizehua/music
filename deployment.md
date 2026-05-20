# 部署文档

> 本文件定义了音乐软件项目的部署架构、环境说明、CI/CD 流程和配置管理。

---

## 一、部署架构

### 1.1 架构图

```
┌─────────────────────────────────────────────────────────────┐
│                    用户客户端                              │
│              Android App (Kotlin)                         │
└──────────────────────────┬────────────────────────────────┘
                           │ HTTP/HTTPS
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                   后端服务                                 │
│         Spring Boot (Java 17) + SQLite                    │
└──────────────────────────┬────────────────────────────────┘
                           │ 文件系统
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                   音乐文件存储                             │
│              /music-storage/songs/                        │
└─────────────────────────────────────────────────────────────┘
```

### 1.2 部署环境

| 环境 | 说明 | URL |
|------|------|-----|
| 开发环境 | 本地开发 | http://localhost:8080 |
| 测试环境 | 测试验证 | http://test.music.example.com |
| 生产环境 | 正式部署 | http://music.example.com |

---

## 二、环境要求

### 2.1 服务端环境

| 项目 | 要求 |
|------|------|
| 操作系统 | Linux (Ubuntu 22.04+) |
| Java 版本 | 17 |
| 内存 | ≥ 512MB |
| 存储 | ≥ 10GB（用于音乐文件存储） |
| 端口 | 8080 |

### 2.2 客户端环境

| 项目 | 要求 |
|------|------|
| Android 版本 | API 26+ (Android 8.0) |
| 最低 RAM | 2GB |
| 存储空间 | ≥ 1GB |

---

## 三、配置管理

### 3.1 服务端配置

**application.properties 关键配置**:

```properties
# 服务端口
server.port=8080

# 数据库配置
spring.datasource.url=jdbc:sqlite:./music-server.db
spring.datasource.driver-class-name=org.sqlite.JDBC

# 音乐文件存储路径
music.storage.path=./music-storage/songs
music.temp.path=./music-storage/temp

# 文件上传限制
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```

### 3.2 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| SERVER_PORT | 服务端口 | 8080 |
| DB_PATH | 数据库路径 | ./music-server.db |
| STORAGE_PATH | 音乐存储路径 | ./music-storage/songs |

---

## 四、CI/CD 流程

### 4.1 后端 CI/CD

```
代码提交 → 自动构建 → 运行测试 → 部署到测试环境
                                     ↓
                              手动批准 → 部署到生产环境
```

### 4.2 客户端 CI/CD

```
代码提交 → 自动构建 → 运行测试 → 生成 APK → 发布到应用商店
```

---

## 五、部署步骤

### 5.1 服务端部署

```bash
# 克隆项目
git clone <repository-url>
cd server

# 构建项目
mvn clean package -DskipTests

# 创建存储目录
mkdir -p music-storage/songs
mkdir -p music-storage/temp

# 运行服务
java -jar target/music-server-1.0.0.jar
```

### 5.2 客户端部署

```bash
# 克隆项目
git clone <repository-url>
cd client

# 构建 APK
./gradlew assembleRelease

# APK 位置
# app/build/outputs/apk/release/app-release.apk
```

---

> **最后更新**：2026-05-20
> **版本**：1.0.0