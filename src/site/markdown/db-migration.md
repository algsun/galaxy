## 数据库迁移

这是一个沉重的话题，每次做 Galaxy 环境的升级维护，首先需要对数据进行迁移到最新版本。
但迁移期间可能数据修改了许多地方，需要对比现在的数据库与最新数据库差别。然后应用这些差别，例如加张表，或者表中加一列。
在测试环境还好，但在生产环境，做这个事情是很刺激的。所以不得不将数据库迁移自动化提上日程，
世面上有一些做数据库迁移的工具(如 [flyway](http://flywaydb.org/)，下面要说的迁移脚本的格式也是flyway 的格式)，
但不管怎么样，都有迭代的迁移脚本，所以我们先从习惯编写迁移脚本开始。

#### 编写数据库迁移脚本

* 文件位置

    `src/resources/db/migration`

* 文件名称规则

    `V{date}_{time}__{description}.sql` 为 `V{日期}_{时间}__{描述}.sql`，注意描述前两个`-`，文件名除了除了第一个字符统一使用小写,
    例如 `V20130829_0916__add_qq_number_to_user.sql`，添加 QQ 号到用户表 。

* 一个脚本只做一件事情

    方便定位问题，有些数据不支持多条DDL语句事务

* 文件内容

    文件内要有作者，迁移的简单描述


    -- author: gao.hui
    -- description: add QQ number to user table

    ALTER TABLE user
      ADD COLUMN qq_number varchar(10);


#### 决对不要手动改数据库，即使是测试环境

如果你手动改数据库，那么维护人员则需求同样的操作在生产环境重复一次，但生产环境不只一个。
维护不同版本的数据真的是一件很爽的事情。
我们想利用自动化工具，将你的操作自动化在每个生产环境上，所以最好的方式是写迁移脚本。
然后让机器去做机器能做的事情，省下的时间可以思考一下人生。

如果发现手动改数据库，奖励 Galaxy 生产环境维护机会一次，可累积。

## flyway 的使用

* 第一次初始化数据库


    mvn flyway:init

* 查看状态


    mvn clean compile flyway:info

* 执行迁移


    mvn clean compile flyway:migrate

* 修复迁移(迁移失败的情况下)


    mvn flyway:repair

更多参考 [flyway maven 使用](http://flywaydb.org/documentation/maven/)。

#### 命令参数

* dbHost 数据库主机，默认为`127.0.0.1`
* dbPort 数据库端口，默认为 `3306`
* db 数据库名，默认为 `galaxy`
* dbUser 用户名，默认为 `microwise`. 如果没有 microwise 用户，请参考[数据库说明](databases.html)添加用户
* dbPassword 密码，默认为 `microwise`

以上参数可通过命令指定，如 `mvn flyway:info -DdbHost=192.168.0.88 -DdbUser=root -DdbPassword=1234` 。

> @gaohui 2013-08-29
