--li.jianfei
--t_zone position column 改为小写
--解决 Windows与 Linux 下区域Position字段大小写格式不一致 问题

ALTER TABLE t_zone CHANGE POSITION `position` INT(1);