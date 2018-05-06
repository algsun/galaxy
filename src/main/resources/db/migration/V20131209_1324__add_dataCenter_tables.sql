--author wang.geng
--description 添加数据中心相关的三张表：布局表，布局控件表，控件数据显示条件表
--dc_layout 布局表
DROP TABLE IF EXISTS `dc_layout`;
CREATE TABLE `dc_layout` (
  `layoutId` varchar(50) NOT NULL COMMENT '主键，布局UUID',
  `description` varchar(1000) NOT NULL COMMENT '描述',
  PRIMARY KEY (`layoutId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--dc_items 布局控件表
DROP TABLE IF EXISTS `dc_items`;
CREATE TABLE `dc_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，序号',
  `related_layoutId` varchar(50) NOT NULL COMMENT '外键，关联到布局表的layoutId',
  `item_id` varchar(20) NOT NULL COMMENT '控件选择器ID',
  `data_col` int(11) NOT NULL COMMENT '列坐标',
  `data_row` int(11) NOT NULL COMMENT '行坐标',
  `data_sizex` int(11) NOT NULL COMMENT '宽度',
  `data_sizey` int(11) NOT NULL COMMENT '高度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--dc_chart_condition 控件数据显示条件表
DROP TABLE IF EXISTS `dc_chart_condition`;
CREATE TABLE `dc_chart_condition` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，序号',
  `related_item_id` varchar(20) NOT NULL COMMENT '外键，关联到控制表的选择器ID',
  `chart_type` int(2) NOT NULL COMMENT '图表类型:1.实时图形;2.基础曲线图;3.风向玫瑰图;4.降雨量图;5.累积光照图',
  `deviceId` varchar(22) NOT NULL COMMENT '关联的设备ID',
  `startTime` datetime DEFAULT NULL COMMENT '基础曲线图的条件:起始时间',
  `endTime` datetime DEFAULT NULL COMMENT '基础曲线图的条件:结束时间',
  `sensorPhysicalid` varchar(100) NOT NULL COMMENT '监测指标ID，可以有多个，以逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


