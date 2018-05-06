--author xu.yuexi
--description修改表h_exhibition_state 更改beginTime,endTime字段可以为空

--删除beginTime字段
ALTER TABLE  h_exhibition_state DROP COLUMN beginTime;
--删除endTime字段
ALTER TABLE  h_exhibition_state DROP COLUMN endTime;
--添加beginTime字段
ALTER TABLE  h_exhibition_state ADD COLUMN beginTime dateTime;
--添加endTime字段
ALTER TABLE  h_exhibition_state ADD COLUMN endTime dateTime;