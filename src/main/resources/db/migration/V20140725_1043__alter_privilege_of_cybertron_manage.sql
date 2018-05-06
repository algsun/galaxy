-- author xu.yuexi
-- description 更改档案管理权限

DELETE FROM t_role_privilege WHERE privilegeId ="cybertron:manage";

UPDATE  t_system_privilege SET parentPrivilegeId =  NULL,state = 50,isNavigation =0 WHERE privilegeId LIKE 'cybertron:manage:%';

DELETE FROM t_system_privilege WHERE privilegeId ="cybertron:manage";


