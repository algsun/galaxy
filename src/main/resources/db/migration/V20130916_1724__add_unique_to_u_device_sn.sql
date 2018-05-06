-- author xu.baoji 
-- description 为表 u_device表 sn 字段添加唯一约束
ALTER TABLE `u_device` ADD UNIQUE(`sn`);