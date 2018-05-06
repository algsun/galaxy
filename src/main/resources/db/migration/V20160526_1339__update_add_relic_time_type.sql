-- author: liuzhu
-- description: 修改藏品入藏时间类型

UPDATE `o_property`
SET
  `propertyType` = 1
WHERE `id` = 17;
