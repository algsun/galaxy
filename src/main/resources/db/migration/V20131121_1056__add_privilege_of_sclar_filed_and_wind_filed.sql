-- author gaohui
-- description 添加标量场，风场 权限

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
  2,
  'blueplanet:zone:scalarFiled',
  'blueplanet:zone',
  '标量场',
  '',
  50,
  0
),(
  2,
  3,
  'blueplanet:zone:windFiled',
  'blueplanet:zone',
  '风场',
  '',
  50,
  0
);

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:zone:scalarFiled' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:zone:windFiled' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;
