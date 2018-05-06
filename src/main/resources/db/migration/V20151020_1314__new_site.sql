--author li.jianfei
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '53060201', '霍承嗣墓葬', '530602',103.719838,27.345921 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '53060201') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '53070202', '大觉宫寺', '530702',101.592952,24.864213 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '53070202') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '53072101', '白沙壁画', '530721',100.223962,26.961028 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '53072101') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '53082401', '普洱芒岛佛寺', '530824',100.301639,23.293672 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '53082401') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '53092701', '广允缅寺', '530927',99.257014,23.157985 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '53092701') ;

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '53342301', '迪庆寿国寺', '533423',99.713682,27.831029 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '53342301') ;