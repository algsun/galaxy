--xie.deng
--添加m_upload_files表

CREATE TABLE `m_upload_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `filename` varchar(50) NOT NULL COMMENT '文件名称',
  `uploadTime` datetime NOT NULL COMMENT '上传的时间',
  `analysisSign` int(11) NOT NULL DEFAULT '0' COMMENT '0,未上传。 1 ，已经上传',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
