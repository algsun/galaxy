--author xu.yuexi
--description 更改档案管理表，档案Id可以为空
ALTER TABLE cbt_attachement MODIFY recordUuid VARCHAR(22) DEFAULT NULL COMMENT '档案ID';
