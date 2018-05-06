-- author liuzhu
-- description 添加首页权限

INSERT INTO `t_system_privilege` (
  `subsystemId`,
  `sequence`,
  `privilegeId`,
  `privilegeName_CN`,
  `url`,
  `state`,
  `isNavigation`
)
VALUES
  ('11','0','saturn:index','首页','index','122','1');

  INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'saturn:index' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

