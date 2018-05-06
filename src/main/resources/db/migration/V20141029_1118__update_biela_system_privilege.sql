-- author li.jianfei
-- description 修改biela系统权限

UPDATE
  t_system_privilege
SET
  sequence = 2
WHERE privilegeId = 'biela:issueInfo' ;

UPDATE
  t_system_privilege
SET
  sequence = 1,
  url = 'mapOverview',
  isNavigation = 1
WHERE privilegeId = 'biela:mapOverview' ;

INSERT INTO t_system_privilege (subsystemId, sequence, privilegeId, parentPrivilegeId, privilegeName_CN, url, state, isNavigation)
SELECT 9, 0, 'biela:evaluate', NULL, '实时评估', 'evaluate', 122, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_system_privilege WHERE privilegeId = 'biela:evaluate') ;