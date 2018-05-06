--author liu.zhu
--description 更换探头记录表

CREATE TABLE `m_replace_sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `locationId` varchar(20) NOT NULL COMMENT '位置点id',
  `replaceDate` datetime NOT NULL COMMENT '更换时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT '更换探头记录表'

