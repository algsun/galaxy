--author liuzhu
--添加信息发布
--给管理员分配权限

INSERT INTO `t_system_privilege` (
  subsystemId,
  sequence,
  privilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) VALUE (9,1,'biela:issueInfo','信息发布','issueInfo',122,1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'biela:issueInfo' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);