# UI/UX 设计规范

> 本文档定义音乐软件项目的用户界面与交互设计，包括页面结构、组件树、设计系统。

---

## 一、Android 客户端页面结构

### 1.1 页面导航

```
首页 (Home)
├── 音乐库 (Library)
│   ├── 全部音乐
│   ├── 按艺术家
│   ├── 按专辑
│   └── 搜索
├── 歌单 (Playlists)
│   ├── 收藏歌单
│   └── 自定义歌单
├── 播放 (Player)
│   └── 全屏播放页
└── 设置 (Settings)
```

---

### 1.2 首页 (Home)

**页面功能**：
- 快捷入口（全部音乐、收藏、最近播放）
- 快速播放控制栏（底部）
- 推荐/热门歌曲（如果有）

**组件**：
```
HomeScreen
├── AppBar
├── QuickActionsGrid
├── RecentPlayList
└── MiniPlayer (fixed bottom)
```

---

### 1.3 音乐库 (Library)

**页面功能**：
- 列表视图：显示所有音乐
- 筛选：按艺术家、专辑、文件名
- 排序：按名称、添加时间、播放次数
- 搜索栏

**组件**：
```
LibraryScreen
├── SearchBar
├── FilterChipRow
├── MusicList
│   └── MusicListItem
└── MiniPlayer
```

**MusicListItem**：
- 专辑封面（左侧）
- 歌曲名称、艺术家（中央）
- 时长（右侧）
- 更多菜单（右侧）

---

### 1.4 播放页 (Player)

**页面功能**：
- 专辑封面（大）
- 歌曲信息（名称、艺术家、专辑）
- 播放控制（上一首、播放/暂停、下一首）
- 进度条
- 播放模式（顺序、单曲循环、随机）
- 歌词显示（如果有）
- 均衡器快捷入口

**组件**：
```
PlayerScreen
├── TopBar (back, more)
├── AlbumArt (large)
├── TrackInfo
├── ProgressBar
├── PlaybackControls
├── ModeSelector
├── LyricsView (optional)
└── EqualizerButton
```

---

### 1.5 歌单页 (Playlist)

**页面功能**：
- 显示歌单列表
- 创建新歌单
- 编辑/删除歌单
- 歌单内歌曲管理

**组件**：
```
PlaylistScreen
├── PlaylistList
└── CreatePlaylistButton
```

**PlaylistDetailScreen**：
```
PlaylistDetailScreen
├── PlaylistHeader
├── SongList
└── AddSongsButton
```

---

### 1.6 设置页 (Settings)

**页面功能**：
- 音频设置（缓存大小、音质）
- 外观设置（主题色、深色模式）
- 关于（版本信息）

**组件**：
```
SettingsScreen
├── SettingsSections
│   ├── AudioSettings
│   ├── AppearanceSettings
│   └── AboutSection
```

---

## 二、Vue 管理后台页面结构

### 2.1 页面导航

```
登录页 (Login)
    ↓
仪表盘 (Dashboard)
├── 音乐管理 (Music)
│   ├── 音乐列表
│   ├── 上传音乐
│   └── 音乐编辑
├── 歌单管理 (Playlists)
│   ├── 歌单列表
│   └── 歌单编辑
├── 用户管理 (Users)
│   ├── 用户列表
│   └── 用户编辑
├── 系统配置 (Settings)
│   ├── 存储配置
│   ├── 播放配置
│   └── 安全配置
└── 日志查看 (Logs)
```

---

### 2.2 登录页 (Login)

**页面功能**：
- 用户名输入
- 密码输入
- 登录按钮

**组件**：
```
LoginPage
├── LoginForm
│   ├── UsernameInput
│   ├── PasswordInput
│   └── LoginButton
```

---

### 2.3 仪表盘 (Dashboard)

**页面功能**：
- 概览统计（音乐总数、用户数、今日播放、总存储）
- 快捷操作卡片
- 数据图表（播放趋势）

**组件**：
```
DashboardPage
├── StatsCardsRow
├── QuickActions
└── ChartsSection
```

---

### 2.4 音乐管理 (Music)

**页面功能**：
- 音乐表格（ID、名称、艺术家、专辑、时长、大小、操作）
- 上传按钮（支持拖拽）
- 搜索栏
- 批量操作（批量删除）
- 分页

