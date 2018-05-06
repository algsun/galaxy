-- author 李建飞
-- description 添加高德地图经纬度坐标存储列
-- date 2015-7-1

ALTER TABLE t_site ADD `lngAmap` DOUBLE DEFAULT NULL COMMENT '高德地图：经度';
ALTER TABLE t_site ADD `latAmap` DOUBLE DEFAULT NULL COMMENT '高德地图：纬度';

