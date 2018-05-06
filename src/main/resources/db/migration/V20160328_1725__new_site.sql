-- author li.jianfei
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '34010402', '安徽博物院', '340104', 117.226873, 31.808403 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '34010402') ;
