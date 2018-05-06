--author wang.geng
--description 数据中心二级菜单
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
  4,
  'blueplanet:datacenter',
  NULL,
  '图库',
  '',
  122,
  1
),(
  2,
  1,
  'blueplanet:datacenter:charts',
  'blueplanet:datacenter',
  '图库',
  'dataCenter/charts/listLayout',
  122,
  1
) ;