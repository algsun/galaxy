-- author xu.yuexi
-- 创建平面图坐标表

CREATE TABLE m_coordinate(
id VARCHAR(36) PRIMARY KEY COMMENT 'uuid',
parentZoneId VARCHAR(50) NOT NULL  COMMENT 'uuid',
zoneId VARCHAR(50) COMMENT '区域节点id，可为空',
nodeId VARCHAR(20) COMMENT '节点id，可为空',
dvPlaceId VARCHAR(22) COMMENT '摄像机节点id，可为空',
X FLOAT NOT NULL COMMENT 'x坐标',
Y FLOAT NOT NULL COMMENT 'y坐标'
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '平面图坐标表';