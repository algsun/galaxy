--author xu.yuexi
--description 创建控制模块通知设置


CREATE TABLE m_control_module_notification(
id VARCHAR(36) PRIMARY KEY  COMMENT "主键uuid",
siteId varchar(8) NOT NULL COMMENT "站点ID",
userId INT(11) NOT NULL COMMENT "用户id",
subscribeType INT(2) NOT NULL COMMENT "全部设备或者自定义设备，全部设备存1，自定义设备存2",
triggerEvent INT(2) NOT NULL COMMENT "发送事件：1电池供电，2开关切换，3全选",
notifyMethod INT(2) NOT NULL COMMENT "通知方式：1短信，2邮件，3全选",
beginTime DATE COMMENT "免打扰时间段，开始时间",
endTime DATE COMMENT "免打扰时间段，结束时间",
UNIQUE INDEX (siteId, userId),
FOREIGN KEY(userId) REFERENCES t_users(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '控制模块通知设置';

CREATE TABLE m_control_module_notification_device(
id VARCHAR(36) PRIMARY KEY  COMMENT "主键uuid",
notifyId VARCHAR(36) NOT NULL COMMENT "外键",
deviceId VARCHAR(20) NOT NULL COMMENT "控制模块Id",
UNIQUE INDEX (notifyId, deviceId),
FOREIGN KEY(notifyId) REFERENCES m_control_module_notification(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '控制模块通知设置--选择控制模块';