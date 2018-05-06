-- author : bai.weixing
-- description : 添加资产管理外展数据分析权限

-- t_system_privilege添加资产管理外展数据分析权限
INSERT INTO t_system_privilege(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_zh_CN,privilegeName_en_US,url,state,isNavigation)
SELECT 6,5,'phoenix:orion:outreachDataAnalysis','phoenix:orion','外展数据分析','OutreachDataAnalysis','orion/exhibition-stat',122,1 FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM t_system_privilege WHERE privilegeId='phoenix:orion:outreachDataAnalysis');

-- t_role_privilege添加资产管理外展数据分析权限
DELETE FROM t_role_privilege WHERE privilegeId='phoenix:orion:outreachDataAnalysis' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO t_role_privilege(roleId,privilegeId)
SELECT id AS roleId,'phoenix:orion:outreachDataAnalysis' AS privilegeId FROM t_roles WHERE isManager=1;