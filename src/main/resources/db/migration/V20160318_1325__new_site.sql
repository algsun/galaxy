-- author li.jianfei
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '43072302', '余家牌坊', '430723', 111.709635, 29.714232 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '43072302') ;
