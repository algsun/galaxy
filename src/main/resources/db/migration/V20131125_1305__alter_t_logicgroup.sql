-- author xu.yuexi
-- 给t_logicgroup添加两列 titleImage和bgImage

-- 给t_logicgroup添加列 titleImage
ALTER TABLE t_logicgroup  ADD COLUMN titleImage varchar(100);

-- 给t_logicgroup添加列 bgImage
ALTER TABLE t_logicgroup  ADD COLUMN bgImage varchar(100);