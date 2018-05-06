--author liuzhu
--description 添加网络拓扑图权限
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
  3,
  'blueplanet:monitor:topo',
  'blueplanet:monitor',
  '网络拓扑图',
  'topo.action',
  50,
  1
) ;

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:monitor:topo' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1)  ;