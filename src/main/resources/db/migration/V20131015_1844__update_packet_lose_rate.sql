--author xie.deng
--description 添加丢包率计算数据表和触发器

DELIMITER ;;
DROP TRIGGER IF EXISTS tg_insertLinkcheck;;
CREATE
    TRIGGER `tg_insertLinkcheck` AFTER INSERT ON `m_nodeinfomemory` 
    FOR EACH ROW BEGIN
	    INSERT INTO m_device_link_info
            (`nodeid`,
             `nodeVersion`,
             `isControl`,
             `parentIP`,
             `childIP`,
             `feedbackIP`,
             `sequence`,
             `stamp`,
             `emptyStamp`,
             `interval_i`,
             `rssi`,
             `lqi`,
             `lowvoltage`,
             `anomaly`,
             `deviceMode`,
             `remoteIp`,
             `remotePort`,
             `sdCardState`,
             `dataVersion`)
              VALUES
             (
              NEW.nodeid, 
              NEW.nodeVersion,
              NEW.isControl, 
              NEW.parentIP, 
              NEW.childIP, 
              NEW.feedbackIP, 
              NEW.sequence, 
              NEW.stamp,
              NEW.emptyStamp,
              NEW.interval_i,
              NEW.rssi,
              NEW.lqi,
              NEW.lowvoltage,
              NEW.anomaly,
              NEW.deviceMode,
              NEW.remoteIp,
              NEW.remotePort,
              NEW.sdCardState,
              NEW.dataVersion);	
    END;;
DELIMITER ;



