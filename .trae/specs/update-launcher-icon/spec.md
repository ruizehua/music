# 修改客户端桌面图标 Spec

## Why
当前 Android 客户端的桌面图标只是一个纯绿色方块（无前景图案），无法让用户识别这是一个音乐应用。需要替换为带有音乐符号的图标，提升品牌辨识度。

## What Changes
- 创建矢量图标前景层（`ic_launcher_foreground.xml`），绘制音乐符号图案
- 更新 `ic_launcher.xml` 和 `ic_launcher_round.xml`，引用新的前景层
- 保持背景层为品牌主色 `#1DB954`

## Impact
- Affected specs: music-software-init (客户端 UI)
- Affected code:
  - `client/app/src/main/res/drawable/ic_launcher_foreground.xml`（新建）
  - `client/app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`（修改）
  - `client/app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml`（修改）

## ADDED Requirements

### Requirement: 音乐符号桌面图标
系统 SHALL 提供一个带有音乐符号图案的应用桌面图标。

#### Scenario: 用户查看桌面图标
- **WHEN** 用户在 Android 设备桌面查看应用图标
- **THEN** 图标显示绿色背景 + 白色音乐符号（音符）图案
- **AND** 圆形图标和方形图标均正确显示
