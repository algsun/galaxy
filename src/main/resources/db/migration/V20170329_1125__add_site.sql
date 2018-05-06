-- author chen.yaofei
-- description 添加站点

-- 宝鸡陈仓博物馆
INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61030401', ' 宝鸡陈仓博物馆', '610304',107.384895,34.359824 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61030401');

-- 安康博物馆
INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61090202', ' 安康博物馆', '610902',109.013195,32.692556 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61090202');

-- 龙井朝鲜族民俗博物馆
INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '22240501', '龙井朝鲜族民俗博物馆', '222405',129.429126, 42.776888 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '22240501');

-- 公输堂
INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61012501', '公输堂', '610125',108.593399, 34.208156 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61012501');