--author wang.geng
--数据中心一些表的修改，添加字段

-- dc_chart_condition表添加三个字段
ALTER TABLE `dc_chart_condition` ADD dateNum int(11) DEFAULT NULL COMMENT '时间参数，近多少天';
ALTER TABLE `dc_chart_condition` ADD param1 varchar(22) DEFAULT NULL COMMENT '预留配置参数1';
ALTER TABLE `dc_chart_condition` ADD param2 int(11) DEFAULT NULL COMMENT '预留配置参数2';