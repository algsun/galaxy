-- author liuzhu
-- description 添加权限

CREATE TABLE `s_tasks` (
  `id` VARCHAR(36) NOT NULL COMMENT '自增id',
  `title` VARCHAR(200) NOT NULL COMMENT '任务标题',
  `siteId` VARCHAR(36) NOT NULL COMMENT '站点id',
  `responsible` INT(11) NOT NULL COMMENT '负责人',
  `participate` INT(11) NOT NULL COMMENT '参与人',
  `updateDate` DATETIME DEFAULT NULL COMMENT '更新时间',
  `predictStart` DATETIME DEFAULT NULL COMMENT '预计开始时间',
  `predictEnd` DATETIME DEFAULT NULL COMMENT '预计结束时间',
  `realStart` DATETIME DEFAULT NULL COMMENT '实际开始时间',
  `realEnd` DATETIME DEFAULT NULL COMMENT '实际结束时间',
  `taskDescription` VARCHAR(200) NOT NULL COMMENT '任务描述',
  `taskTarget` VARCHAR(2000) NOT NULL COMMENT '任务目标',
  `demand` VARCHAR(2000) NOT NULL COMMENT '需求保障',
  `remark` VARCHAR(1000) DEFAULT NULL COMMENT '备注',
  `createUser` INT(11) NOT NULL COMMENT '创建人',
  `createDate` DATETIME NOT NULL COMMENT '创建时间',
  `checkUser` INT(11) DEFAULT NULL COMMENT '审核人',
  `checkDate` DATETIME DEFAULT NULL COMMENT '审核时间',
  `invalidUser` INT(11) DEFAULT NULL COMMENT '作废人',
  `invalidDate` DATETIME DEFAULT NULL COMMENT '作废时间',
  `state` INT(11) NOT NULL DEFAULT '0' COMMENT '0:待审批 1:进行中 2:已完成 3:已作废',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='任务管理表';

