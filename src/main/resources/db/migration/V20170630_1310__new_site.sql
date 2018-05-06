-- author li.jianfei
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61010206', '西安市民俗博物馆', '610102', 108.977097, 34.26692 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61010206');