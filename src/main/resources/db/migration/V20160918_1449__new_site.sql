-- author : chenYaoFei
-- description  添加站点

INSERT INTO t_site  (siteId, siteName, areaCode)
SELECT '10000312', '常兴庄渔场', '110114' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000312') ;

INSERT INTO t_site  (siteId, siteName, areaCode)
SELECT '10000313', '京朝花园', '110105' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000313') ;

INSERT INTO t_site  (siteId, siteName, areaCode)
SELECT '10000314', '小汤山海曼水产', '110114' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000314') ;