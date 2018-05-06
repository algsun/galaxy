-- author wang.geng
-- dc_items表新增一列，表明控件是图表或者幻灯片

ALTER TABLE dc_items  ADD COLUMN  itemType int(11) NOT NULL COMMENT '0:图表；1:幻灯片';