--author chenyaofei
--description  删除环境监测首页多余数据

-- 删除角色权限表中关联数据
DELETE FROM t_role_privilege WHERE privilegeId='blueplanet:home:home';

--删除权限表中多余数据
DELETE FROM  t_system_privilege WHERE privilegeId='blueplanet:home:home';