-- author: gaohui
-- description: m_sensorinfo 主键添加 auto_increment, sensorPhysicalId 添加唯一索引

-- ALTER TABLE `m_sensorinfo` MODIFY id INT(11) AUTO_INCREMENT;

ALTER TABLE `m_sensorinfo` ADD UNIQUE INDEX(sensorPhysicalId);

ALTER TABLE `m_sensorinfo` MODIFY COLUMN showType INT NOT NULL COMMENT '0 默认; 1 风向类；该字段用于呈现判断，风向类在实时数据、历史数据中需要展示为方向标识，而在图表中需要具体数值，考虑扩展性，加入此标识; 2 GPS 类;';
