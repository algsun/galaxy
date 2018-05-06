-- author gaohui
-- description 将 t_sitemap 表合并到 t_siteinfo

-- t_siteinfo 添加 targetHost, isActive 列
ALTER TABLE `t_siteinfo` ADD COLUMN targetHost VARCHAR(20) NOT NULL DEFAULT '' COMMENT '目标主机地址(IP或域名)';

ALTER TABLE `t_siteinfo` ADD COLUMN isActive INT(1) NOT NULL DEFAULT 1 COMMENT '网络状态-标识其是否加入网络:0未加入、1加入';

UPDATE `t_siteinfo` i JOIN `t_sitemap` m ON  i.siteId = m.siteId SET i.`isActive` = m.`isActive`, i.`targetHost` = m.`targetHost`;

DROP TABLE `t_sitemap`;