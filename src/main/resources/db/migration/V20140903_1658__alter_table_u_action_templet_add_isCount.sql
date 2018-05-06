-- author xu.yuexi
-- description 给表u_action_templet添加行isCount判断是否统计
ALTER TABLE u_action_templet ADD isCount INT(2) DEFAULT 1 COMMENT "是否统计，0否，1是";