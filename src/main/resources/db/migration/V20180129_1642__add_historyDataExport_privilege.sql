-- author : bai.weixing
-- description : 添加历史数据导出权限

-- t_system_privilege添加历史数据导出权限
INSERT INTO t_system_privilege(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_zh_CN,privilegeName_en_US,url,state,isNavigation)
SELECT 2,4,'blueplanet:manage:historyDataExport','blueplanet:manage','历史数据导出','history data export','history-data-export',50,1 FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM t_system_privilege WHERE privilegeId='blueplanet:manage:historyDataExport');

