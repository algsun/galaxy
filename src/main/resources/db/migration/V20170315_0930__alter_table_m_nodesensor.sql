-- author:li.jianfei
-- description: 删除设备级联删除实时数据

ALTER TABLE `m_nodesensor` DROP FOREIGN KEY `FK_nodeinfo_nodesensor`;
ALTER TABLE `m_nodesensor` ADD CONSTRAINT `FK_nodeinfo_nodesensor` FOREIGN KEY (`nodeid`) REFERENCES `m_nodeinfo`(`nodeid`) ON DELETE CASCADE;
ALTER TABLE `m_location_sensor` DROP FOREIGN KEY `FK_location_locationsensor`;
ALTER TABLE `m_location_sensor` ADD CONSTRAINT `FK_location_locationsensor` FOREIGN KEY (`locationId`) REFERENCES `m_location`(`id`) ON DELETE CASCADE;