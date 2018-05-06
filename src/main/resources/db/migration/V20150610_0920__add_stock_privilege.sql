-- author li.jianfei
-- description 添加大数据分析预警功能权限
-- date 2015-06-10

INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 2, 1, 'blueplanet:location', NULL, '位置点', NULL, 50, 0 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'blueplanet:location') ;

INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_CN`, `url`, `state`, `isNavigation`)
  SELECT 2, 1, 'blueplanet:location:stock', 'blueplanet:location', '大数据分析预警', NULL, 50, 0 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'blueplanet:location:stock') ;

DELETE FROM `t_role_privilege` WHERE privilegeId='blueplanet:location' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'blueplanet:location' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;

DELETE FROM `t_role_privilege` WHERE privilegeId='blueplanet:location:stock' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'blueplanet:location:stock' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;
