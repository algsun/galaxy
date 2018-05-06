--author liuzhu
--description 添加新站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '65210101', '新疆吐鲁番交河故城', '652101',89.077395,42.954298 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '65210101') ;

