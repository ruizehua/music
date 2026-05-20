<#
需求规范文档自动提交脚本 (Windows PowerShell)

功能：自动检测需求规范文档变更并提交到 Git

使用方式：
    .\update-spec.ps1
    .\update-spec.ps1 -AutoPush
#>

<#
.SYNOPSIS
自动提交需求规范文档变更到 Git 仓库

.DESCRIPTION
检测需求规范文档的变更，自动添加、提交并可选推送到远程仓库

.PARAMETER AutoPush
是否自动推送到远程仓库，无需确认

.EXAMPLE
.\update-spec.ps1
手动确认后推送

.EXAMPLE
.\update-spec.ps1 -AutoPush
自动推送，无需确认
#>
param(
    [switch]$AutoPush
)

<#
.SYNOPSIS
显示脚本标题信息
#>
function Show-Title {
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "需求规范文档自动提交工具" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
}

<#
.SYNOPSIS
检查并显示找到的需求规范文档

.PARAMETER SpecFiles
需要检查的文件名数组

.OUTPUTS
bool - 是否找到任何规范文件
#>
function Test-SpecFiles {
    param(
        [string[]]$SpecFiles
    )
    
    $foundSpecFile = $false
    foreach ($file in $SpecFiles) {
        if (Test-Path $file) {
            $foundSpecFile = $true
            Write-Host "找到需求规范文档: $file" -ForegroundColor Green
        }
    }
    
    if (-not $foundSpecFile) {
        Write-Host "警告: 未找到任何需求规范文档" -ForegroundColor Yellow
    }
    
    return $foundSpecFile
}

<#
.SYNOPSIS
检查 Git 工作状态
#>
function Show-GitStatus {
    Write-Host ""
    Write-Host "检查 git 状态..." -ForegroundColor Yellow
    git status
}

<#
.SYNOPSIS
添加所有变更到暂存区
#>
function Add-GitChanges {
    Write-Host ""
    Write-Host "添加所有变更..." -ForegroundColor Yellow
    git add -A
}

<#
.SYNOPSIS
提交变更到本地仓库

.OUTPUTS
bool - 是否成功提交
#>
function Commit-GitChanges {
    # 生成带时间戳的提交信息
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $commitMessage = "docs: 更新需求规范文档 - $timestamp"
    
    Write-Host ""
    Write-Host "提交变更..." -ForegroundColor Green
    git commit -m $commitMessage
    
    return ($LASTEXITCODE -eq 0)
}

<#
.SYNOPSIS
推送到远程仓库

.PARAMETER AutoPush
是否自动推送，无需确认
#>
function Push-GitChanges {
    param(
        [switch]$AutoPush
    )
    
    if ($AutoPush) {
        Write-Host ""
        Write-Host "正在推送中..." -ForegroundColor Yellow
        git push
        Write-Host "推送完成!" -ForegroundColor Green
    }
    else {
        $push = Read-Host "是否推送到远程仓库? (Y/N)"
        if ($push -eq "Y" -or $push -eq "y") {
            Write-Host ""
            Write-Host "正在推送中..." -ForegroundColor Yellow
            git push
            Write-Host "推送完成!" -ForegroundColor Green
        }
    }
}

<#
.SYNOPSIS
显示操作完成信息
#>
function Show-Completion {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "操作完成" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
}

<#
.SYNOPSIS
主执行函数
#>
function Invoke-SpecCommit {
    # 显示标题
    Show-Title
    
    # 定义需要监控的文件
    $specFiles = @("需求规范.md", "requirements.md", "spec.md", "requirements-history.md")
    
    # 检查文件是否存在
    Test-SpecFiles -SpecFiles $specFiles
    
    # 检查 git 状态
    Show-GitStatus
    
    # 添加所有变更
    Add-GitChanges
    
    # 提交变更
    $commitSuccess = Commit-GitChanges
    
    if ($commitSuccess) {
        Write-Host ""
        Write-Host "变更已提交成功!" -ForegroundColor Green
        # 推送
        Push-GitChanges -AutoPush:$AutoPush
    }
    else {
        Write-Host ""
        Write-Host "提示: 没有变更需要提交" -ForegroundColor Yellow
    }
    
    # 显示完成信息
    Show-Completion
}

# 执行主函数
Invoke-SpecCommit