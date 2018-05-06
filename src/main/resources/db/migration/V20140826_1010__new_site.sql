--author xiedeng
--description 添加新站点
DELETE FROM t_site WHERE siteId = '65232302';

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
VALUES ("65232302", "新疆康家石门子", "652323",86.352361,43.839638);
