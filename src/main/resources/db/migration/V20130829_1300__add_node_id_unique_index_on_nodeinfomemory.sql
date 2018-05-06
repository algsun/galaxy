-- author: gaohui
-- description: m_nodeinfomemory 表中添加 nodeid 的唯一索引

ALTER TABLE m_nodeinfomemory
  ADD UNIQUE index_unique_nodeid (nodeid);
