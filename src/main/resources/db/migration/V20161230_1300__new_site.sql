-- author li.jianfei
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '43072303', '城头山古文化遗址', '430723',111.667446, 29.693896 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '43072303');