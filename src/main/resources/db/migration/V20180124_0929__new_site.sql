-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '15292101', '阿拉善博物馆', '152921', 105.731932, 38.854518 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '15292101');

-- author lv.dongyu
-- description更新站点

UPDATE t_site SET siteName='阿拉善博物馆',lngBaiDu='105.731932',latBaiDu='38.854518' WHERE siteId = '15292101';

