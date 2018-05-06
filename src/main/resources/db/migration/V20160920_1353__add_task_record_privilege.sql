 -- author : chenyaofei
 -- description : 成果展示任务管理添加审核与完成权限

-- t_system_privilege表添加权限
 INSERT INTO t_system_privilege
  SELECT 11,5,'saturn:task_records:check','saturn:task_records:index','审核','Check',NULL,50,0 FROM DUAL
  WHERE NOT EXISTS
  (SELECT privilegeId FROM t_system_privilege WHERE privilegeId='saturn:task_records:check');

 INSERT INTO t_system_privilege
  SELECT 11,6,'saturn:task_records:finish','saturn:task_records:index','完成','Finish',NULL,50,0 FROM DUAL
  WHERE NOT EXISTS
  (SELECT privilegeId FROM t_system_privilege WHERE privilegeId='saturn:task_records:finish');


-- t_role_privilege表管理员添加权限
DELETE FROM t_role_privilege
 WHERE privilegeId='saturn:task_records:check'
 AND roleId IN(SELECT id FROM t_roles WHERE isManager = 1);

 INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT id AS roleId,'saturn:task_records:check' AS privilegeId FROM t_roles WHERE isManager = 1) ;

DELETE FROM t_role_privilege
 WHERE privilegeId='saturn:task_records:finish'
 AND roleId IN(SELECT id FROM t_roles WHERE isManager = 1);

 INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT id AS roleId,'saturn:task_records:finish' AS privilegeId FROM t_roles WHERE isManager = 1) ;