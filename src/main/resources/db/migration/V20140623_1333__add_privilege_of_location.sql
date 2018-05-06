 -- author xuyuexi
-- 添加权限位置点管理

INSERT INTO `t_system_privilege`
(`subsystemId`,
 `sequence`,
 `privilegeId`,
 `parentPrivilegeId`,
 `privilegeName_CN`,
 `url`,
 `state`,
 `isNavigation`)
VALUES
(2,
 3,
 'blueplanet:manage:location',
 'blueplanet:manage',
 '位置点管理',
 'queryLocations',
 50,
 1),
 (2,
 0,
 'blueplanet:manage:location:delete',
 'blueplanet:manage:location',
 '删除',
 '',
 50,
 1),
 (
2,
 0,
 'blueplanet:manage:location:edit',
 'blueplanet:manage:location',
 '编辑',
 '',
 50,
 1
 ),
 (
2,
 0,
 'blueplanet:manage:location:query',
 'blueplanet:manage:location',
 '查询',
 '',
 50,
 1
 );

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:manage:location' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:manage:location:query' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:manage:location:edit' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'blueplanet:manage:location:delete' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;


