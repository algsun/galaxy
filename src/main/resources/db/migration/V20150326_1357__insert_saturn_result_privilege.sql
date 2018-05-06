-- author liuzhu
-- description 添加成果展示权限

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
  ('11','3','saturn:result','成果展示','result','122','1');

  INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'saturn:result' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;


