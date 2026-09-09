-- 威海菜鸟 css_config 业务参数列（光电误差、超时清理、分拣口容量）
-- 现有表已有 plc_ip / plc_port 等字段，本脚本仅追加业务列，可重复执行

IF COL_LENGTH('dbo.css_config', 'arrival_tolerance_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD arrival_tolerance_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_arrival_tolerance_sec DEFAULT 2; -- 光电匹配误差（秒，±）
END
GO

IF COL_LENGTH('dbo.css_config', 'not_entered_timeout_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD not_entered_timeout_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_not_entered_timeout_sec DEFAULT 4.5; -- 已发命令未进分拣口清理时间（秒）
END
GO

IF COL_LENGTH('dbo.css_config', 'cmd_not_sent_timeout_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD cmd_not_sent_timeout_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_cmd_not_sent_timeout_sec DEFAULT 5; -- 超时未发命令清理时间（秒，超过应到达后再等待）
END
GO

IF COL_LENGTH('dbo.css_config', 'large_port_capacity') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD large_port_capacity INT NOT NULL
        CONSTRAINT DF_css_config_large_port_capacity DEFAULT 5; -- 大包分拣口容量
END
GO

IF COL_LENGTH('dbo.css_config', 'small_port_capacity') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD small_port_capacity INT NOT NULL
        CONSTRAINT DF_css_config_small_port_capacity DEFAULT 8; -- 小包分拣口容量
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'arrival_tolerance_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'触发分拣机光电后应到达的误差（秒，±）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'arrival_tolerance_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'not_entered_timeout_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'清理未进入分拣口包裹的时间（秒）：已发转向命令后仍未进分拣口则删除',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'not_entered_timeout_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'cmd_not_sent_timeout_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'清理超时未发送分拣命令的包裹的时间（秒）：超过应到达时刻后再等待该时长仍未发命令则删除',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'cmd_not_sent_timeout_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'large_port_capacity', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'大包分拣口容量',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'large_port_capacity';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'small_port_capacity', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'小包分拣口容量',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'small_port_capacity';
END
GO
