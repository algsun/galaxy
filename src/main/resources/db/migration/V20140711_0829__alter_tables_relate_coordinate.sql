-- author 李建飞
-- 删除冗余列

ALTER TABLE t_zone DROP coordinateX;
ALTER TABLE t_zone DROP coordinateY;

ALTER TABLE m_location DROP coordinateX;
ALTER TABLE m_location DROP coordinateY;

ALTER TABLE m_nodeinfo DROP coordinateX;
ALTER TABLE m_nodeinfo DROP coordinateY;

ALTER TABLE p_dv_place DROP coordinateX;
ALTER TABLE p_dv_place DROP coordinateY;