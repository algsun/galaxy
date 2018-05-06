--author li.jianfei
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61011304', '大雁塔', '610113', 108.970585, 34.224594 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61011304') ;