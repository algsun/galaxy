-- author bai.weixing
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '35050303', '福建泉州市博物馆', '350503',118.592204,24.940864 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '35050303');