--author wang.geng
--添加信息发布配置表dc_config
--添加节目表dc_program
DROP TABLE IF EXISTS `dc_config`;
CREATE TABLE `dc_config` (
  `related_layoutId` VARCHAR(50) NOT NULL COMMENT '主键，关联到布局表的layoutId',
  `url` VARCHAR(50) NOT NULL COMMENT '背景图片路径',
  PRIMARY KEY (`related_layoutId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '信息发布配置表';

DROP TABLE IF EXISTS `dc_program`;
CREATE TABLE `dc_program` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，序号',
  `related_layoutId` VARCHAR(50) NOT NULL COMMENT '外键，关联到布局表的layoutId',
  `related_item_id` varchar(50) NOT NULL COMMENT '外键，关联到控件表的选择器ID',
  `title` varchar(50) NOT NULL COMMENT '文字描述标题',
  `url` varchar(200) NOT NULL COMMENT '图片路径',
  `refresh` INT(11) DEFAULT 10 NOT NULL COMMENT '播放时长，默认10秒',
  `deviceId` varchar(50) NOT NULL COMMENT '关联的设备',
  `detail` varchar(2000) NOT NULL COMMENT '文字描述',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '信息发布节目表';