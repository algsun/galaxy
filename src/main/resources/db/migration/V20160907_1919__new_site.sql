-- author xie.deng
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '65292602', '克孜尔千佛洞', '652926',82.514622,41.794642 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '65292602');