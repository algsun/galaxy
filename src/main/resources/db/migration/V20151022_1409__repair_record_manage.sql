--王耕
--文物修复图片管理
CREATE TABLE `o_repair_photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `repairRecordId` int(22) NOT NULL COMMENT '关联的修复记录',
  `description` varchar(100) DEFAULT NULL COMMENT '图片描述',
  `path` varchar(100) NOT NULL COMMENT '图片路径',
  `type` int(2) NOT NULL COMMENT '类型，1:现状图片;2:修复记录图片',
  `stamp` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
