--author li.jianfei
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode)
SELECT '10000106', '"Rahat"古城遗址（拉哈特塞种人遗址）', '100001' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000106') ;