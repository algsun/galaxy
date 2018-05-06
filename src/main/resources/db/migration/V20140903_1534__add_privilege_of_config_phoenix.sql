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
(6,
 100,
 'phoenix:config',
 NULL,
 '设置',
 NULL,
122,
 1),
 (6,
 1,
 'phoenix:config:zone',
 'phoenix:config',
 '设置需统计区域',
 'uma/configPhoenix',
122,
 1)
 ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'phoenix:config' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'phoenix:config:zone' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;