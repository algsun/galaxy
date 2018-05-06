-- author sun.cong
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '22018301', '德惠市博物馆', '220183', 126.443308, 41.959802 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '22018301');