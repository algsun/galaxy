-- author li.jianfei
-- description 添加子系统及相关权限并创建档案管理系统相关表

INSERT INTO `t_subsystem`(`subsystemId`,`subsystemName`,`subsystemCode`,`remark`,`enable`) VALUES
(10,'档案管理','cybertron','塞伯坦',1);

INSERT INTO t_system_privilege (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUE (
  1,
  10,
  'blackhole:subsystem:cybertron',
  'blackhole:subsystem',
  '档案管理系统',
  '',
  122,
  0
) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blackhole:subsystem:cybertron' AS privilegeId
FROM
  `t_roles`
WHERE isManager =1) ;

INSERT INTO `t_system_privilege` (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUE
 (10,1,'cybertron:setting',NULL,'设置','setting',122,1);


CREATE TABLE `cbt_config` (
  `uuid` VARCHAR (22) NOT NULL COMMENT 'UUID',
  `siteId` VARCHAR (50) NOT NULL COMMENT '站点ID',
  `officialId` VARCHAR (9) NOT NULL COMMENT '档案全宗号(官方指定)',
  PRIMARY KEY (`uuid`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '档案管理系统配置表' ;

CREATE TABLE `cbt_volume` (
  `volumeCode` INT (11) NOT NULL COMMENT '代码',
  `name` VARCHAR (25) NOT NULL COMMENT '卷名称',
  `parentCode` INT (11) DEFAULT NULL COMMENT '上级卷代码',
  PRIMARY KEY (`volumeCode`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '卷字典表' ;

CREATE TABLE `cbt_records` (
  `uuid` VARCHAR (22) NOT NULL COMMENT 'UUID',
  `siteId` VARCHAR (50) NOT NULL COMMENT '站点ID',
  `recordCode` VARCHAR (20) NOT NULL COMMENT '档号',
  `name` VARCHAR (50) NOT NULL COMMENT '案卷题名',
  `stateSecrets` INT (1) NOT NULL COMMENT '密级',
  `department` VARCHAR (50) NOT NULL COMMENT '立卷单位名称',
  `establishDate` DATETIME NOT NULL COMMENT '立卷日期',
  `volumeCode` INT (11) NOT NULL COMMENT '卷代码',
  PRIMARY KEY (`uuid`),
  KEY `fk_cbt_volume_volumeCode` (`volumeCode`),
  KEY `fk_t_site_siteId` (`siteId`),
  CONSTRAINT `fk_t_site_siteId` FOREIGN KEY (`siteId`) REFERENCES `t_site` (`siteId`),
  CONSTRAINT `fk_cbt_volume_volumeCode` FOREIGN KEY (`volumeCode`) REFERENCES `cbt_volume` (`volumeCode`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '档案表' ;

CREATE TABLE `cbt_attachement` (
  `uuid` VARCHAR (22) NOT NULL COMMENT 'UUID',
  `recordUuid` VARCHAR (22) NOT NULL COMMENT '档案ID',
  `path` VARCHAR (150) NOT NULL COMMENT '附件路径',
  `uploadName` VARCHAR(100) NOT NULL COMMENT '上传时文件名',
  `userId` INT (11) NOT NULL COMMENT '上传人',
  `uploadDate` DATETIME NOT NULL COMMENT '上传日期',
  PRIMARY KEY (`uuid`),
  KEY `fk_cbt_records_uuid` (`recordUuid`),
  KEY `fk_t_users_id` (`userId`),
  CONSTRAINT `fk_cbt_records_uuid` FOREIGN KEY (`recordUuid`) REFERENCES `cbt_records` (`uuid`),
  CONSTRAINT `fk_t_users_id` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '档案附件表' ;
