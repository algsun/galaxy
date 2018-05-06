-- author sun.cong
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '12022504', '天津市蓟州区文物管理所', '120225', 117.409531, 40.051121 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '12022504');