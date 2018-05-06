--author xuyuexi
--description 更改新闻权限，初始化时赋给管理员

UPDATE  t_system_privilege SET state=50 WHERE privilegeId LIKE 'blackhole:post%'