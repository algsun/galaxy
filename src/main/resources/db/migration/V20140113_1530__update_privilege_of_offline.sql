-- author xu.baoji
-- description 修改离线数据权限



DELETE FROM t_role_privilege WHERE  privilegeId LIKE "blueplanet:offline%" ;

DELETE FROM t_system_privilege WHERE  parentprivilegeId LIKE "blueplanet:offline%";

DELETE FROM t_system_privilege WHERE  privilegeId LIKE "blueplanet:offline%";

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
  NULL,
  122,
  1
),
 (
  2,
  5,
  'blueplanet:offline:batch',
  'blueplanet:offline',
  '离线数据',
  'offline/offline.action',
  122,
  1
),
(
  2,
  5,
  'blueplanet:offline:batch:list',
  'blueplanet:offline:batch',
  '批次列表',
  NULL,
  122,
  0
),
(
  2,
  5,
  'blueplanet:offline:batch:add',
  'blueplanet:offline:batch',
  '批次添加',
  NULL,
  122,
  0
),
(
  2,
  5,
  'blueplanet:offline:batch:update',
  'blueplanet:offline:batch',
  '批次修改',
  NULL,
  122,
  0
),
(
  2,
  5,
  'blueplanet:offline:data:edit',
  'blueplanet:offline:batch',
  '数据编辑',
  NULL,
  122,
  0
) ;



INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:offline' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:offline:batch' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;


INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:offline:batch:add' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;


INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:offline:batch:update' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:offline:data:edit' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;


