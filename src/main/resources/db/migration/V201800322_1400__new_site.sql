-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010206', '首都博物馆', '11010206', 116.348761, 39.912187 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010206');

-- author lv.dongyu
-- description更新站点

UPDATE t_site SET siteName='首都博物馆',lngBaiDu='116.348761',latBaiDu='39.912187' WHERE siteId = '11010206';

