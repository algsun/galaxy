## session 说明

用户登录后 session 中会放入 N 多东西，例如当前用户，用户的归属站点(LogicGroup) 等等。

session 中的值：

* "currentUser": User
    当前用户
* "userLogicGroup": LogicGroup
    用户归属站点


如果用 java 可以通过 `com.microwise.blackhole.sys.Sessions` 类获取当前用户 session 中的各种属性。

* currentUser: User

    当前用户

* currentUserLogicGroup: LogicGroup

    用户归属站点

* currentLogicGroup: LogicGroup

    当前 logicgroup

* currentSiteId: String

    当前站点ID
