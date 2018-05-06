--author li.jianfei
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '10000105', '乌兹别克斯坦2015考古发掘', '100001',99.713682,27.831029 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000105') ;