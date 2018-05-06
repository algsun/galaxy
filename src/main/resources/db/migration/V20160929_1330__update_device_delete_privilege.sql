-- author : chen.yaofei
-- description : 修改设备删除权限

-- 修改设备删除权限状态,初始化站点管理员拥有删除设备权限
UPDATE t_system_privilege SET state=50 WHERE privilegeId='blueplanet:manage:device:delete';

-- 管理员添加设备删除权限
DELETE FROM t_role_privilege
WHERE roleId IN (SELECT id FROM t_roles WHERE isManager = 1)
AND privilegeId = 'blueplanet:manage:device:delete';

INSERT INTO t_role_privilege (roleId, privilegeId)
(SELECT id AS roleId,'blueplanet:manage:device:delete' AS privilegeId
FROM t_roles  WHERE isManager = 1);