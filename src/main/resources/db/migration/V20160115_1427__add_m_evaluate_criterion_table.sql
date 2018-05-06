--author liu.zhu
--description 添加qcm评估标准

CREATE TABLE `m_evaluate_criterion` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `airQuality` varchar(100) NOT NULL COMMENT '空气质量',
  `opLevel` varchar(20) NOT NULL COMMENT '有机污染物等级',
  `opRange` float NOT NULL COMMENT '有机污染物评判标准',
  `ipLevel` varchar(20) NOT NULL COMMENT '无机污染物等级',
  `ipRange` float NOT NULL COMMENT '无机污染物评判标准',
  `spLevel` varchar(20) NOT NULL COMMENT '含硫污染物等级',
  `spRange` float NOT NULL COMMENT '含硫污染物评判标准',
  `color` varchar(50) NOT NULL COMMENT '颜色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'QCM分级评估准则';

insert into `m_evaluate_criterion` (`id`, `airQuality`, `opLevel`, `opRange`, `ipLevel`, `ipRange`, `spLevel`, `spRange`, `color`) values('1','非常洁净','P1','2','C1','20','S1','10','#63be7b');
insert into `m_evaluate_criterion` (`id`, `airQuality`, `opLevel`, `opRange`, `ipLevel`, `ipRange`, `spLevel`, `spRange`, `color`) values('2','洁净','P2','5','C2','55','S2','25','#b2d581');
insert into `m_evaluate_criterion` (`id`, `airQuality`, `opLevel`, `opRange`, `ipLevel`, `ipRange`, `spLevel`, `spRange`, `color`) values('3','清洁','P3','25','C3','105','S3','65','#feeb84');
insert into `m_evaluate_criterion` (`id`, `airQuality`, `opLevel`, `opRange`, `ipLevel`, `ipRange`, `spLevel`, `spRange`, `color`) values('4','轻度污染','P4','45','C4','175','S4','125','#fba977');
insert into `m_evaluate_criterion` (`id`, `airQuality`, `opLevel`, `opRange`, `ipLevel`, `ipRange`, `spLevel`, `spRange`, `color`) values('5','污染','P5','45','C5','175','S5','125','#f8696b');

