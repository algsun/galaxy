-- author xu.baoji
-- 离线数据文件 表 添加文件 上传日期

ALTER TABLE m_offline_file  ADD COLUMN  uploadDate date COMMENT '文档上传日期';
