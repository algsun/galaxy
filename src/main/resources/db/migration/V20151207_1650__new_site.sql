--author xie.deng
--description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '34011102', '安徽合肥渡江战役纪念馆', '340111',117.336449,31.718041 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '34011102') ;