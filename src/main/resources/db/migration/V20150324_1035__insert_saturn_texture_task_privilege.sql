-- author liuzhu
-- description 添加 m_tasks表
INSERT INTO `t_system_privilege` (
  `subsystemId`,
  `sequence`,
  `privilegeId`,
  `parentPrivilegeId`,
  `privilegeName_CN`,
  `url`,
  `state`,
  `isNavigation`
)
VALUES
  ('11','1','saturn:task:index','saturn:manager','任务管理','task/index','122','1'),
  ('11','2','saturn:texture:index','saturn:manager','材质管理','texture/index','122','1') ;

  INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'saturn:task:index' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'saturn:texture:index' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;


