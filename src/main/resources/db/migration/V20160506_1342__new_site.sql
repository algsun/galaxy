--author liuzhu
--description 添加站点
INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '10000301', '北京水产研究所实验室监测系统', '100003',116.385144,39.843522 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000301') ;