--author 王耕
--description 修改o_inscription表的info字段的长度为2000
ALTER TABLE `o_inscription` CHANGE  info info VARCHAR(2000);