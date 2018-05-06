--author liuzhu
--修改dc_layout中siteId为logicGroupId

ALTER TABLE `dc_layout` CHANGE siteId logicGroupId INT NOT NULL COMMENT '站点组id'