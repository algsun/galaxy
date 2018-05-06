-- author 李建飞
-- description 添加各系统 首页 权限

INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 2, 0, 'blueplanet:home', NULL, '首页', '../blackhole/subsystems.action', 122, 1 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'blueplanet:home') ;

DELETE FROM `t_role_privilege` WHERE privilegeId='blueplanet:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'blueplanet:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;

INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 2, 1, 'blueplanet:home:home', 'blueplanet:home', '首页', '../blackhole/subsystems.action', 122, 0 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'blueplanet:home:home') ;

DELETE FROM `t_role_privilege` WHERE privilegeId='blueplanet:home:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'blueplanet:home:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;



UPDATE t_system_privilege SET privilegeName_CN = '首页', url = '../blackhole/subsystems.action', state = 122 WHERE privilegeId = 'proxima:home';

DELETE FROM `t_role_privilege` WHERE privilegeId='proxima:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'proxima:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;


INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 4, 0, 'orion:home', NULL, '首页', '../blackhole/subsystems.action', 122, 1 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'orion:home') ;

DELETE FROM `t_role_privilege` WHERE privilegeId='orion:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'orion:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;



UPDATE t_system_privilege SET privilegeName_CN = '首页', url = '../blackhole/subsystems.action', state = 122, isNavigation = 1 WHERE privilegeId = 'uma:home';

DELETE FROM `t_role_privilege` WHERE privilegeId='uma:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'uma:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;



INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 8, 0, 'halley:home', NULL, '首页', '../blackhole/subsystems.action', 122, 1 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'halley:home') ;

DELETE FROM `t_role_privilege` WHERE privilegeId='halley:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'halley:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;


INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 9, 0, 'biela:home', NULL, '首页', '../blackhole/subsystems.action', 122, 1 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'biela:home') ;

DELETE FROM `t_role_privilege` WHERE privilegeId='biela:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'biela:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;


INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 10, 0, 'cybertron:home', NULL, '首页', '../blackhole/subsystems.action', 122, 1 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'cybertron:home') ;

DELETE FROM `t_role_privilege` WHERE privilegeId='cybertron:home' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'cybertron:home' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;

