--author chen.yaofei
-- 任务表siteId改logicGroupId

ALTER TABLE `t_task` CHANGE siteId logicGroupId INT NOT NULL COMMENT '站点组id';

UPDATE `t_task` AS t SET t.logicGroupId = (SELECT id FROM `t_logicgroup` AS tl WHERE t.logicGroupId = tl.siteId);
