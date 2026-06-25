# Minecraft 纹理占位符生成脚本
# 这个脚本会创建基本的彩色方块作为临时纹理

$texturePath = "E:\More-Decorative-Blocks\src\main\resources\assets\more_decorative_blocks\textures"

Write-Host "=== Minecraft 纹理占位符生成器 ===" -ForegroundColor Cyan
Write-Host ""

# 定义纹理颜色和路径
$textures = @{
    # 方块纹理 (银白色主题)
    "block\aluminum_ore.png" = "#C0C0C0"           # 银色
    "block\deepslate_aluminum_ore.png" = "#808080"  # 深灰色
    "block\raw_aluminum_block.png" = "#D3D3D3"      # 浅灰色
    "block\aluminum_block.png" = "#E8E8E8"          # 很浅的灰色
    
    # 物品纹理
    "item\raw_aluminum.png" = "#C8C8C8"             # 银灰色
    "item\aluminum_ingot.png" = "#D8D8D8"           # 亮银色
}

Write-Host "注意: PowerShell 无法直接创建PNG图像文件。" -ForegroundColor Yellow
Write-Host "请使用以下方法之一创建纹理：" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. 使用 Blockbench (推荐):" -ForegroundColor Green
Write-Host "   - 下载: https://blockbench.net/" -ForegroundColor White
Write-Host "   - 创建 16x16 或 32x32 的纹理" -ForegroundColor White
Write-Host "   - 导出为 PNG" -ForegroundColor White
Write-Host ""
Write-Host "2. 使用在线工具:" -ForegroundColor Green
Write-Host "   - https://www.pixilart.com/" -ForegroundColor White
Write-Host "   - https://piskelapp.com/" -ForegroundColor White
Write-Host ""
Write-Host "3. 从其他模组复制纹理并重命名" -ForegroundColor Green
Write-Host ""
Write-Host "需要创建的纹理文件：" -ForegroundColor Cyan
Write-Host ""

foreach ($tex in $textures.GetEnumerator()) {
    $fullPath = Join-Path $texturePath $tex.Key
    $exists = Test-Path $fullPath
    $status = if ($exists) { "✓ 已存在 (但可能损坏)" } else { "✗ 不存在" }
    $color = if ($exists) { "Yellow" } else { "Red" }
    
    Write-Host "$($tex.Key)" -ForegroundColor White -NoNewline
    Write-Host " - " -NoNewline
    Write-Host "$status" -ForegroundColor $color
    Write-Host "  建议颜色: $($tex.Value)" -ForegroundColor Gray
}

Write-Host ""
Write-Host "=== 纹理设计建议 ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "铝矿石 (aluminum_ore):" -ForegroundColor White
Write-Host "  - 背景: 石头灰色 (#808080)" -ForegroundColor Gray
Write-Host "  - 矿脉: 银白色 (#C0C0C0) 斑点" -ForegroundColor Gray
Write-Host ""
Write-Host "深层铝矿石 (deepslate_aluminum_ore):" -ForegroundColor White
Write-Host "  - 背景: 深板岩色 (#4A4A4A)" -ForegroundColor Gray
Write-Host "  - 矿脉: 银白色 (#C0C0C0) 斑点" -ForegroundColor Gray
Write-Host ""
Write-Host "粗铝块 (raw_aluminum_block):" -ForegroundColor White
Write-Host "  - 9个粗糙的银白色金属块图案" -ForegroundColor Gray
Write-Host "  - 类似原版的粗铁块" -ForegroundColor Gray
Write-Host ""
Write-Host "铝块 (aluminum_block):" -ForegroundColor White
Write-Host "  - 光滑的银白色表面" -ForegroundColor Gray
Write-Host "  - 类似原版的铁块但更亮" -ForegroundColor Gray
Write-Host ""
Write-Host "粗铝 (raw_aluminum):" -ForegroundColor White
Write-Host "  - 单个粗糙的银白色金属块" -ForegroundColor Gray
Write-Host "  - 16x16 像素" -ForegroundColor Gray
Write-Host ""
Write-Host "铝锭 (aluminum_ingot):" -ForegroundColor White
Write-Host "  - 光滑的银白色金属锭" -ForegroundColor Gray
Write-Host "  - 类似原版的铁锭" -ForegroundColor Gray
Write-Host ""
Write-Host "完成后，运行: ./gradlew clean build" -ForegroundColor Green
Write-Host ""
