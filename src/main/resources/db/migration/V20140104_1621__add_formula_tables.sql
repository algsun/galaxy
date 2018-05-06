-- author gaohui
-- description 添加环境公式相关表

CREATE TABLE `m_formula` (
  id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` VARCHAR (20) NOT NULL COMMENT '名称',
  paramCount INT (11) NOT NULL DEFAULT 0 COMMENT '公式系数个数',
  description VARCHAR (100) NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='公式表' ;


CREATE TABLE `m_formula_sensor` (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  device_id VARCHAR(13) NOT NULL DEFAULT '0' COMMENT '设备ID。 如果为 0 则为监测指标默认参数；如果不为 0 则为设备自定义参数',
  sensor_id INT(11) NOT NULL COMMENT '监测指标标识',
  formula_id INT(11) NOT NULL COMMENT '公式ID',
  min_x INT(11) NOT NULL DEFAULT 0 COMMENT 'x 最小取值范围',
  max_x INT(11) NOT NULL DEFAULT 0 COMMENT 'x 最大取值范围',
  x_range_type INT(1) NOT NULL DEFAULT 0 COMMENT '无范围限制 0; 只有最小值限制 1; 只有最大值限制 2; 两个都有 3;',
  min_y INT(11) NOT NULL DEFAULT 0 COMMENT 'y 最小取值范围',
  max_y INT(11) NOT NULL DEFAULT 0 COMMENT 'y 最大取值范围',
  y_range_type INT(1) NOT NULL DEFAULT 0 COMMENT '无范围限制 0; 只有最小值限制 1; 只有最大值限制 2; 两个都有 3;',
  sign_type INT(11) NOT NULL DEFAULT '0' COMMENT '原始值是否有符号。无符号 0; 有符号 1;',
  PRIMARY KEY(id),
  FOREIGN KEY(sensor_id) REFERENCES `m_sensorinfo`(sensorPhysicalid),
  FOREIGN KEY(formula_id) REFERENCES `m_formula`(id),
  UNIQUE INDEX (device_id, sensor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='监测指标公式表' ;

CREATE TABLE `m_formula_param` (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  device_id VARCHAR(13) NOT NULL DEFAULT '0' COMMENT '设备ID。 如果为 0 则为监测指标默认参数；如果不为 0 则为设备自定义参数',
  sensor_id INT(11) NOT NULL COMMENT '监测指标标识',
  `name` VARCHAR(2) NOT NULL COMMENT '参数名称：例如 a, b, c',
  `value` VARCHAR(20) NOT NULL COMMENT '参数值',
  PRIMARY KEY(id),
  FOREIGN KEY(sensor_id) REFERENCES `m_sensorinfo`(sensorPhysicalid),
  UNIQUE INDEX(`device_id`, `sensor_id`, `name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='公式系数表' ;


/* 公式 */
INSERT INTO m_formula (`id`, `name`, `paramCount`, `description`) VALUES
	(1, '一元一次', 2, 'aX + b'),
	(2, '一元二次', 3, 'aX2 + bX + c'),
	(3, '一元三次', 4, 'aX3 + bX2 + cX + d'),
	(4, '角度规整', 2, 'degree(aX + b)'),
	(5, '一元一次分段', 5, 'if(X <= e){ aX + b } else { cX + d }');
