-- author: liuzhu
-- description: 位置点绑定文物表，位置点表添加列photo

ALTER TABLE `m_location` ADD COLUMN photo VARCHAR(200) NULL COMMENT '文物图片';

CREATE TABLE `m_location_relic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `locationId` varchar(36) NOT NULL COMMENT '位置点id',
  `relicId` int(11) NOT NULL COMMENT '文物id',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '位置点绑定文物表';
