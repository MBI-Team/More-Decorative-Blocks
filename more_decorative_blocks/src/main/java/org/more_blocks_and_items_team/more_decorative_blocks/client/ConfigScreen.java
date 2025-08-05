
@Override
protected void init() {
    super.init();
    
    HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    layout.setHeaderHeight(30);
    
    GridLayout grid = new GridLayout();
    grid.defaultCellSetting().alignHorizontallyCenter();
    GridLayout.RowHelper rowHelper = grid.createRowHelper(2);

    // 添加复选框
    enableVersionCheckerBox = Checkbox.builder(Component.translatable("config.more_decorative_blocks.enable_version_checker"), this.font)
            .selected(Config.enableVersionChecker)
            .build();
    rowHelper.addChild(enableVersionCheckerBox, 2);

    // 添加文本框
    heightOfSeatStoneBox = new EditBox(this.font, 0, 0, 100, 20, 
            Component.translatable("config.more_decorative_blocks.height_of_seat_stone"));
    heightOfSeatStoneBox.setValue(String.valueOf(Config.heightOfSeatStone));
    rowHelper.addChild(heightOfSeatStoneBox, 2);

    // 添加灰色不可滚动区域
    rowHelper.addChild(new Widget() {
        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            guiGraphics.fillGradient(0, 0, width, 50, 0x808080, 0x808080); // 灰色背景
        }
    }, 2);

    grid.arrangeElements();
    layout.addToContents(grid);

    // 添加按钮
    layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (button) -> {
        this.onDone();
    }).build());

    layout.visitWidgets(this::addRenderableWidget);
    layout.arrangeElements();
}
