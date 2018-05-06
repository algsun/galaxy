-- author: gaohui
-- description: t_linkcheck 触发器中去掉 delete 语句，解决 m_device_link_info 表死锁问题

DELIMITER ;;
DROP TRIGGER IF EXISTS `tg_linkcheck`;;
CREATE
    TRIGGER `tg_linkcheck` AFTER UPDATE ON `m_nodeinfomemory`
    FOR EACH ROW BEGIN
	IF NEW.stamp <> OLD.stamp THEN
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
	END IF;
END;;
DELIMITER ;
