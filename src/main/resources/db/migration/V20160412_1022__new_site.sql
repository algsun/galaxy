--author chen.yaofei
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010607', '水产养殖观赏鱼水质监测', '110106',116.380276,39.850437 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010607') ;