-- author : chen.yaofei
-- description : 修改离线数据权限

-- 离线数据权限设置
-- 离线位置点列表
INSERT INTO t_system_privilege VALUES(2,0,'blueplanet:offline:location','blueplanet:offline','离线数据','Off-line data','offline/offline.action',122,1);
UPDATE t_role_privilege   SET privilegeId = 'blueplanet:offline:location'  WHERE privilegeId = 'blueplanet:offline:batch';
DELETE FROM t_system_privilege   WHERE privilegeId = 'blueplanet:offline:batch';

-- 离线位置点添加
INSERT INTO t_system_privilege VALUES(2,1,'blueplanet:offline:location:add','blueplanet:offline:location','离线数据添加','Off-line data add',NULL,122,0);
UPDATE t_role_privilege   SET privilegeId = 'blueplanet:offline:location:add'  WHERE privilegeId = 'blueplanet:offline:batch:add';
DELETE FROM t_system_privilege   WHERE privilegeId = 'blueplanet:offline:batch:add';

-- 离线位置点修改
INSERT INTO t_system_privilege VALUES(2,2,'blueplanet:offline:location:update','blueplanet:offline:location','离线数据修改','Off-line data update',NULL,122,0);
UPDATE t_role_privilege   SET privilegeId = 'blueplanet:offline:location:update'  WHERE privilegeId = 'blueplanet:offline:batch:update';
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:offline:batch:update';

-- 离线位置点删除
INSERT INTO t_system_privilege VALUES(2,3,'blueplanet:offline:location:delete','blueplanet:offline:location','离线数据删除','Off-line data delete',NULL,122,0);

-- 离线数据导入
INSERT INTO t_system_privilege VALUES(2,4,'blueplanet:offline:location:import','blueplanet:offline:location','离线数据导入','Off-line data import',NULL,122,0);

-- 修改离线数据编辑父权限
UPDATE t_system_privilege SET parentPrivilegeId='blueplanet:offline:location' WHERE privilegeId	= 'blueplanet:offline:data:edit';

-- 取消批次数据列表
DELETE FROM t_role_privilege   WHERE privilegeId = 'blueplanet:offline:batch:list';
DELETE FROM t_system_privilege WHERE privilegeId = 'blueplanet:offline:batch:list';