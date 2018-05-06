-- author li.jianfei
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '44060404', '祖庙博物馆', '440604',113.119563,23.034764 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '44060404');