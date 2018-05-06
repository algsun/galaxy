
ALTER TABLE `m_nodeinfomemory` ADD isThresholdAlarm INT(1) NOT NULL DEFAULT '0' COMMENT '报警状态';

ALTER TABLE `m_threshold_sensor` ADD nodeId VARCHAR(20) NULL COMMENT '设备id';

ALTER TABLE `m_threshold_sensor` DROP FOREIGN KEY FK_Reference_81;

ALTER TABLE `m_threshold_sensor` MODIFY zoneId VARCHAR(50) NULL COMMENT '区域id';

ALTER TABLE `m_threshold_sensor`  
  ADD CONSTRAINT `location_ibfk_2` FOREIGN KEY (`nodeId`) REFERENCES `m_nodeinfo` (`nodeid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `m_threshold_sensor` ADD UNIQUE INDEX `Uk_NODEID_SENSORPHYSICALID` (`sensorPhysicalid`,`nodeId`)
