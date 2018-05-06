-- author chenyaofei
-- describe  t_system_privilege add privilegeName_en_US column  and alter privilegeName_CN column

ALTER TABLE t_system_privilege CHANGE privilegeName_CN privilegeName_zh_CN VARCHAR(50) NOT NULL COMMENT '权限名称-中文';
ALTER TABLE t_system_privilege ADD privilegeName_en_US VARCHAR(50) COMMENT '权限名称-英文' AFTER privilegeName_zh_CN;