-- author 王耕
-- 创建展示内容表

CREATE TABLE s_media_detail (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识,MediaDetailID',
  mediaShowId INT(11) DEFAULT NULL COMMENT '展示类别的ID',
  detailTitle VARCHAR(120) DEFAULT NULL COMMENT '主展示内容标题',
  detailContent VARCHAR(700) DEFAULT NULL COMMENT '主图片展示内容',
  detailImage VARCHAR(100) DEFAULT NULL COMMENT '主图片',
  detailSubTitle  VARCHAR(120)  DEFAULT NULL COMMENT '副展示内容标题',
  detailSubImage VARCHAR(100) DEFAULT NULL COMMENT '副图片',
  detailSubDesc  VARCHAR(500) DEFAULT NULL COMMENT '副图片展示内容',
  createOn DATETIME DEFAULT NULL COMMENT '录入时间',
  createBy VARCHAR(50) DEFAULT NULL COMMENT '录入人',
  line INT(11) NOT NULL COMMENT '排序号',
  `enable` INT(11) NOT NULL COMMENT '启用停用.1:启用,2：禁用',
  pubDate DATETIME DEFAULT NULL COMMENT '发表时间',
  material VARCHAR(50) DEFAULT NULL COMMENT '材质',
  PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='展示内容表';
