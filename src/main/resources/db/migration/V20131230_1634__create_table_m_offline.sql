--author xu.yuexi
--description 添加离线数据批次的表
DROP TABLE IF EXISTS `m_offline_batch`;
CREATE TABLE `m_offline_batch` (
  `id` varchar(36) NOT NULL  COMMENT 'uuid',
  `batchName` VARCHAR(100) NOT NULL COMMENT '批次名称',
  `startTime` DATETIME NOT NULL COMMENT '采样开始时间',
  `endTime` DATETIME NOT NULL COMMENT '采样结束时间',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '离线数据批次表';

--description 添加离线数据批次的挂接文档的表
DROP TABLE IF EXISTS `m_offline_file`;
CREATE TABLE `m_offline_file` (
  `id` varchar(36) NOT NULL  COMMENT 'uuid',
  `batchId` varchar(36) NOT NULL COMMENT '批次id',
  `path` VARCHAR(100) NOT NULL COMMENT '文件存储路径',
  PRIMARY KEY (`id`),
  KEY `batchId` (`batchId`),
  CONSTRAINT `m_offline_batch` FOREIGN KEY (`batchId`) REFERENCES `m_offline_batch` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '离线数据上传文档记录表';

--description 添加离线数据批次的位置
DROP TABLE IF EXISTS `m_offline_location`;
CREATE TABLE `m_offline_location` (
  `id` varchar(36) NOT NULL  COMMENT 'uuid',
  `name` VARCHAR(100) NOT NULL COMMENT '位置名',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '离线数据位置表';

--description 添加离线数据监测指标
DROP TABLE IF EXISTS `m_offline_sensorinfo`;
CREATE TABLE `m_offline_sensorinfo` (
  `id` varchar(36) NOT NULL  COMMENT 'uuid',
  `name` VARCHAR(100) NOT NULL COMMENT '监测指标名称',
  `units` VARCHAR(100) NOT NULL COMMENT '检测指标单位',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '离线数据监测指标';

--description 添加离线数据监测指标数值
DROP TABLE IF EXISTS `m_offline_data`;
CREATE TABLE `m_offline_data` (
  `id` varchar(36) NOT NULL  COMMENT 'uuid',
  `batchId` varchar(36) NOT NULL COMMENT '批次id',
  `locationId` varchar(36) NOT NULL COMMENT '位置Id',
  `sensorinfoId` varchar(36) NOT NULL COMMENT '监测指标id 外键',
  `sensorValue` varchar(20) NOT NULL COMMENT '监测指标数据值',
  PRIMARY KEY (`id`),
  KEY `batchId` (`batchId`),
  CONSTRAINT `m_offline_batch_data_batchId` FOREIGN KEY (`batchId`) REFERENCES `m_offline_batch` (`id`),
    KEY `locationId` (`locationId`),
  CONSTRAINT `m_offline_location` FOREIGN KEY (`locationId`) REFERENCES `m_offline_location` (`id`),
    KEY `sensorinfoId` (`sensorinfoId`),
  CONSTRAINT `m_offline_sensorinfo` FOREIGN KEY (`sensorinfoId`) REFERENCES `m_offline_sensorinfo` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8  COMMENT '离线数据，数据表';

--description 添加离线数据批次监测指标
DROP TABLE IF EXISTS `m_offline_batch_sensorinfo`;
CREATE TABLE `m_offline_batch_sensorinfo` (
  `id` varchar(36) NOT NULL COMMENT 'UUID',
  `batchId` varchar(36) NOT NULL COMMENT '批次id',
  `sensorinfoId` varchar(36) NOT NULL COMMENT '离线数据检测指标id',
  PRIMARY KEY (`id`),
  KEY `batch_1` (`batchId`),
  KEY `batch_2` (`sensorinfoId`),
  CONSTRAINT `batch_1` FOREIGN KEY (`batchId`) REFERENCES `m_offline_batch` (`id`),
  CONSTRAINT `batch_2` FOREIGN KEY (`sensorinfoId`) REFERENCES `m_offline_sensorinfo` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '离线数据批次监测指标';



