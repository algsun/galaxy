-- author liuzhu
-- description biela添加权限

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
  1,
  9,
  'blackhole:subsystem:biela',
  'blackhole:subsystem',
  '区域中心',
  '',
  122,
  0
) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blackhole:subsystem:biela' AS privilegeId
FROM
  `t_roles`
WHERE isManager =1) ;
