--author xie.deng
--description 添加丢包率计算数据表和触发器

CREATE TABLE IF NOT EXISTS `m_device_link_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nodeid` VARCHAR(20) NOT NULL COMMENT '产品入网唯一标识',
  `nodeVersion` INT(11) NOT NULL COMMENT '节点协议版本号',
  `isControl` INT(11) NOT NULL COMMENT '0:可控 1:不可控',
  `parentIP` INT(11) NOT NULL COMMENT '父节点IP号',
  `childIP` INT(11) NOT NULL COMMENT '当前节点IP号',
  `feedbackIP` INT(11) NOT NULL COMMENT '反馈地址IP号',
  `sequence` INT(11) NOT NULL COMMENT '包序列号',
  `stamp` DATETIME NOT NULL COMMENT '时间戳',
  `emptyStamp` DATETIME DEFAULT NULL COMMENT '空数据时间戳',
  `interval_i` INT(11) NOT NULL DEFAULT '600' COMMENT '工作周期',
  `rssi` INT(11) NOT NULL COMMENT '接收信号强度',
  `lqi` INT(11) NOT NULL COMMENT '连接质量参数',
  `lowvoltage` FLOAT NOT NULL DEFAULT '0' COMMENT '电压：-1、无电压值，其他的、电压值',
  `anomaly` INT(11) NOT NULL DEFAULT '0' COMMENT '设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电',
  `deviceMode` INT(11) NOT NULL DEFAULT '0' COMMENT '0：正常模式 1：巡检模式',
  `remoteIp` VARCHAR(15) NOT NULL DEFAULT '192.168.0.1' COMMENT '网关设备ip',
  `remotePort` INT(11) NOT NULL DEFAULT '10000' COMMENT '网关数据监听端口',
  `sdCardState` INT(1) NOT NULL DEFAULT '0' COMMENT 'SD卡状态：0未插卡或卡未插好 1卡已插好 2卡已写满',
  `dataVersion` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;

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
	SET @preDate = (SELECT DATE_SUB(SYSDATE(),INTERVAL 3 MONTH));
	DELETE FROM m_device_link_info WHERE stamp < @preDate; 
    END;;
DELIMITER ;



