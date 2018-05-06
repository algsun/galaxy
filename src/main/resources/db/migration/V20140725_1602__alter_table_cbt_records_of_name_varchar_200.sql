--author xu.yuexi
--description 更改档案管理表，案卷題名保存
ALTER TABLE cbt_records MODIFY `name` VARCHAR(200) NOT NULL COMMENT '案卷题名';
