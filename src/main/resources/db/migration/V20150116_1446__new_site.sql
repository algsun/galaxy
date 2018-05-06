--author xiedeng
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '43012203', '长沙铜官窑遗址', '430122',112.840208,28.42495 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '43012203') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '43112601', '湖南舜帝庙遗址', '431126',111.999725,25.33893 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '43112601') ;
