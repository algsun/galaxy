-- author: liuzhu
-- description: 阈值相关修改

CREATE TABLE `m_location_follower` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `locationId` VARCHAR(36) NOT NULL COMMENT '位置点id',
  `userId` VARCHAR(36) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='位置点收藏表';


ALTER TABLE `m_threshold_sensor`
DROP INDEX `FK_Reference_78`,
DROP INDEX `location_ibfk_2`,
DROP INDEX `Uk_NODEID_SENSORPHYSICALID`,
DROP FOREIGN KEY `FK_Reference_78`,
DROP FOREIGN KEY `location_ibfk_2`,
DROP COLUMN `thresholdOptionId`,
CHANGE `zoneId` `locationId` VARCHAR(50) NOT NULL,
CHANGE `sensorPhysicalid` `sensorPhysicalId` INT(11) NOT NULL,
ADD COLUMN thresholdType INT(1) NOT NULL COMMENT '类型0：位置点，1：区域，2：质地' AFTER floating;


CREATE TABLE `m_texture_threshold` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `textureId` INT(11) NOT NULL COMMENT '质地id',
  `sensorPhysicalId` INT(11) NOT NULL COMMENT '监测指标id',
  `conditionType` INT(11) NOT NULL COMMENT '条件',
  `target` FLOAT NOT NULL COMMENT '目标值',
  `floating` FLOAT DEFAULT NULL COMMENT '浮动值',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '质地阈值表';



INSERT INTO `m_texture_threshold` (
  `textureId`,
  `sensorPhysicalId`,
  `conditionType`,
  `target`,
  `floating`
)
VALUES
  (11,33,1,20,4), (11,32,1,50,20), (11,41,5,300,NULL),(11,42,3,20,NULL),(11,85,3,4,NULL),(11,2049,3,5,NULL),(11,2048,3,5,NULL),(11,103,3,100,NULL),(11,104,3,250,NULL),(11,105,3,100,NULL),(11,91,3,80,NULL),(11,83,3,300,NULL),(11,2068,3,75,NULL),
  (1,33,1,20,4), (1,32,1,50,20), (1,41,5,300,NULL),(1,42,3,20,NULL),(1,85,3,4,NULL),(1,2049,3,5,NULL),(1,2048,3,5,NULL),(1,103,3,100,NULL),(1,104,3,250,NULL),(1,105,3,100,NULL),(1,91,3,80,NULL),(1,83,3,300,NULL),(1,2068,3,75,NULL),
  (4,33,1,20,4), (4,32,3,45,NULL), (4,41,5,300,NULL),(4,42,3,20,NULL),(4,85,3,4,NULL),(4,2049,3,5,NULL),(4,2048,3,5,NULL),(4,103,3,100,NULL),(4,104,3,250,NULL),(4,105,3,100,NULL),(4,91,3,80,NULL),(4,83,3,300,NULL),(4,2068,3,75,NULL),
  (2,33,1,20,4), (2,32,3,45,NULL), (2,41,5,300,NULL),(2,42,3,20,NULL),(2,85,3,4,NULL),(2,2049,3,5,NULL),(2,2048,3,5,NULL),(2,103,3,100,NULL),(2,104,3,250,NULL),(2,105,3,100,NULL),(2,91,3,80,NULL),(2,83,3,300,NULL),(2,2068,3,75,NULL),
  (56,33,1,20,4), (56,32,1,50,20), (56,41,5,50,NULL),(56,42,3,20,NULL),(56,85,3,4,NULL),(56,2049,3,5,NULL),(56,2048,3,5,NULL),(56,103,3,100,NULL),(56,104,3,250,NULL),(56,105,3,100,NULL),(56,91,3,80,NULL),(56,83,3,300,NULL),(56,2068,3,75,NULL),
  (51,33,1,20,4), (51,32,1,50,20), (51,41,5,50,NULL),(51,42,3,20,NULL),(51,85,3,4,NULL),(51,2049,3,5,NULL),(51,2048,3,5,NULL),(51,103,3,100,NULL),(51,104,3,250,NULL),(51,105,3,100,NULL),(51,91,3,80,NULL),(51,83,3,300,NULL),(51,2068,3,75,NULL),
  (52,33,1,20,4), (52,32,1,50,20), (52,41,5,50,NULL),(52,42,3,20,NULL),(52,85,3,4,NULL),(52,2049,3,5,NULL),(52,2048,3,5,NULL),(52,103,3,100,NULL),(52,104,3,250,NULL),(52,105,3,100,NULL),(52,91,3,80,NULL),(52,83,3,300,NULL),(52,2068,3,75,NULL),
  (24,33,1,20,4), (24,32,1,50,20), (24,41,5,150,NULL),(24,42,3,20,NULL),(24,85,3,4,NULL),(24,2049,3,5,NULL),(24,2048,3,5,NULL),(24,103,3,100,NULL),(24,104,3,250,NULL),(24,105,3,100,NULL),(24,91,3,80,NULL),(24,83,3,300,NULL),(24,2068,3,75,NULL);


UPDATE
 `m_sensorinfo`
SET
  `target` = 300
WHERE `sensorPhysicalid` = 83;


UPDATE
 `m_sensorinfo`
SET
  `target` = 4
WHERE `sensorPhysicalid` = 85;

CREATE TABLE `m_zone_threshold` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `zoneId` VARCHAR(50) NOT NULL COMMENT '区域uuid',
  `sensorPhysicalId` INT(11) NOT NULL COMMENT '监测指标id',
  `conditionType` INT(1) DEFAULT NULL COMMENT '达标条件类型，1-范围；2-大于；3-小于；4-大于等于；5-小于等于。与目标值/浮动值有关',
  `target` FLOAT DEFAULT NULL COMMENT '文保行业监测调控预期目标值',
  `floating` FLOAT DEFAULT NULL COMMENT '浮动值：以目标值为中心的浮动范围',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='区域阈值表';

DROP TABLE IF EXISTS `m_threshold_user` ;

DROP TABLE IF EXISTS `m_threshold`;

ALTER TABLE m_threshold_sensor RENAME m_threshold;
