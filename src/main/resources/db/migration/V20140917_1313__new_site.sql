--author xiedeng
--description 添加新站点
DELETE FROM t_site WHERE siteId = '33020602';

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
VALUES ("33020602", "中国港口博物馆", "330206",121.914924,29.763481);
