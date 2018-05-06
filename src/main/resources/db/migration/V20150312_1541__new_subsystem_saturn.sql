-- author li.jianfei
-- description 添加成果展示子系统及部分权限

insert into t_subsystem(subsystemId, subsystemName, subsystemCode, remark, enable)
select 11, '成果展示', 'saturn', '土星', 1 from dual
where not exists(select 1 from t_subsystem where subsystemId=11);


INSERT INTO t_system_privilege (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_CN,
  url,
  state,
  isNavigation
) select
  1,
  11,
  'blackhole:subsystem:saturn',
  'blackhole:subsystem',
  '成果展示',
  '',
  122,
  0
from dual
where not exists(select 1 from t_system_privilege where privilegeId='blackhole:subsystem:saturn') ;


INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roldId,
  'blackhole:subsystem:saturn' AS privilegeId
FROM
  `t_roles`
WHERE isManager =1) ;
