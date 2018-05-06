  --author liuzhu
--description 添加设备删除

  
INSERT INTO t_system_privilege (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUE (
  2,
  0,
  'blueplanet:manage:device:delete',
  'blueplanet:manage:device',
  '删除',
  '',
  20,
  0
) ;

