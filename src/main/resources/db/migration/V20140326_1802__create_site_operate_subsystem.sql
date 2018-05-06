-- author liu.zhu
-- 创建站点操作系统表

CREATE TABLE t_logicGroup_subsystem_disable(
	id VARCHAR(36) NOT NULL COMMENT '主键',
	logicGroupId INT(11) NOT NULL COMMENT '站点组id',
        subsystemId INT(11) NOT NULL COMMENT '业务系统id',
	PRIMARY KEY(id),
	FOREIGN KEY(logicGroupId) REFERENCES t_logicgroup(id),
	FOREIGN KEY(subsystemId) REFERENCES t_subsystem(subsystemId),
	UNIQUE KEY `t_logicGroupId_subsystemId` (logicGroupId,subsystemId)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '业务系统开关';

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
  1,
  4,
  'blackhole:currentLogicGroup:logicGroupSubsystem',
  'blackhole:currentLogicGroup',
  '业务系统',
  'logicGroupSubsystem.action',
  20,
  1
) ;


