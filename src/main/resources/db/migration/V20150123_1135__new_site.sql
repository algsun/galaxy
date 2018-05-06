--author xiedeng
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61011302', '陕西省考古研究院', '610113',108.972441,34.231751 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61011302') ;
