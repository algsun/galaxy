--王耕
--修复dc_program的locationId字段，其中有些是错误数据被存成了nodeid
UPDATE `dc_program` dc SET locationId = (SELECT id FROM m_location ml WHERE ml.nodeid = dc.locationId)