-- author 刘柱
-- description 改变环境监控导航
-- date 2015-6-18

INSERT INTO `t_system_privilege`(`subsystemId`,`sequence`,`privilegeId`,`privilegeName_CN`,`state`,`isNavigation`)
VALUES ('2','1','blueplanet:controller','调控','122','1');
        
INSERT INTO `t_system_privilege`(`subsystemId`,`sequence`,`privilegeId`,`parentPrivilegeId`,`privilegeName_CN`,`url`,`state`,`isNavigation`)
VALUES ('2','0','blueplanet:controller:index','blueplanet:controller','调控主页','controller/index','122',1);
 
UPDATE `t_system_privilege` SET parentPrivilegeId = 'blueplanet:controller' WHERE parentPrivilegeId = 'blueplanet:alarm';
 
UPDATE `t_system_privilege` SET parentPrivilegeId = 'blueplanet:controller' WHERE privilegeId = 'blueplanet:monitor:controlPanel';
 
UPDATE  `t_system_privilege` SET privilegeName_CN ='监测',sequence = 0 WHERE privilegeId = 'blueplanet:monitor';

UPDATE  `t_system_privilege` SET parentPrivilegeId = 'blueplanet:monitor',privilegeName_CN ='统计分析',sequence = 4 WHERE privilegeId = 'blueplanet:statistics:report';

DELETE FROM `t_role_privilege` WHERE privilegeId = 'blueplanet:statistics';

DELETE FROM `t_system_privilege` WHERE privilegeId = 'blueplanet:statistics';

DELETE FROM `t_role_privilege` WHERE privilegeId = 'blueplanet:alarm';

DELETE FROM `t_system_privilege` WHERE privilegeId = 'blueplanet:alarm';

INSERT INTO `t_role_privilege` (roleId, privilegeId) (SELECT id AS roleId,'blueplanet:controller' AS privilegeId FROM `t_roles` WHERE isManager = 1);

INSERT INTO `t_role_privilege` (roleId, privilegeId) (SELECT id AS roleId,'blueplanet:controller:index' AS privilegeId FROM `t_roles` WHERE isManager = 1);

