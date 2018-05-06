--author liuzhu
--description 添加阈值报警权限
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
  2,
  1,
  'blueplanet:zone',
  NULL,
  '区域',
  '',
  50,
  0
) ;

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
  2,
  1,
  'blueplanet:zone:thresholdAlarm',
  'blueplanet:zone',
  '阈值报警',
  '',
  50,
  0
) ;

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:zone' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:zone:thresholdAlarm' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1)  ;