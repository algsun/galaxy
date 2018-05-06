-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '12010302', '天津自然博物馆', '120103', 117.215291, 39.091766 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '12010302');

-- author lv.dongyu
-- description更新站点

UPDATE t_site SET siteName='天津自然博物馆',lngBaiDu='117.215291',latBaiDu='39.091766' WHERE siteId = '12010302';

