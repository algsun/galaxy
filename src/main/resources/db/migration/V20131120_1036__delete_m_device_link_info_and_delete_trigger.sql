-- author: liuzhu
-- description:删除 表: m_device_link_info;触发器:tg_insertLinkcheck、tg_linkcheck

DROP TABLE IF EXISTS `m_device_link_info`;

DROP TRIGGER IF EXISTS `tg_insertLinkcheck`;
DROP TRIGGER IF EXISTS `tg_linkcheck`;