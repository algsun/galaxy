--author 刘柱
-- 任务记录权限
CREATE TABLE `o_repair_record` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `identifier` INT(11) NOT NULL COMMENT '序号',
  `relicId` INT(11) NOT NULL COMMENT '文物id',
  `repairReasonId` INT(11) NOT NULL COMMENT '因由id',
  `extractDate` DATETIME NOT NULL COMMENT '提取时间',
  `returnDate` DATETIME DEFAULT NULL COMMENT '归还时间',
  `siteId` VARCHAR(36) NOT NULL COMMENT '站点id',
  `state` INT(11) NOT NULL COMMENT '任务状态:1-待分配;2-待接受;3-修复中;4-待审核;5-待点评;6-完成',
  `repairInfo` VARCHAR(500) DEFAULT NULL COMMENT '修复内容',
  `schemeId` INT(11) DEFAULT NULL COMMENT '方案id',
  `institutionId` INT(11) DEFAULT NULL COMMENT '单位di',
  `mainUserId` INT(11) DEFAULT NULL COMMENT '负责人',
  `secondaryUserId` VARCHAR(50) DEFAULT NULL COMMENT '协助人',
  `notByReason` VARCHAR(100) DEFAULT NULL COMMENT '不通过原因',
  `checkUserId` INT(11) DEFAULT NULL COMMENT '审核人id',
  `checkDate` DATETIME DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`),
  KEY `reasonId` (`repairReasonId`),
  KEY `relicId` (`relicId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '任务记录表';


INSERT INTO `t_system_privilege` (
  subsystemId,
  sequence,
  privilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUE (
  4,
  6,
  'orion:repairRecord',
  '任务管理',
  'repairRecords',
  50,
  1
) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:repairRecord' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_system_privilege` (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUES (4,0,'orion:repairRecord:delete','orion:repairRecord','删除','repairRecords/index',50,0),
	 (4,0,'orion:repairRecord:update','orion:repairRecord','编辑','repairRecords/update',50,0),
	 (4,0,'orion:repairRecord:add','orion:repairRecord','添加','repairRecords/new',50,0),
	 (4,0,'orion:repairRecord:accept','orion:repairRecord','分配','repairRecords/accept',50,0),
	 (4,0,'orion:repairRecord:handle','orion:repairRecord','处理(接收、填档案、提交档案)','repairRecords/handle',50,0),
	 (4,0,'orion:repairRecord:check','orion:repairRecord','审核','repairRecords/check',50,0);
	 
	 
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:repairRecord:delete' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:repairRecord:update' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:repairRecord:add' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:repairRecord:accept' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:repairRecord:handle' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:repairRecord:check' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;