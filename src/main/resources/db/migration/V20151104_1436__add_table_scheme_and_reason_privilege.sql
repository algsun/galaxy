--liuzhu
--方案表与原因表权限
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
  7,
  'orion:scheme',
  '方案管理',
  'schemes/index',
  50,
  1
) ;

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
  8,
  'orion:reason',
  '因由管理',
  'reason/index',
  50,
  1
) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:scheme' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:reason' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;