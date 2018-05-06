--王耕
--設備刪除權限

DELETE FROM `t_role_privilege` WHERE privilegeId='blueplanet:manage:device:delete' AND roleId IN (SELECT id FROM t_roles WHERE isManager=1);
INSERT INTO `t_role_privilege`(roleId, privilegeId)
SELECT id ,'blueplanet:manage:device:delete' FROM t_roles WHERE isManager =1;
