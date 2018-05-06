# 应用程序配置

配置文件在 src/resources/config.properties

    # 应用程序模式 三种枚举
    # product, develop, test
    # 生产   , 开发   , 测试
    app.stage=product

目前只配置了应用程序模式

* 如果是__生产__环境 `app.stage` 配置为 `product`
* 如果是__开发__环境 `app.stage` 配置为 `develop`
* 如果是__测试__环境 `app.stage` 配置为 `test`

友情提示：

> 如果开发者懒得修改此配置，可以系统的环境变量加入 `I_AM_HACKER`, 值随便填写但不能为空。
> 则 `app.stage` 默认为 `develop`, 配置文件的值将会被忽略。

## java

可以通过

    Configs appConfig = ConfigFactory.getInstance().getConfig("config.properties");
    appConfig.get("app.stage");

来获取配置信息。


## jsp 或者 freemarker

可以通过 Application 获取配置信息，例如 freemarker

    Application["app.stage.product"]:boolean
    Application["app.stage.develop"]:boolean
    Application["app.stage.test"]:boolean
