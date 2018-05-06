-- author: chen.yaofei
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode)
SELECT '64040201', '固原博物馆', '640400' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '64040201');

