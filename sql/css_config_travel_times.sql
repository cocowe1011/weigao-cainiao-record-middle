-- 威海菜鸟 css_config 行进时长列（分拣机1-6、X光机）
-- 单位：秒，精确一位小数；可重复执行

IF COL_LENGTH('dbo.css_config', 'sorter1_travel_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD sorter1_travel_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_sorter1_travel_sec DEFAULT 19; -- 五面扫进队到分拣机1的行进时间（秒）
END
GO

IF COL_LENGTH('dbo.css_config', 'sorter2_travel_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD sorter2_travel_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_sorter2_travel_sec DEFAULT 23; -- 五面扫进队到分拣机2的行进时间（秒）
END
GO

IF COL_LENGTH('dbo.css_config', 'sorter3_travel_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD sorter3_travel_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_sorter3_travel_sec DEFAULT 27; -- 五面扫进队到分拣机3的行进时间（秒）
END
GO

IF COL_LENGTH('dbo.css_config', 'sorter4_travel_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD sorter4_travel_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_sorter4_travel_sec DEFAULT 30; -- 五面扫进队到分拣机4的行进时间（秒）
END
GO

IF COL_LENGTH('dbo.css_config', 'sorter5_travel_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD sorter5_travel_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_sorter5_travel_sec DEFAULT 34; -- 五面扫进队到分拣机5的行进时间（秒）
END
GO

IF COL_LENGTH('dbo.css_config', 'sorter6_travel_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD sorter6_travel_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_sorter6_travel_sec DEFAULT 38; -- 五面扫进队到分拣机6的行进时间（秒）
END
GO

IF COL_LENGTH('dbo.css_config', 'xray_travel_sec') IS NULL
BEGIN
    ALTER TABLE dbo.css_config ADD xray_travel_sec DECIMAL(10,1) NOT NULL
        CONSTRAINT DF_css_config_xray_travel_sec DEFAULT 11; -- 五面扫进队到X光机光电的行进时间（秒）
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'sorter1_travel_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'五面扫进队到分拣机1的行进时间（秒）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'sorter1_travel_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'sorter2_travel_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'五面扫进队到分拣机2的行进时间（秒）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'sorter2_travel_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'sorter3_travel_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'五面扫进队到分拣机3的行进时间（秒）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'sorter3_travel_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'sorter4_travel_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'五面扫进队到分拣机4的行进时间（秒）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'sorter4_travel_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'sorter5_travel_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'五面扫进队到分拣机5的行进时间（秒）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'sorter5_travel_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'sorter6_travel_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'五面扫进队到分拣机6的行进时间（秒）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'sorter6_travel_sec';
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.extended_properties
    WHERE major_id = OBJECT_ID(N'dbo.css_config')
      AND minor_id = COLUMNPROPERTY(OBJECT_ID(N'dbo.css_config'), N'xray_travel_sec', 'ColumnId')
      AND name = N'MS_Description'
)
BEGIN
    EXEC sys.sp_addextendedproperty
        @name = N'MS_Description',
        @value = N'五面扫进队到X光机光电的行进时间（秒）',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE',  @level1name = N'css_config',
        @level2type = N'COLUMN', @level2name = N'xray_travel_sec';
END
GO
