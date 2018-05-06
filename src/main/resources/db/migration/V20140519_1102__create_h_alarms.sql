-- author lijianfei
-- 创建外展报警记录表

CREATE TABLE `h_alarms` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `exhibitionId` int(11) NOT NULL COMMENT '外展ID',
  `content` varchar(200) NOT NULL COMMENT '报警内容',
  `contact` int(11) NOT NULL COMMENT '联系人',
  `sendTime` datetime NOT NULL COMMENT '短信发送时间',
  PRIMARY KEY (`id`),
  KEY `exhibitionId` (`exhibitionId`),
  KEY `contact` (`contact`),
  CONSTRAINT `h_alarms_ibfk_1` FOREIGN KEY (`exhibitionId`) REFERENCES `h_exhibition` (`id`),
  CONSTRAINT `h_alarms_ibfk_2` FOREIGN KEY (`contact`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '外展报警记录表';
