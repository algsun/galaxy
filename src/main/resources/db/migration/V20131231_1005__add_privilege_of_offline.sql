-- author xu.yuexi
-- description 添加离线数据权限

INSERT INTO t_system_privilege (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUES (
  2,
  5,
  'blueplanet:offline',
  NULL,
  '离线数据',
  'offline/offline.action',
  122,
  1
),
(
  2,
  1,
  'blueplanet:offline:offlineData',
  'blueplanet:offline',
  '离线数据',
  'offline/offline.action',
  122,
  1
);

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:offline' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:offline:offlineData' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