**组件**：
```
MusicPage
├── HeaderActions
│   ├── SearchInput
│   └── UploadButton
├── MusicTable
└── Pagination
```

**MusicEditDialog**：
```
MusicEditDialog
├── FormFields
│   ├── TitleInput
│   ├── ArtistInput
│   ├── AlbumInput
│   ├── CoverUpload
│   └── FilePreview
└── SaveButtons
```

---

### 2.5 歌单管理 (Playlists)

**页面功能**：
- 歌单表格
- 创建歌单
- 编辑歌单
- 删除歌单
- 歌单内歌曲排序

**组件**：
```
PlaylistPage
├── HeaderActions
│   ├── SearchInput
│   └── CreateButton
├── PlaylistTable
└── Pagination
```

---

### 2.6 用户管理 (Users)

**页面功能**：
- 用户表格
- 创建用户
- 编辑用户
- 启用/禁用用户
- 重置密码

**组件**：
```
UserPage
├── HeaderActions
│   ├── SearchInput
│   └── CreateButton
├── UserTable
└── Pagination
```

---

### 2.7 系统配置 (Settings)

**页面功能**：
- 存储配置（路径）
- 播放配置（音质、缓存）
- 安全配置（JWT 密钥、过期时间）

**组件**：
```
SettingsPage
├── TabsNavigation
├── StorageSettingsForm
├── PlaybackSettingsForm
├── SecuritySettingsForm
└── SaveButton
```

---

## 三、设计系统

### 3.1 颜色

| 类别 | 色值 | 用途 |
|------|------|------|
| 主色 | #1DB954 | 按钮、重要操作 |
| 主色深色 | #1AA34A | 悬停 |
| 背景 | #FFFFFF (Light) / #121212 (Dark) | 页面背景 |
| 卡片背景 | #F5F5F5 / #282828 | 卡片背景 |
| 文字主色 | #000000 / #FFFFFF | 主要文字 |
| 文字次色 | #666666 / #B3B3B3 | 次要文字 |
| 边框 | #E0E0E0 / #404040 | 分割线 |
| 成功 | #4CAF50 | 成功提示 |
| 警告 | #FFC107 | 警告提示 |
| 错误 | #F44336 | 错误提示 |

---

### 3.2 字体

| 类别 | 字体大小 (sp) | 字重 |
|------|---------------|------|
| 大标题 (H1) | 28 | 700 |
| 标题 (H2) | 22 | 600 |
| 小标题 (H3) | 18 | 600 |
| 正文 | 14 | 400 |
| 小字 | 12 | 400 |

---

### 3.3 间距

| 类别 | 值 (dp) |
|------|---------|
| 极小 | 4 |
| 小 | 8 |
| 中 | 16 |
| 大 | 24 |
| 特大 | 32 |

---

### 3.4 圆角

| 类别 | 半径 (dp) |
|------|-----------|
| 按钮 | 24 |
| 卡片 | 8 |
| 图片 | 8 |
| 对话框 | 12 |

---

### 3.5 图标

使用 **Material Icons** 作为主要图标库。

---

## 四、交互设计原则

### 4.1 通用交互

| 场景 | 行为 |
|------|------|
| 加载 | 显示 Loading Spinner / Skeleton |
| 错误 | 友好提示 + 重试按钮 |
| 空状态 | 空状态插图 + 提示文字 |
| 删除 | 二次确认对话框 |
| 表单验证 | 即时反馈 + 明确错误提示 |

---

### 4.2 手势

| 手势 | 功能 |
|------|------|
| 下拉刷新 | 刷新列表 |
| 上拉加载 | 加载更多 |
| 点击歌曲 | 播放该歌曲 |
| 长按歌曲 | 显示更多菜单 |
| 左右滑动 | 歌曲项快捷操作（添加歌单、删除） |

---

## 五、响应式设计

### 管理后台响应式断点

| 断点 | 宽度范围 | 布局 |
|------|----------|------|
| 移动端 | < 640px | 单列，侧边栏折叠 |
| 平板 | 640px - 1024px | 单列，侧边栏可展开 |
| 桌面 | > 1024px | 侧边栏 + 主内容区 |

---

> **最后更新**：2026-05-21
> **版本**：1.0.0

