-- author liuzhu
-- description 添加数据统计权限，并修改后台管理权限sequence

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
  ('11','5','saturn:statistics','数据统计','statistics/repair','122','1');

  INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'saturn:statistics' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;


UPDATE 
  `t_system_privilege` 
SET
  `sequence` = 6
WHERE `privilegeId` = 'saturn:manager' ;


