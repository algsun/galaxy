-- author : bai.weixing
-- description : 添加资产管理健康评测权限

-- t_system_privilege添加资产管理健康评测权限
INSERT INTO t_system_privilege(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_zh_CN,privilegeName_en_US,url,state,isNavigation)
SELECT 4,0,'orion:culturalRelic:healthEvaluation','orion:culturalRelic','健康评测','healthEvaluation','',50,0 FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM t_system_privilege WHERE privilegeId='orion:culturalRelic:healthEvaluation');

