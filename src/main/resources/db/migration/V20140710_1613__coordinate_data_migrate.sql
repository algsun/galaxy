-- author 李建飞
-- 区域/摄像机坐标信息迁移

-- 迁移区域坐标信息
INSERT INTO m_coordinate (id, parentZoneId, zoneId, X, Y) 
SELECT 
  UUID() AS id,
  parentId AS parentZoneId,
  zoneId,
  coordinateX AS X,
  coordinateY AS Y
FROM
  t_zone 
WHERE parentId IS NOT NULL 
  AND coordinateX <> - 1 
  AND coordinateY <> - 1 
  AND zoneId NOT IN 
  (SELECT 
    mc.zoneId 
  FROM
    t_zone tz,
    m_coordinate mc 
  WHERE mc.parentZoneId = tz.parentId 
    AND mc.zoneId = tz.zoneId) ;

    
-- 迁移摄像机坐标信息
INSERT INTO m_coordinate (id, parentZoneId, dvPlaceId, X, Y) 
SELECT 
  UUID() AS id,
  zoneId AS parentZoneId,
  id AS dvPlaceId,
  coordinateX AS X,
  coordinateY AS Y
FROM
  p_dv_place 
WHERE coordinateX <> - 1 
  AND coordinateY <> - 1 
  AND id NOT IN 
  (SELECT 
    mc.dvPlaceId 
  FROM
    p_dv_place dp,
    m_coordinate mc 
  WHERE mc.parentZoneId = dp.zoneId 
    AND mc.dvPlaceId = dp.id) ;

-- 迁移节点坐标信息数据
INSERT INTO m_coordinate (id, parentZoneId, locationId, X, Y)
SELECT
  UUID() AS id,
  roomId AS parentZoneId,
  nodeid AS locationId,
  coordinateX AS X,
  coordinateY AS Y
FROM
  m_nodeinfo
WHERE coordinateX <> - 1
  AND coordinateY <> - 1
  AND nodeid NOT IN
  (SELECT
    mc.locationId
  FROM
    m_nodeinfo n,
    m_coordinate mc
  WHERE mc.parentZoneId = n.roomId
    AND mc.locationId = n.nodeid );

