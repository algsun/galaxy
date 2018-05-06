-- author : chen.yaofei
-- description : 修改权限表url并且删除系统维护基准点，修改t_site表故宫博物馆高德地图坐标

-- 删除基准点
DELETE FROM t_role_privilege WHERE privilegeId = 'blueplanet:manage:standard' ;
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:manage:standard' ;

-- 资产管理页面URL修改
UPDATE t_system_privilege SET url = 'toAddRelic.action' WHERE privilegeId='orion:culturalRelic:add';
UPDATE t_system_privilege SET url = 'queryRelic.action' WHERE privilegeId='orion:culturalRelic';

-- 故宫博物院高德地图坐标
UPDATE t_site SET lngAmap='116.397026',latAmap='39.918058' WHERE siteId='11010101';


