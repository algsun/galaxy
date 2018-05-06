-- author 王耕
-- description 3d模型管理相关脚本，包括权限，表的新建
-- date 2015-7-13
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:monitor:threedimensional';
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES (2,5,'blueplanet:monitor:threedimensional','blueplanet:monitor','3d模型管理','three-dimensional',122,1);
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:monitor:threedimensional:preview';
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,state,isNavigation)
VALUES (2,0,'blueplanet:monitor:threedimensional:preview','blueplanet:monitor:threedimensional','浏览',122,0);
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:monitor:threedimensional:look';
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,state,isNavigation)
VALUES (2,0,'blueplanet:monitor:threedimensional:look','blueplanet:monitor:threedimensional','分析',122,0);
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:monitor:threedimensional:edit';
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,state,isNavigation)
VALUES (2,0,'blueplanet:monitor:threedimensional:edit','blueplanet:monitor:threedimensional','编辑',122,0);
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:monitor:threedimensional:delete';
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,state,isNavigation)
VALUES (2,0,'blueplanet:monitor:threedimensional:delete','blueplanet:monitor:threedimensional','删除',122,0);

DROP TABLE IF EXISTS m_three_dimensional;
CREATE TABLE `m_three_dimensional` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `siteId` VARCHAR (50) NOT NULL COMMENT '站点ID',
  `updatetime` DATETIME NOT NULL COMMENT '模型上传时间',
  `path` VARCHAR(50) NOT NULL COMMENT '上传路径',
  `remark` VARCHAR(50) NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '3d模型管理列表' ;

DROP TABLE IF EXISTS m_dimensional_location;
CREATE TABLE `m_dimensional_location` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `siteId` VARCHAR (50) NOT NULL COMMENT '站点ID',
  `dimensionalId` INT(11) NOT NULL COMMENT '模型id',
  `locationId` VARCHAR(36) NOT NULL COMMENT '位置点id',
  `coordinateX` FLOAT NOT NULL DEFAULT 0 COMMENT '3d温湿度场坐标X',
  `coordinateY` FLOAT NOT NULL DEFAULT 0 COMMENT '3d温湿度场坐标Y',
  `coordinateZ` FLOAT NOT NULL DEFAULT 0 COMMENT '3d温湿度场坐标Z',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '3d模型与位置点关系表' ;