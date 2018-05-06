--author wang.geng
--数据中心一些表的修改，添加字段

-- dc_chart_condition表添加一个字段
ALTER TABLE `dc_chart_condition` ADD related_layout_id varchar(50) NOT NULL COMMENT '关联的布局ID';