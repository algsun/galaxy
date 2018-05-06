--author wang.geng
--description 添加报警辅助计算表
DROP TABLE IF EXISTS `h_path_points`;
CREATE TABLE `h_path_points` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `exhibitionId` INT(11) NOT NULL COMMENT '外展ID',
  `longitude` DOUBLE NOT NULL COMMENT '经度',
  `latitude` DOUBLE NOT NULL COMMENT '纬度',
  PRIMARY KEY (`id`),
  KEY `exhibitionId` (`exhibitionId`),
  CONSTRAINT `h_path_points_ibfk_1` FOREIGN KEY (`exhibitionId`) REFERENCES `h_exhibition` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
