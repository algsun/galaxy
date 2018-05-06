-- author gaohui
-- description  m_netinfo 将 id 改为 int(11) auto_increment, 并且 lport 添加唯一索引

ALTER TABLE `m_netinfo` MODIFY id INT(11) AUTO_INCREMENT;

ALTER TABLE `m_netinfo` ADD UNIQUE INDEX(lport);
