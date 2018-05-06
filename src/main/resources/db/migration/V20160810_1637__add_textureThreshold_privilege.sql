 -- author : chenyaofei
 -- description   添加质地阈值权限

 INSERT INTO t_system_privilege VALUES(2,6,'blueplanet:monitor:textureThreshold','blueplanet:monitor','质地阈值','Texture Threshold','texture-threshold',50,1);

 INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blueplanet:monitor:textureThreshold' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;