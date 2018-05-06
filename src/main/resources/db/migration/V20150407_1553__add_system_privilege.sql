--author li.jianfei
--description 添加科研成果子系统表及权限

CREATE TABLE `s_affair` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` INT(11) DEFAULT NULL COMMENT '事务类型\n1-发表论文/著作，2-负责项目，3-其他服务',
  `title` VARCHAR(50) DEFAULT NULL COMMENT '事务名称',
  `incharge` VARCHAR(50) DEFAULT NULL COMMENT '姓名',
  `magazine` VARCHAR(50) DEFAULT NULL COMMENT '发表期刊',
  `publicDate` DATE DEFAULT NULL COMMENT '发表日期',
  `magazineSrc` VARCHAR(50) DEFAULT NULL COMMENT '期刊来源',
  `projectType` INT(11) DEFAULT NULL COMMENT '项目属性\n1-科研，2-修复',
  `projectLevel` INT(11) DEFAULT NULL COMMENT '项目级别\n1-院级，2-省级，3-国家级',
  `progress` VARCHAR(250) DEFAULT NULL COMMENT '进展情况',
  `prize` VARCHAR(250) DEFAULT NULL COMMENT '获奖情况',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `createTime` DATE DEFAULT NULL COMMENT '录入时间',
  `createBy` VARCHAR(50) DEFAULT NULL COMMENT '录入人',
  `siteId` VARCHAR(20) DEFAULT NULL COMMENT '站点ID',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='成果展示';


CREATE TABLE `s_literature` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `catalog` VARCHAR(50) DEFAULT NULL COMMENT '资料分类\n1-论文，2-著作，3-其他',
  `title` VARCHAR(120) DEFAULT NULL COMMENT '文献名称',
  `summary` VARCHAR(700) DEFAULT NULL COMMENT '摘要',
  `magazine` VARCHAR(50) DEFAULT NULL COMMENT '期刊\n',
  `periodical` VARCHAR(50) DEFAULT NULL COMMENT '期别\n',
  `author` VARCHAR(50) DEFAULT NULL COMMENT '作者\n',
  `publicDate` DATE DEFAULT NULL COMMENT '发表日期',
  `keywords` VARCHAR(50) DEFAULT NULL COMMENT '关键字',
  `createTime` DATE DEFAULT NULL COMMENT '创建时间\n',
  `createBy` VARCHAR(50) DEFAULT NULL COMMENT '收录人',
  `siteId` VARCHAR(50) DEFAULT NULL COMMENT '站点ID\n',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='文献资料';


INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
SELECT 11, 2, 'saturn:literature', NULL, '文献资料', 'literatures', 122, 1 FROM DUAL
WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'saturn:literature') ;

INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
SELECT 11, 3, 'saturn:task', NULL, '任务公告', 'tasks', 122, 1 FROM DUAL
WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'saturn:task') ;


INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
SELECT 11, 6, 'saturn:manager', NULL, '后台管理', NULL, 122, 1 FROM DUAL
WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'saturn:manager') ;


INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
SELECT 11, 3, 'saturn:manager:affair', 'saturn:manager', '专业成果', 'manager/affair', 122, 1 FROM DUAL
WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'saturn:manager:affair') ;

INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
SELECT 11, 4, 'saturn:manager:literature', 'saturn:manager', '文献资料', 'manager/literature', 122, 1 FROM DUAL
WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'saturn:manager:literature') ;

