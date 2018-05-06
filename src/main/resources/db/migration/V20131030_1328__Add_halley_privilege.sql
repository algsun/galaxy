INSERT INTO t_system_privilege (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) 
VALUES
  (
    1,
    7,
    'blackhole:subsystem:halley',
    'blackhole:subsystem',
    '外展管理',
    '',
    122,
    0
  ) ;
  
INSERT INTO t_role_privilege (roleId, privilegeId) 
(SELECT 
  id AS roldId,
  'blackhole:subsystem:halley' AS privilegeId 
FROM
  t_roles 
WHERE isManager = 1) ;




