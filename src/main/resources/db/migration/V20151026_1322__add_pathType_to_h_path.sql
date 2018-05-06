--author liuzhu
--description h_path表添加列

ALTER TABLE h_path ADD COLUMN  pathType INT(11) NOT NULL COMMENT '0:最快捷模式 1:最经济模式 2:最短距离模式';