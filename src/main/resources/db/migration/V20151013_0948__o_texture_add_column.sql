--author 王耕
--文物修复新增，固化六个材质，不能被删除修改
ALTER TABLE `o_texture` ADD COLUMN deleteAble INT(2) DEFAULT 1 COMMENT '是否能被删除,1:可以被删除;0:不能被删除';
ALTER TABLE `o_texture` ADD COLUMN enName varchar(11) NULL COMMENT '材质的英文名称';

UPDATE `o_texture` SET enName = 'paper' where id = 43 AND name = '纸';

INSERT INTO `o_texture`(NAME,deleteAble,enName) VALUES('壁画',0,'mural');
INSERT INTO `o_texture`(NAME,deleteAble,enName) VALUES('馆藏纺织品',0,'spinning');
INSERT INTO `o_texture`(NAME,deleteAble,enName) VALUES('金属',0,'metal');
INSERT INTO `o_texture`(NAME,deleteAble,enName) VALUES('丝织品',0,'fabrics');
INSERT INTO `o_texture`(NAME,deleteAble,enName) VALUES('陶制彩绘',0,'ceramicPain');
INSERT INTO `o_texture`(NAME,deleteAble,enName) VALUES('纸质书画',0,'painting');