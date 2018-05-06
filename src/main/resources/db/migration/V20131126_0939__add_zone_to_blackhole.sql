--author liuzhu
-- 将区域管理一直到系统管理中

-- 系统管理中添加区域管理权限
INSERT INTO t_system_privilege (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
)
VALUES
  (
    1,
    11,
    'blackhole:zone',
    NULL,
    '区域管理',
    'zone',
    122,
    1
  ) ;


--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blackhole:zone' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

--先删除 t_role_privelege 中的数据
DELETE
FROM
  `t_role_privilege`
WHERE privilegeId = 'blueplanet:manage:zone';

--删除环境监控中区域管理的权限
DELETE
FROM
  `t_system_privilege`
WHERE subsystemId = 2
  AND privilegeId = 'blueplanet:manage:zone'