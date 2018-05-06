--author wang.geng
--数据中心，图表组件化，添加站点管理员权限

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:datacenter' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:datacenter:charts' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:datacenter:edit' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:datacenter:delete' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:datacenter:preview' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:datacenter:addlayout' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);