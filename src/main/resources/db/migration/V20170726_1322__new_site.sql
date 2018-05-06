-- author sun.cong
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '10000205', '元智机房', '100002', 108.866424, 34.203324 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000205');