--author li.jianfei
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010818', '圆明园遗址', '110108', 119.643874, 30.019958 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010818') ;
