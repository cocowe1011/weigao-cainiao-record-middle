-- 威海菜鸟 PDA扫码队列明细表
-- 一个包裹一行，入队=INSERT、出队=DELETE队头行、删除=DELETE指定行、排序=交换seq_no，
-- 行级操作替代原 queue_info(id=17) 整队列JSON覆盖，从根上避免 PDA端/WCS端 并发写互相覆盖；可重复执行
-- 说明：PDA扫码只建队列、不落库order_info；order_info由WCS收到目的地请求下发时统一save（与五面扫一致）

IF OBJECT_ID(N'dbo.pda_scan_queue', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.pda_scan_queue (
        id             BIGINT        NOT NULL PRIMARY KEY,       -- 主键（MyBatis-Plus 雪花ID，非自增）
        package_no     VARCHAR(64)   NOT NULL,                   -- 大包号（条码）
        channel        VARCHAR(64)   NULL,                       -- 渠道（菜鸟 laneCode）
        package_size   VARCHAR(16)   NULL,                       -- 包裹大小：large大包 / small小包
        charge_weight  VARCHAR(32)   NULL,                       -- 计费重量（菜鸟 standardWeight），WCS出队落库order_info用
        packing_weight VARCHAR(32)   NULL,                       -- 包装重量
        expected_qty   VARCHAR(16)   NULL,                       -- 预期小包数
        seq_no         INT           NOT NULL DEFAULT 0,         -- 队列顺序号，越小越靠前，队头=最小
        scan_time      DATETIME      NOT NULL DEFAULT GETDATE()  -- 扫码入队时间
    );

    EXEC sys.sp_addextendedproperty
        @name=N'MS_Description', @value=N'PDA扫码队列明细表（一个包裹一行，行级增删改，替代整队列JSON覆盖）',
        @level0type=N'SCHEMA', @level0name=N'dbo',
        @level1type=N'TABLE',  @level1name=N'pda_scan_queue';

    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键（MyBatis-Plus雪花ID，非自增）',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'id';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'大包号（条码）',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'package_no';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'渠道（菜鸟 laneCode）',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'channel';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'包裹大小：large大包 / small小包',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'package_size';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计费重量（菜鸟standardWeight），WCS出队落库order_info用',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'charge_weight';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'包装重量',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'packing_weight';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预期小包数',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'expected_qty';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'队列顺序号，越小越靠前，队头=最小',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'seq_no';
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'扫码入队时间',
        @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
        @level2type=N'COLUMN', @level2name=N'scan_time';
END
GO

-- 迁移：若表已按旧结构创建（含 order_info_id、无 charge_weight），调整为新结构
IF OBJECT_ID(N'dbo.pda_scan_queue', N'U') IS NOT NULL
BEGIN
    IF COL_LENGTH('dbo.pda_scan_queue', 'order_info_id') IS NOT NULL
    BEGIN
        -- 先删列上的扩展属性，再删列
        IF EXISTS (
            SELECT 1 FROM sys.extended_properties
            WHERE major_id = OBJECT_ID('dbo.pda_scan_queue')
              AND minor_id = COLUMNPROPERTY(OBJECT_ID('dbo.pda_scan_queue'), 'order_info_id', 'ColumnId')
              AND name = 'MS_Description'
        )
            EXEC sys.sp_dropextendedproperty @name=N'MS_Description',
                @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'pda_scan_queue',
                @level2type=N'COLUMN', @level2name=N'order_info_id';
        ALTER TABLE dbo.pda_scan_queue DROP COLUMN order_info_id;
    END
    IF COL_LENGTH('dbo.pda_scan_queue', 'charge_weight') IS NULL
        ALTER TABLE dbo.pda_scan_queue ADD charge_weight VARCHAR(32) NULL;
END
GO
