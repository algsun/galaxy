-- author : chen.yaofei
-- description : 添加数据分析环境监测中的数据分布权限

-- t_system_privilege添加区域数据分布权限
INSERT INTO t_system_privilege(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_zh_CN,privilegeName_en_US,url,state,isNavigation)
SELECT 6,5,'phoenix:blueplanet:dataDistribution','phoenix:blueplanet','区域数据分布',NULL,'blueplanet/zoneDataDistribution',122,1 FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM t_system_privilege WHERE privilegeId='phoenix:blueplanet:dataDistribution');

-- t_role_privilege管理员添加区域数据分布权限
DELETE FROM t_role_privilege WHERE privilegeId='phoenix:blueplanet:dataDistribution' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO t_role_privilege(roleId,privilegeId)
SELECT id AS roleId,'phoenix:blueplanet:dataDistribution' AS privilegeId FROM t_roles WHERE isManager=1;