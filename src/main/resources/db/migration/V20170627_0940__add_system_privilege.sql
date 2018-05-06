--author bai.weixing
-- 添加点评权限

INSERT INTO `t_system_privilege` (
  subsystemId,
  sequence,
  privilegeId,
  parentPrivilegeId,
  privilegeName_zh_CN,
  privilegeName_en_US,
  url,
  state,
  isNavigation
) VALUES (4,0,'orion:repairRecord:comment','orion:repairRecord','点评','comment','repairRecords/comment',50,0);

	 
