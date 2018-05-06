--author liuzhu
--description dc_chart_condition 表添加两个字段

ALTER TABLE dc_chart_condition ADD url VARCHAR(1500) COMMENT '请求url',ADD serializationParam VARCHAR(1500) COMMENT '序列化的参数';

ALTER TABLE dc_layout ADD siteId VARCHAR(50) NOT NULL COMMENT '站点ID';
