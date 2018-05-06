-- author chen.yaoFei ;
-- description ADD subsystemName_zh_CN AND ADD subsystemName_en_US column;

ALTER TABLE t_subsystem ADD subsystemName_zh_CN VARCHAR(50) COMMENT '业务系统名称-中文' AFTER subsystemName;
ALTER TABLE t_subsystem ADD subsystemName_en_US VARCHAR(50) COMMENT '业务系统名称-英文' AFTER subsystemName_zh_CN;

-- 数据迁移
UPDATE t_subsystem SET  subsystemName_zh_CN=subsystemName;