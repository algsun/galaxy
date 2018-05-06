-- author li.jianfei
-- description 添加文物注销权限
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
    4,
    0,
    'orion:culturalRelic:logOut',
    'orion:culturalRelic',
    '注销',
    'logOutRelic.action',
    50,
    0
  ) ;


INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'orion:culturalRelic:logOut' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);
