-- author: liuzhu
-- description: 历史数据导出加权限

INSERT INTO `t_system_privilege` (`subsystemId`, `sequence`, `privilegeId`, `parentPrivilegeId`, `privilegeName_zh_CN`, `url`, `state`, `isNavigation`)
  SELECT 2, 2, 'blueplanet:location:export', 'blueplanet:location', '导出', NULL, 50, 0 FROM DUAL
  WHERE NOT EXISTS
  (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'blueplanet:location:export') ;


DELETE FROM `t_role_privilege` WHERE privilegeId='blueplanet:location:export' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege` (roleId, privilegeId)
  (SELECT id AS roldId, 'blueplanet:location:export' AS privilegeId FROM `t_roles` WHERE isManager = 1) ;
