# 需求规范文档自动提交脚本 (Windows PowerShell)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "需求规范文档自动提交工具" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 定义需要监控的文件
$specFiles = @("需求规范.md", "requirements.md", "spec.md")

# 检查文件是否存在
$foundSpecFile = $false
foreach ($file in $specFiles) {
    if (Test-Path $file) {
        $foundSpecFile = $true
        Write-Host "找到需求规范文档: $file" -ForegroundColor Green
    }
}

if (-not $foundSpecFile) {
    Write-Host "警告: 未找到任何需求规范文档" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "检查 git 状态..." -ForegroundColor Yellow
git status

Write-Host ""
Write-Host "添加所有变更..." -ForegroundColor Yellow
git add -A

# 生成提交信息
$timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
$commitMessage = "docs: 更新需求规范文档 - $timestamp"

Write-Host ""
Write-Host "提交变更..." -ForegroundColor Green
git commit -m $commitMessage

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "提示: 没有变更需要提交" -ForegroundColor Yellow
} else {
    Write-Host ""
    Write-Host "变更已提交成功!" -ForegroundColor Green
    
    # 询问是否推送
    $push = Read-Host "是否推送到远程仓库? (Y/N)"
    if ($push -eq "Y" -or $push -eq "y") {
        Write-Host ""
        Write-Host "正在推送中..." -ForegroundColor Yellow
        git push
        Write-Host "推送完成!" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "操作完成" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
