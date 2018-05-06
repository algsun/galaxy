--author xu.yuexi
--description修改表h_exhibition_state 删除changeTime字段,添加beginTime,endTime,operator字段
-- 删除changeTime字段
ALTER TABLE h_exhibition_state DROP COLUMN changeTime;
--添加beginTime字段
ALTER TABLE  h_exhibition_state ADD  beginTime dateTime not null;
--添加 endTime字段
ALTER TABLE  h_exhibition_state ADD  endTime dateTime not null;
--添加 operator字段
ALTER TABLE  h_exhibition_state ADD  operator int not null;
--添加operator字段的外键关联
alter table h_exhibition_state add constraint FK_Reference_95 foreign key (operator)
      references t_users (id) on delete restrict on update restrict;