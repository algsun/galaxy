--author wang.geng
--修改 dc_layout 的表结构，增加一列，排序时间
ALTER TABLE `dc_layout`
ADD COLUMN create_time DATETIME NOT NULL COMMENT '记录创建时间' AFTER `description`;