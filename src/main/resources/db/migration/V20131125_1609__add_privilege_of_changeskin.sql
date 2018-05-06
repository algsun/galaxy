-- author xu.yuexi
-- description 添加换肤权限

INSERT INTO t_system_privilege (
  subsystemId,
  sequence,
  privilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUE (
  1,
  10,
  'blackhole:theme',
  '主题',
  'theme.action',
  50,
  1
);

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blackhole:theme' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

