-- author sun.cong
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '31011507', '上海历史博物馆', '310115', 121.481584, 31.234816 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '31011507');