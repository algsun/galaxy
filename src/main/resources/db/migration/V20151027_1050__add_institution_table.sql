--author 刘柱
-- 单位库房权限
CREATE TABLE `o_institution` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `siteId` VARCHAR(20) NOT NULL COMMENT '站点id',
  `name` VARCHAR(100) NOT NULL COMMENT '名称',
  `seat` VARCHAR(100) NOT NULL COMMENT '所在地',
  `mailing` VARCHAR(100) NOT NULL COMMENT '通讯地址',
  `zipcode` INT(11) NOT NULL COMMENT '邮编',
  `qualification` VARCHAR(100) NOT NULL COMMENT '资质证书',
  `code` VARCHAR(50) NOT NULL COMMENT '代号',
  `institutionType` INT(1) NOT NULL COMMENT '0:设计单位 1:收藏单位 2:修复单位',
  `createDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '单位表';


CREATE TABLE `o_institution_room` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `institutionId` INT(11) NOT NULL COMMENT '单位id',
  `roomName` VARCHAR(50) NOT NULL COMMENT '库房名称',
  PRIMARY KEY (`id`),
  KEY `FK_institution_Id` (`institutionId`),
  CONSTRAINT `FK_institution_Id` FOREIGN KEY (`institutionId`) REFERENCES `o_institution` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '单位库房';


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
  5,
  'orion:institution',
  '单位管理',
  'institution/index',
  50,
  1
) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:institution' AS privilegeId
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
) VALUES (4,0,'orion:institution:delete','orion:institution','单位删除','institution/index',50,0),
	 (4,0,'orion:institution:update','orion:institution','单位编辑','institution/update',50,0),
	 (4,0,'orion:institution:add','orion:institution','单位添加','institution/add',50,0),
	 (4,0,'orion:institution:addRoom','orion:institution','单位库房','institution/add',50,0);
	 
	 
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:institution:delete' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:institution:update' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:institution:add' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:institution:addRoom' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;