-- author xu.baoji
-- 创建 主题表

CREATE TABLE `t_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `logicgroupId` int(11) DEFAULT NULL COMMENT '站点',
  `template` varchar(50) NOT NULL DEFAULT 'template_1' COMMENT '首页模版',
  `titleImage` varchar(100) DEFAULT NULL COMMENT '标题图片路径',
  `bgImage` varchar(100) DEFAULT NULL COMMENT '背景图片路径',
  `usetitle` int(10) NOT NULL DEFAULT '1' COMMENT '当前使用的背景',
  `usebg` int(10) DEFAULT '1' COMMENT '当前使用的背景',
  PRIMARY KEY (`id`),
  KEY `t_theme_logicgroup` (`logicgroupId`),
  CONSTRAINT `t_theme_logicgroup` FOREIGN KEY (`logicgroupId`) REFERENCES `t_logicgroup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
