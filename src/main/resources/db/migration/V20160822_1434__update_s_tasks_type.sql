 -- author : liuzhu
 -- description 成果展示任务表修改字段类型

ALTER TABLE s_tasks MODIFY taskDescription TEXT NOT NULL COMMENT '任务描述';
ALTER TABLE s_tasks MODIFY taskTarget TEXT NOT NULL COMMENT '任务目标';
ALTER TABLE s_tasks MODIFY demand TEXT NOT NULL COMMENT '需求保障';
ALTER TABLE s_tasks MODIFY remark TEXT COMMENT '备注';

ALTER TABLE s_tasks MODIFY responsible VARCHAR(200) NOT NULL COMMENT '负责人';
ALTER TABLE s_tasks MODIFY participate VARCHAR(200) NOT NULL COMMENT '参与人';

ALTER TABLE s_task_records MODIFY recordContent TEXT NOT NULL COMMENT '记录内容';
ALTER TABLE s_task_records MODIFY handleResult TEXT COMMENT '处理结果';

ALTER TABLE `s_task_records` CHANGE COLUMN recordUserId recordUserName VARCHAR(200) COMMENT '记录人';
ALTER TABLE `s_task_records` CHANGE COLUMN handleUserId handleUserName VARCHAR(200) COMMENT '处理人';