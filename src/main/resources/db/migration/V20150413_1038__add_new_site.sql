--author 王耕
--description 添加新站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61011303', '陕西省文物保护研究院', '610113',108.881209,34.234549 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61011303') ;