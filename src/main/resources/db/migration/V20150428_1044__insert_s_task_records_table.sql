-- author liuzhu
-- description 添加任务记录表

CREATE TABLE `s_task_records` (
  `id` varchar(36) NOT NULL COMMENT 'uuid',
  `taskId` varchar(36) NOT NULL COMMENT '任务id',
  `important` int(11) NOT NULL DEFAULT '1' COMMENT '重要性 (0:重要 1:不重要)',
  `recordDate` datetime NOT NULL COMMENT '记录时间',
  `recordContent` varchar(1000) NOT NULL COMMENT '记录内容',
  `recordUserId` varchar(36) NOT NULL COMMENT '记录人',
  `handleDate` datetime DEFAULT NULL COMMENT '处理时间',
  `handleResult` varchar(1000) DEFAULT NULL COMMENT '处理结果',
  `handleUserId` varchar(36) DEFAULT NULL COMMENT '处理人',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `taskId_FK_m_task_id` (`taskId`),
  CONSTRAINT `taskId_FK_m_task_id` FOREIGN KEY (`taskId`) REFERENCES `s_tasks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务记录表';

INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,7,'saturn:task_records:index','saturn:manager','任务记录','task_records/index',50,0);

INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,1,'saturn:task_records:insert','saturn:task_records:index','新增','task_records/insert',50,0);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,2,'saturn:task_records:update','saturn:task_records:index','修改','task_records/update',50,0);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,3,'saturn:task_records:delete','saturn:task_records:index','删除','task_records/delete',50,0);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,4,'saturn:task_records:handle','saturn:task_records:index','处理','task_records/handle',50,0);


