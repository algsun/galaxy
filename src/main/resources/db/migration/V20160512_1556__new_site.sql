--author li.jianfei
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '22010204', '吉林省文物考古研究所', '220102',125.38431, 43.863762 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '22010204') ;