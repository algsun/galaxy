## 编码

数据库编码统一使用 `utf-8`( 注意配置时为 utf8)

## 数据库名

* 测试数据库为: galaxy_test

* 开发数据库为: galaxy_dev

* 生产数据库为：galaxy_product

## 初始化数据库

用户统一为 "microwise" ,密码为 "microwise".
如果没有 "microwise" 可执行,

    $> GRANT ALL PRIVILEGES ON *.* TO 'microwise'@'localhost' IDENTIFIED BY 'microwise' WITH GRANT OPTION;

重新初始化开发数据库执行

    $> mvn validate -P rebuild-db

如果要指定数据库和主机，初始化数据库执行

    $> mvn validate -P rebuild-db -DdevDB=yourDatabase -DdbHost=yourIP

## 数据库角本
    
数据库脚本在 src/main/sql ：

	galaxy-schema-all.sql	数据库表结构
	galaxy-data-all.sql 	初始化数据
	galaxy-block-all.sql	程序块：视图/函数/存储过程
	
block注意，因为自动化测试问题，项目中，此文件开头和结束的DELIMITER需要注释起来

## 数据库链接配置

src/main/resources/config.properties

    jdbc.driver=com.mysql.jdbc.Driver
    jdbc.url=jdbc:mysql://127.0.0.1:3306/galaxy_product
    jdbc.username=msp
    jdbc.password=msp
