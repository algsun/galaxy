-- author gaohui
-- description 添加控制面板相关权限

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
  4,
  'blueplanet:monitor:controlPanel',
  'blueplanet:monitor',
  '控制面板',
  'control-panel',
  122,
  1
),
(
 2,
 1,
 'blueplanet:monitor:controlPanel:query',
 'blueplanet:monitor:controlPanel',
 '查询',
 'control-panel',
 122,
 0
),
(
 2,
 2,
 'blueplanet:monitor:controlPanel:edit',
 'blueplanet:monitor:controlPanel',
 '编辑',
 'control-panel',
 50,
 0
);

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:monitor:controlPanel' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:monitor:controlPanel:query' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:monitor:controlPanel:edit' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;
