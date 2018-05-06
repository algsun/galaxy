 -- author : li.jianfei
 -- description 添加IO模块版本字段

ALTER TABLE `p_dv_place_optics`
  ADD COLUMN `ioVersion` INT(11) DEFAULT 1 NULL COMMENT 'IO模块版本' AFTER `dvPort`;

