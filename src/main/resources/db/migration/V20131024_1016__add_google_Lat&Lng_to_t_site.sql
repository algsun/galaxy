-- author li.jianfei
-- t_site 添加谷歌地图经纬度存储列，并添加秦始皇帝陵博物院、陕西历史博物馆经纬度信息

-- 添加 lngGoogle 
ALTER TABLE t_site ADD lngGoogle DOUBLE;

-- 添加 latGoogle 
ALTER TABLE t_site ADD latGoogle DOUBLE;

-- 添加秦始皇帝陵博物院经纬度(Google Map)
UPDATE t_site SET lngGoogle = 109.26347399999997, latGoogle = 34.383492 WHERE siteName='秦始皇帝陵博物院';

-- 添加陕西历史博物馆经纬度(Google Map)
UPDATE t_site SET lngGoogle = 108.95546579999996, latGoogle = 34.2229916 WHERE siteName='陕西历史博物馆';
