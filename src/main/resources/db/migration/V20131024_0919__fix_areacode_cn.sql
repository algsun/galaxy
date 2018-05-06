-- author gaohui
-- description 修正 t_areacode_cn 中的 重庆相关数据

UPDATE `t_areacode_cn` SET parentCode = NULL WHERE areaCode = '500000';

UPDATE `t_areacode_cn` SET isFilte = 1 WHERE areaCode = '500300';