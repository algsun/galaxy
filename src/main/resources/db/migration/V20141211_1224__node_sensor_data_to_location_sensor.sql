--m_nodesensor 数据迁移到 m_location_sensor
--李建飞

INSERT INTO m_location_sensor (
  id,
  locationId,
  sensorPhysicalId,
  sensorPhysicalValue,
  state,
  stamp,
  dataVersion
)
SELECT
  UUID() id,
  ml.id locationId,
  mn.sensorPhysicalId,
  mn.sensorPhysicalValue,
  mn.state,
  mn.stamp,
  mn.dataVersion
FROM
  m_nodesensor mn,
  m_location ml
WHERE mn.nodeid = ml.nodeid
  AND NOT EXISTS
  (SELECT
    1
  FROM
    m_location_sensor t
  WHERE t.locationId = ml.id) ;


