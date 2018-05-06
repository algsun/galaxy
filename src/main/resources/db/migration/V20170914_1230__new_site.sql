-- author bai.weixing
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '54010202', '西藏罗布林卡', '540102', 91.098927,29.657822 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '54010202');