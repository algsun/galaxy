-- author: gaohui
-- description: m_nodesensor 表中添加 (nodeid, sensorPhysicaId) 的唯一索引

ALTER TABLE `m_nodesensor` ADD UNIQUE INDEX(nodeid, sensorPhysicalId);