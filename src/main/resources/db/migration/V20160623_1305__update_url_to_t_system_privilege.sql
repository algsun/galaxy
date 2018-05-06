-- author:chen.yaofei
-- description 修改资产管理藏品信息查询url

UPDATE t_system_privilege SET url='queryRelic.action' WHERE privilegeId = 'orion:culturalRelic:list';