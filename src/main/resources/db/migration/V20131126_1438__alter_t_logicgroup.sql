-- author xu.yuexi
-- 给t_logicgroup添加两列 useTitle和useBg
-- useTitle使用默认的标题，0为不使用默认标题，1为使用默认标题1,2为使用默认标题2
-- 给t_logicgroup添加列 useTitle
ALTER TABLE t_logicgroup  ADD COLUMN useTitle int(10) default 1;

-- useBg使用默认的背景，0为不使用默认背景，1为使用默认背景1,2为使用默认背景2
-- 给t_logicgroup添加列 useBg
ALTER TABLE t_logicgroup  ADD COLUMN useBg int(10) default 1;