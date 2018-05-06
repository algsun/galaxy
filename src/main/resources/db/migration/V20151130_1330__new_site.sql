--author xie.deng
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61012602', '高陵示范项目', '610126',109.012731,34.449179 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61012602') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '10000311', '西安理工大学壁画', '100003',109.003474,34.227185 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '10000311') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '14020201', '云冈石窟', '140202',113.139459,40.117185 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '14020201') ;