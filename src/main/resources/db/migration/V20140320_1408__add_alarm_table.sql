--author liuzhu
-- description: 添加监测预警表，权限

CREATE TABLE m_measure(
	id VARCHAR(36) NOT NULL COMMENT '主键',
	description VARCHAR(500) NOT NULL COMMENT '措施描述',
	siteId VARCHAR(50) NOT NULL COMMENT '站点id',
	createTime TIMESTAMP NOT NULL COMMENT '创建时间',
	PRIMARY KEY(id)

)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '措施表';

CREATE TABLE m_zone_measure(
	id VARCHAR(36) NOT NULL COMMENT '主键',
	zoneId VARCHAR(50) NOT NULL COMMENT '区域id',
	measureId VARCHAR(36) NOT NULL COMMENT '措施id',
	PRIMARY KEY(id),
	FOREIGN KEY(zoneId) REFERENCES t_zone(zoneId),
	FOREIGN KEY(measureId) REFERENCES m_measure(id),
	UNIQUE KEY `m_zone_measure_zoneId_measureid` (`zoneId`,`measureId`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '区域措施表';


CREATE TABLE m_alarm_history(
	id VARCHAR(36) NOT NULL COMMENT '主键',
	zoneId VARCHAR(50) NOT NULL COMMENT '区域id',
	sensorId INT(11) NOT NULL COMMENT '监测指标id',
	factor VARCHAR(500) NOT NULL COMMENT '因素',
	siteId VARCHAR(50) NOT NULL COMMENT '站点id',
	alarmTime TIMESTAMP NOT NULL COMMENT '报警时间',
	PRIMARY KEY(id),
	FOREIGN KEY(zoneId) REFERENCES t_zone(zoneId),
	FOREIGN KEY(sensorId) REFERENCES m_sensorinfo(sensorPhysicalid),
	FOREIGN KEY(siteId) REFERENCES t_logicgroup(siteId)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '报警记录表';


CREATE TABLE m_history_measure(
	id VARCHAR(36) NOT NULL COMMENT '主键',
	alarmHistoryId VARCHAR(36) NOT NULL COMMENT '报警id',
	measureId VARCHAR(36) NOT NULL COMMENT '措施id',
	PRIMARY KEY(id),
	FOREIGN KEY(alarmHistoryId) REFERENCES  m_alarm_history(id),
	FOREIGN KEY(measureId) REFERENCES m_measure(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '报警措施表';


-- 阈值添加发起人
ALTER TABLE `m_threshold_option` ADD releaser INT(11) NOT NULL COMMENT '发起人';

-- 修改阈值的时间类型
ALTER TABLE m_threshold_option MODIFY beforeTime TIME COMMENT '免打扰时段：时间点之前';
ALTER TABLE m_threshold_option MODIFY afterTime TIME COMMENT '免打扰时段：时间点之后';

INSERT INTO `t_system_privilege` (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUES
 (2,6,'blueplanet:alarm',NULL,'监测预警','',122,1),
 (2,1,'blueplanet:alarm:index','blueplanet:alarm','监测预警','alarm/index',122,1),
 (2,2,'blueplanet:alarm:measure','blueplanet:alarm','措施管理','alarm/measure',122,1),
 (2,3,'blueplanet:alarm:record','blueplanet:alarm','报警记录','alarm/history',122,1);

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:alarm' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:alarm:index' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1)  ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:alarm:measure' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1)  ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:alarm:record' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1)  ;

-- 修改表名
ALTER  TABLE m_threshold RENAME TO m_threshold_sensor;
ALTER  TABLE m_threshold_option RENAME TO m_threshold;







