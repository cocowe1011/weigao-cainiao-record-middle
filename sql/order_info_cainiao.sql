-- 威海菜鸟 order_info（字段对齐大包列表 CSV + WCS 流转字段）
IF OBJECT_ID('dbo.order_info', 'U') IS NOT NULL
    DROP TABLE dbo.order_info;
GO

CREATE TABLE dbo.order_info (
    id                   BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,   -- 主键
    invalid_flag         VARCHAR(1)   NOT NULL DEFAULT '0',           -- 作废标识：0未作废 1作废
    tray_status          VARCHAR(1)   NULL     DEFAULT '1',           -- WCS流转状态：1已上货 2分拣 3AGV运输中 4已送达WMS
    insert_time          DATETIME     NOT NULL DEFAULT GETDATE(),     -- WCS入库时间
    finish_time          DATETIME     NULL,                           -- 完成时间（送达WMS）
    package_no           VARCHAR(50)  NULL,                            -- 大包号
    customer_source      VARCHAR(100) NULL,                            -- 客户来源
    package_create_time  VARCHAR(30)  NULL,                            -- 创建时间(UTC+08:00)
    source_warehouse     VARCHAR(100) NULL,                            -- 来源仓
    charge_weight        VARCHAR(20)  NULL,                            -- 计费重
    expected_qty         VARCHAR(10)  NULL,                            -- 预计件数
    actual_qty           VARCHAR(10)  NULL,                            -- 实际件数
    channel              VARCHAR(100) NULL,                            -- 渠道
    package_status       VARCHAR(20)  NULL,                            -- 状态（如已装箱）
    destination_country  VARCHAR(50)  NULL,                            -- 目的国
    departure_port       VARCHAR(50)  NULL,                            -- 起运港
    destination_port     VARCHAR(50)  NULL,                            -- 目的港
    mbl_no               VARCHAR(50)  NULL,                            -- MBL提单号
    sub_bill_no          VARCHAR(50)  NULL,                            -- 分单号
    business_no          VARCHAR(255) NULL,                            -- 业务编号
    container_no         VARCHAR(50)  NULL,                            -- 箱号
    seal_no              VARCHAR(50)  NULL,                            -- 封号
    packing_time         VARCHAR(30)  NULL,                            -- 装箱时间(UTC+08:00)
    packer               VARCHAR(50)  NULL,                            -- 装箱人
    handover_time        VARCHAR(30)  NULL,                            -- 仓干交接时间(UTC+08:00)
    handover_person      VARCHAR(50)  NULL,                            -- 仓干交接人
    customs_port         VARCHAR(50)  NULL,                            -- 清关口岸
    bill_receiver        VARCHAR(100) NULL,                            -- 提单收件人
    batch_no             VARCHAR(50)  NULL,                            -- 批次号
    plate_no             VARCHAR(20)  NULL                             -- 车牌号
);
GO

EXEC sys.sp_addextendedproperty
    @name = N'MS_Description', @value = N'大包订单信息表（CSV大包字段 + WCS流转）',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'order_info';
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'id';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作废标识：0未作废 1作废', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'invalid_flag';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'WCS流转状态：1已上货 2分拣 3AGV运输中 4已送达WMS', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'tray_status';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'WCS入库时间', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'insert_time';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'完成时间（送达WMS）', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'finish_time';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'大包号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'package_no';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户来源', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'customer_source';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间(UTC+08:00)', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'package_create_time';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'来源仓', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'source_warehouse';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计费重', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'charge_weight';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预计件数', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'expected_qty';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实际件数', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'actual_qty';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'渠道', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'channel';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态（如已装箱）', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'package_status';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'目的国', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'destination_country';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'起运港', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'departure_port';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'目的港', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'destination_port';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'MBL提单号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'mbl_no';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分单号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'sub_bill_no';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'业务编号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'business_no';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'箱号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'container_no';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'封号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'seal_no';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'装箱时间(UTC+08:00)', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'packing_time';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'装箱人', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'packer';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'仓干交接时间(UTC+08:00)', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'handover_time';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'仓干交接人', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'handover_person';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'清关口岸', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'customs_port';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'提单收件人', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'bill_receiver';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'批次号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'batch_no';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'车牌号', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'plate_no';
GO

CREATE NONCLUSTERED INDEX IX_order_info_invalid_flag_insert_time
    ON dbo.order_info (invalid_flag, insert_time DESC);
CREATE NONCLUSTERED INDEX IX_order_info_tray_status
    ON dbo.order_info (tray_status);
CREATE NONCLUSTERED INDEX IX_order_info_package_no
    ON dbo.order_info (package_no);
CREATE NONCLUSTERED INDEX IX_order_info_business_no
    ON dbo.order_info (business_no);
CREATE NONCLUSTERED INDEX IX_order_info_batch_no
    ON dbo.order_info (batch_no);
GO
