# 使用 spring

## bean 层次

层次依次为：

1. action
2. proxy
3. service
4. dao

层次之间顺序调用，禁止跨层调用。其中 action 可以调用 proxy 和 service。


## 命名空间

由于此项目多个业务系统在一个项目中且 spring bean 数目众多，我们需要 spring 提供命名空间支持，将不同的业务的 bean 划分开。
但 spring 本身是不支持命名空间的，所以只能变向的来实时命名空间，统一在所有的 bean name 前加业务系统前缀。

*   xml 方式

    name 前加 业务系统标识，中间用冒号`:`分隔

        <bean name="blackhole:loginAction" scope="prototype" class="com.microwise.blackhole.action.LoginAction"/>

*   注解方式

    在 class 上加 `@Subsystem("blackhole")` 注解, 或者直接加 `@Blackhole` 注解

        @Compment
        @Scope("prototype")
        @Blackhole
        public class LoginAction{
            //...
        }

    以上是使用方式，但想要达到上面的效果需要在 spring xml 配置文件中加入下面：

        <context:component-scan
            base-package="com.example.your.package"
            name-generator="com.microwise.common.sys.annotation.PrefixBeanNameGenerator" />

## 注解声明 bean

*   action

        @Compomnet
        @Scope("prototype")
        public class ExampleAction{
            //...
        }

        // 或者，效果同上
        @Beans.Action
        public class ExampleAction{
            //...
        }

* service

        @Service
        @Scope("prototype")
        public class ExampleService {
            //...
        }

        // 或者
        @Beans.Servcie
        public class ExampleService {
            //...
        }

* dao

        @Repository
        @Scope("prototype")
        public class ExampleDao {
            //...
        }

        // 或者
        @Beans.Dao
        public class ExampleDao {
            //...
        }
