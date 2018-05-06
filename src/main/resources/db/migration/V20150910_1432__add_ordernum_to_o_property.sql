--author lijianfei
--description 文物属性字典表结构数据调整

ALTER TABLE o_property
  ADD COLUMN orderNum INT(11) NOT NULL  COMMENT '排序号' AFTER propertyType,
  ADD COLUMN visible INT(1) DEFAULT 1  NOT NULL  COMMENT '是否显示？1-显示；0-隐藏' AFTER orderNum;

ALTER TABLE o_property
  CHANGE propertyType propertyType INT(11) DEFAULT 1  NOT NULL  COMMENT '1、输入框 2、日期类型  3、文本域 4、下拉选择框';

INSERT INTO o_property(cnName, enName, belong, propertyType, orderNum)
SELECT '收藏单位', 'institution', 4, 4, 2 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM o_property WHERE cnName = '收藏单位');

INSERT INTO o_property(cnName, enName, belong, propertyType, orderNum)
SELECT '库房', 'storehouse', 4, 4, 3 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM o_property WHERE cnName = '库房');

