-- author 李建飞
-- 节点ID 改为 位置点ID

ALTER TABLE m_coordinate CHANGE  nodeId  locationId VARCHAR(20);