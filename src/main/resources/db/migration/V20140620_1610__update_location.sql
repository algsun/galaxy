 -- author xiedeng
-- 添加字段在表 m_location上
ALTER TABLE `m_location` ADD COLUMN siteId VARCHAR(50) NOT NULL COMMENT '站点编号' AFTER zoneId;

ALTER TABLE `m_location` MODIFY COLUMN zoneId VARCHAR(50) DEFAULT NULL COMMENT '区域编号';

ALTER TABLE `m_location`
  ADD CONSTRAINT `FK_m_nodeinfo_nodeid` FOREIGN KEY (`nodeId`) REFERENCES `m_nodeinfo` (`nodeid`);

ALTER TABLE `m_location`
  ADD CONSTRAINT `FK_t_zone_zoneId` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`);