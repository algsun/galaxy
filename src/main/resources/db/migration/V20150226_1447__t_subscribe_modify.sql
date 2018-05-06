--author 王耕
--description t_subscribe表新增字段locationId，并且修改subscribeType的描述信息

ALTER TABLE t_subscribe MODIFY subscribeType INT(1) NOT NULL COMMENT '订阅类型：1 周报表;2 月报表;3 KDJ报警';

ALTER TABLE t_subscribe  ADD locationId VARCHAR(36) DEFAULT NULL COMMENT 'KDJ报警-用户关注的位置点'