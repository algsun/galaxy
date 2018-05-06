 -- author xiedeng
-- 添加表 m_location  m_location_history

-- 位置点
CREATE TABLE m_location (
  id VARCHAR (36) NOT NULL COMMENT '主键',
  locationName VARCHAR (50) NOT NULL COMMENT '位置点名称',
  nodeId VARCHAR (20) NULL COMMENT '节点id',
  zoneId VARCHAR (36) NOT NULL COMMENT '区域id',
  coordinateX FLOAT NOT NULL DEFAULT '-1' COMMENT '在父区域平面图的x坐标',
  coordinateY FLOAT NOT NULL DEFAULT '-1' COMMENT '在父区域平面图的y坐标',
  createTime TIMESTAMP NOT NULL COMMENT '开始时间',
  PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '位置点表' ;

-- 位置点历史
CREATE TABLE m_location_history (
  id VARCHAR (36) NOT NULL COMMENT '主键',
  locationId VARCHAR (36) NOT NULL COMMENT '位置点id',
  nodeId VARCHAR (20) NOT NULL COMMENT '节点id',
  startTime TIMESTAMP NULL COMMENT '开始时间',
  endTime TIMESTAMP NULL COMMENT '结束时间',
  FOREIGN KEY(locationId) REFERENCES m_location(id),
PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '位置点历史记录' ;

INSERT INTO `t_system_privilege` (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUE (
  2,
  4,
  'blueplanet:zone:location',
  'blueplanet:zone',
  '位置点',
  '',
  50,
  0
) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:zone:location' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;