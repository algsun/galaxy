-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '37100202', '威海市博物馆', '371002', 122.146304, 37.473696 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '37100202');

-- author lv.dongyu
-- description更新站点

UPDATE t_site SET siteName='威海市博物馆',lngBaiDu='122.146304',latBaiDu='37.473696' WHERE siteId = '37100202';

