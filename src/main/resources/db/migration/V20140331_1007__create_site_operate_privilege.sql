-- author liu.zhu
-- 创建权限操作表

CREATE TABLE t_privilege_operate_disable(
	id VARCHAR(36) NOT NULL COMMENT '主键',
	logicGroupId INT(11) NOT NULL COMMENT '站点组id',
  privilegeId VARCHAR(50) NOT NULL COMMENT '权限id',
	PRIMARY KEY(id),
	FOREIGN KEY(logicGroupId) REFERENCES t_logicgroup(id),
	FOREIGN KEY(privilegeId) REFERENCES t_system_privilege(privilegeId),
	UNIQUE KEY `t_logicGroupId_subsystemId` (logicGroupId,privilegeId)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '权限操作表';

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
  5,
  'blackhole:currentLogicGroup:privilegeOperate',
  'blackhole:currentLogicGroup',
  '权限操作',
  'privilegeOperate.action',
  20,
  1
) ;


