-- author li.jianfei
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '22021104', '吉林市博物馆', '220211', 126.568801, 43.834526 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '22021104');