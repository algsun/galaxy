--author 刘柱
--任务记录权限
CREATE TABLE `o_draw_register` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `repairRecordId` INT(11) DEFAULT NULL COMMENT '关联修复记录',
  `drawingType` VARCHAR(50) DEFAULT NULL COMMENT '图纸类型',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '简单描述',
  `drawingPerson` INT(11) DEFAULT NULL COMMENT '绘图人',
  `stamp` DATETIME DEFAULT NULL COMMENT '时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  `imgPath` VARCHAR(200) DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`),
  KEY `FK_register_record` (`repairRecordId`),
  CONSTRAINT `FK_register_record` FOREIGN KEY (`repairRecordId`) REFERENCES `o_repair_record` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '绘图登记表';

CREATE TABLE `o_image_datum` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `repairRecordId` INT(11) DEFAULT NULL COMMENT '关联修复记录',
  `content` VARCHAR(500) DEFAULT NULL COMMENT '内容描述',
  `media` VARCHAR(100) DEFAULT NULL COMMENT '介质',
  `duration` INT(11) DEFAULT NULL COMMENT '时长(min)',
  `submitPerson` INT(11) DEFAULT NULL COMMENT '提交人',
  `stamp` DATETIME DEFAULT NULL COMMENT '采集时间',
  `filePath` VARCHAR(200) DEFAULT NULL COMMENT '文件名',
  PRIMARY KEY (`id`),
  KEY `FK_i_datum_record` (`repairRecordId`),
  CONSTRAINT `FK_i_datum_record` FOREIGN KEY (`repairRecordId`) REFERENCES `o_repair_record` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '影像资料表';


CREATE TABLE `o_repair_assessment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `repairRecordId` INT(11) NOT NULL COMMENT '关联到修复记录',
  `selfAssessment` VARCHAR(1000) DEFAULT NULL COMMENT '自评估意见',
  `stamp` DATETIME DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`id`),
  KEY `FK_assessment_record` (`repairRecordId`),
  CONSTRAINT `FK_assessment_record` FOREIGN KEY (`repairRecordId`) REFERENCES `o_repair_record` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '自评估意见表';

CREATE TABLE `o_detection_analysis` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `repairRecordId` int(11) DEFAULT NULL COMMENT '关联修复记录',
  `sampleName` varchar(50) NOT NULL COMMENT '样品名称',
  `sampleDescription` varchar(1000) DEFAULT NULL COMMENT '样品描述',
  `analysisPurpose` varchar(200) DEFAULT NULL COMMENT '分析目的',
  `analysisMethod` varchar(200) DEFAULT NULL COMMENT '分析方法',
  `analysisResult` varchar(200) DEFAULT NULL COMMENT '分析结果',
  `reportCode` varchar(200) DEFAULT NULL COMMENT '报告代码',
  `analysisTime` datetime DEFAULT NULL COMMENT '分析时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_d_analysis_record` (`repairRecordId`),
  CONSTRAINT `FK_d_analysis_record` FOREIGN KEY (`repairRecordId`) REFERENCES `o_repair_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '分析表';

CREATE TABLE `o_repair_log` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `repairRecordId` INT(11) NOT NULL COMMENT '关联的修复记录',
  `repairPerson` VARCHAR(50) NOT NULL COMMENT '保护修复人',
  `stamp` DATETIME NOT NULL COMMENT '时间日期',
  `log` VARCHAR(1000) DEFAULT NULL COMMENT '日志',
  PRIMARY KEY (`id`),
  KEY `FK_repair_log_record` (`repairRecordId`),
  CONSTRAINT `FK_repair_log_record` FOREIGN KEY (`repairRecordId`) REFERENCES `o_repair_record` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='修复日志';

CREATE TABLE `o_repair_reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `repairRecordId` int(11) NOT NULL COMMENT '关联到修复记录',
  `expertsAcceptance` varchar(1000) DEFAULT NULL COMMENT '专家验收意见',
  `acceptanceDate` datetime DEFAULT NULL COMMENT '时间',
  `organizationUnit` varchar(200) DEFAULT NULL COMMENT '组织单位',
  `expertsList` varchar(200) DEFAULT NULL COMMENT '专家名单',
  `acceptanceIdea` varchar(1000) DEFAULT NULL COMMENT '验收意见',
  PRIMARY KEY (`id`),
  KEY `FK_reviews_record` (`repairRecordId`),
  CONSTRAINT `FK_reviews_record` FOREIGN KEY (`repairRecordId`) REFERENCES `o_repair_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `o_repair_situation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `repairRecordId` int(11) NOT NULL COMMENT '修复记录ID',
  `weight` varchar(50) DEFAULT NULL COMMENT '质量,修复前',
  `repairedWeight` varchar(50) DEFAULT NULL COMMENT '修复后质量',
  `size` varchar(50) DEFAULT NULL COMMENT '尺寸,修复前',
  `repairedSize` varchar(50) DEFAULT NULL COMMENT '修复后尺寸',
  `summarize` varchar(1000) DEFAULT NULL COMMENT '文物修复过程记录综述(材料、工艺步骤及操作条件，附影像资料)',
  `techChange` varchar(200) DEFAULT NULL COMMENT '技术变更',
  `craft` varchar(200) DEFAULT NULL COMMENT '工艺',
  `frameStyle` varchar(200) DEFAULT NULL COMMENT '装裱样式',
  `frameMaterial` varchar(200) DEFAULT NULL COMMENT '装裱用料',
  `packHead` varchar(200) DEFAULT NULL COMMENT '包首',
  `kakemono` varchar(200) DEFAULT NULL COMMENT '画轴',
  `spinningType` varchar(200) DEFAULT NULL COMMENT '种类',
  `craftsmanship` varchar(200) DEFAULT NULL COMMENT '制作工艺',
  `weavingProcess` varchar(200) DEFAULT NULL COMMENT '织造工艺',
  `fabricPart` varchar(200) DEFAULT NULL COMMENT '织物组织',
  `densityLong` varchar(200) DEFAULT NULL COMMENT '织物密度-经线',
  `densityLat` varchar(200) DEFAULT NULL COMMENT '织物密度-纬线',
  `colorLong` varchar(200) DEFAULT NULL COMMENT '丝线颜色-经线',
  `colorLat` varchar(200) DEFAULT NULL COMMENT '细线颜色-纬线',
  `slender` varchar(200) DEFAULT NULL COMMENT '丝线细度',
  `twistDirection` varchar(200) DEFAULT NULL COMMENT '丝线捻向',
  `twistDegree` varchar(200) DEFAULT NULL COMMENT '丝线捻度',
  `position` varchar(200) DEFAULT NULL COMMENT '位置',
  PRIMARY KEY (`id`),
  KEY `FK_situation_record` (`repairRecordId`),
  CONSTRAINT `FK_situation_record` FOREIGN KEY (`repairRecordId`) REFERENCES `o_repair_record` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;



