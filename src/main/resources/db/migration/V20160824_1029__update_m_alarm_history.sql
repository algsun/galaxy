 -- author : liuzhu
 -- description  报警相关修改

ALTER TABLE `m_alarm_history`
DROP INDEX `zoneId`,
DROP FOREIGN KEY `m_alarm_history_ibfk_1`,
CHANGE `zoneId` `locationId` VARCHAR(36) NOT NULL,
ADD COLUMN state INT(1) NOT NULL COMMENT '类型 1:高于阈值,2:低于阈值,3:正常' AFTER alarmTime;


DELETE FROM `m_history_measure`;

DELETE from `m_alarm_history`;
