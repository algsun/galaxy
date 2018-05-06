-- author gaohui
-- description 添加控制模块相关表

CREATE TABLE m_control_module_status (
  id VARCHAR(36),
  node_id VARCHAR (20) NOT NULL COMMENT '控制模块ID',
  `enable` INT NOT NULL DEFAULT 0 COMMENT '启用状态, 从低位开始, 1 启用，0 禁用',
  switch INT NOT NULL DEFAULT 0 COMMENT '开关状态, 从低位开始, 1 开，0 关',
  create_time TIMESTAMP DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '控制模块状态表' ;

CREATE TABLE `m_control_module_switch` (
  id VARCHAR (36),
  node_id VARCHAR (20) NOT NULL COMMENT '控制模块ID',
  route INT NOT NULL DEFAULT 1 COMMENT '路数，从 1 开始',
  alias VARCHAR (50) NOT NULL DEFAULT '' COMMENT '别名/备注',
  PRIMARY KEY (id),
  UNIQUE INDEX (node_id, route)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '控制模块端口表' ;

CREATE TABLE m_control_module_switch_change (
  id VARCHAR(36),
  node_id VARCHAR (20) NOT NULL COMMENT '控制模块ID',
  `enable_before` INT NOT NULL DEFAULT 0 COMMENT '启用状态, 从低位开始, 1 启用，0 禁用',
  switch_before INT NOT NULL DEFAULT 0 COMMENT '开关状态, 从低位开始, 1 开，0 关',
  create_time_before TIMESTAMP DEFAULT 0 COMMENT '创建时间',
  `enable_after` INT NOT NULL DEFAULT 0 COMMENT '启用状态, 从低位开始, 1 启用，0 禁用',
  switch_after INT NOT NULL DEFAULT 0 COMMENT '开关状态, 从低位开始, 1 开，0 关',
  create_time_after TIMESTAMP DEFAULT 0 COMMENT '创建时间',
  route INT NOT NULL DEFAULT 1 COMMENT '变化线路',
  `action` INT NOT NULL DEFAULT 0 COMMENT '动作：1. 开, 0. 关',
  PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '控制模块状态变化表' ;

CREATE TABLE m_control_module_condition_rfl (
  id VARCHAR(36),
  node_id VARCHAR (20) NOT NULL COMMENT '控制模块ID',
  route INT NOT NULL DEFAULT 1 COMMENT '路数，从 1 开始',
  sub_node_id VARCHAR (20) COMMENT '参与条件反射设备ID',
  sensorId INT COMMENT '监测指标标识',
  lowLeft INT COMMENT '低阈值 左值',
  low INT COMMENT '低阈值',
  lowRight INT COMMENT '低阈值 右值',
  lowTarget DOUBLE DEFAULT 0 COMMENT '低阈值的结果',
  highLeft INT COMMENT '高阈值 左值',
  high INT COMMENT '高阈值',
  highRight INT COMMENT '高阈值 右值',
  highTarget DOUBLE DEFAULT 0 COMMENT '高阈值的结果',
  `action` INT NOT NULL DEFAULT 8 COMMENT '动作, 0 无条件关; 2 范围内开，范围外关; 3 高于高阈值开，低于低阈值关; 4 高于高阈值关，低于低阈值开;5 范围外关，范围外开;7 无条件开;8 无条件反射',
  update_time TIMESTAMP NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE INDEX (node_id, route)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '条件反射参数' ;

CREATE TABLE m_control_module_action_daily (
  id VARCHAR (36) PRIMARY KEY COMMENT 'uuid',
  deviceId VARCHAR (20) NOT NULL COMMENT 'uuid 设备id',
  route INT NOT NULL COMMENT '路数',
  `time` TIME NOT NULL COMMENT '定时',
  `action` INT NOT NULL COMMENT '动作 1开 0关',
  updateTime TIMESTAMP NOT NULL COMMENT '最后更新时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '控制模块定时动作表' ;

CREATE TABLE m_control_module_action_interval (
  id VARCHAR (36) PRIMARY KEY COMMENT 'uuid',
  deviceId VARCHAR (20) NOT NULL COMMENT 'uuid 设备id',
  route INT NOT NULL COMMENT '路数',
  intervalTime INT NOT NULL COMMENT '间隔时间 单位:秒',
  executionTime INT NOT NULL COMMENT '执行时间 单位:秒',
  `action` INT NOT NULL COMMENT '动作 1开 0关',
  updateTime TIMESTAMP NOT NULL COMMENT '最后更新时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '控制模块周期动作表' ;
