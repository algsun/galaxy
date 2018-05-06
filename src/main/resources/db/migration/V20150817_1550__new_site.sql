--author xiedeng
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010511', '北京奥运博物馆', '110105',116.40315,39.997776 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010511') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010224', '白塔寺博物馆', '110102',116.370044,39.929866 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010224') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010120', '孔庙和国子监博物馆', '110101',116.419645,39.951932 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010120') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010121', '正阳门', '110101',116.40423,39.907046 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010121') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010816', '大觉寺', '110108',116.113284,40.058445 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010816') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '11010817', '团城演武厅', '110108',116.214523,39.990154 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '11010817') ;