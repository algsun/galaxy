-- author xu.yuexi
-- 创建控制模块条件表

DROP TABLE IF EXISTS m_control_module_sensor_condition;
DROP TABLE IF EXISTS m_control_module_sensor_action;
CREATE TABLE m_control_module_sensor_action(
id VARCHAR(36) PRIMARY KEY COMMENT 'uuid',
controlModuleId VARCHAR(20) NOT NULL  COMMENT '控制模块ID uuid',
route INT NOT NULL UNIQUE COMMENT '路数',
`action` INT NOT NULL COMMENT '满足条件的动作 0关1开',
`logic` INT NOT NULL DEFAULT 1 COMMENT '逻辑关系 1与，2或',
`updateTime` TIMESTAMP NOT NULL COMMENT '更新时间'
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '控制模块条件逻辑表';


CREATE TABLE m_control_module_sensor_condition(
id VARCHAR(36) PRIMARY KEY COMMENT 'uuid',
sensorActionId VARCHAR(36) NOT NULL  COMMENT ',逻辑Id uuid 外键',
deviceId VARCHAR(20) NOT NULL COMMENT '设备ID',
sensorId INT NOT NULL COMMENT '监测ID',
`operator` INT NOT NULL COMMENT '条件 1>,2<,3=',
`value`  DOUBLE NOT NULL COMMENT '条件的值',
`updateTime` TIMESTAMP NOT NULL COMMENT '更新时间',
FOREIGN KEY (`sensorActionId`) REFERENCES `m_control_module_sensor_action` (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '控制模块条件动作表';

