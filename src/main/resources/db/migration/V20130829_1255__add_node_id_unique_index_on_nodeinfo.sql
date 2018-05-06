-- author: gaohui
-- description: m_nodeinfo 表中添加 nodeid 的唯一索引

ALTER TABLE m_nodeinfo
  ADD UNIQUE index_unique_nodeid (nodeid);
