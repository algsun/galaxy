--author xu.baoji
--description修改表 u_device 添加 siteId 站点 id  字段
ALTER TABLE u_device  ADD `siteId` VARCHAR(50) NOT NULL  COMMENT '站点id'