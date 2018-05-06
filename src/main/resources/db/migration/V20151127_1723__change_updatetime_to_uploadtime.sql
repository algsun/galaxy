--author chen.yaofei
--description  修改上传时间字段
ALTER TABLE m_three_dimensional CHANGE updatetime uploadtime DATETIME NOT NULL  COMMENT '模型上传时间'
