## shiro 简介

[shiro](http://shiro.apache.org/) 是一个安全框架，类似的还有 [string security](http://www.springsource.org/spring-security)
。 目前项目只用到他的认证，因为用户的两套权限集合(归属站点权限与浏览其他站点的访客权限)，
授权是通过用户 session 中的权限集合判断的(没有用到 shiro 的授权机制)。

[关于 shiro 的简单介绍](http://192.168.0.101:3000/boards/3/topics/170) -- 内网 redmine 上


### 认证

    // 登录/login

    //1. Acquire submitted principals and credentials:
    AuthenticationToken token = new UsernamePasswordToken(username, password);

    //2. Get the current Subject:
    Subject currentUser = SecurityUtils.getSubject();

    //3. Login:
    currentUser.login(token);

### 核心对象

    // Subject/当前用户
    Subject currentUser = SecurityUtils.getSubject();

### 授权

    // 是否认证
    Subject#isAuthenticated()

    // 是否有角色
    Subject#hasRole(String roleName)
    Subject#hasRoles(List<String> roleNames)
    Subject#hasAllRoles(Collection<String> roleNames)

    // 检查角色
    Subject#checkRole(String roleName)
    Subject#checkRoles(Collection<String> roleNames)
    Subject#checkRoles(String... roleNames)

    // 是否有权限
    Subject#isPermitted(Permission p)
    Subject#isPermitted(List<Permission> perms)
    Subject#isPermittedAll(Collection<Permission> perms)

    Subject#isPermitted(String perm)
    Subject#isPermitted(String... perms)
    Subject#isPermittedAll(String... perms)

    // 检查权限
    Subject#checkPermission(Permission p)
    Subject#checkPermission(String perm)
    Subject#checkPermissions(Collection<Permission> perms)
    Subject#checkPermissions(String... perms)

## 项目是实际使用

### freemarker

我们通过对 freemarker 进行扩展，添加了对 shiro 接口的一些封装，满足我们在 freemarker 页面上进行认证授权的判断。
详见 `src/main/resources/common/freemarker/share-variables.properties` 与
`com.microwise.common.sys.freemarker.GalaxyFreemarkerManager` 。

* 是否是超级管理员

    security.isSuperman():boolean

* 是否是站点管理员

    security.isSiteManager():boolean

* 是否是访客(用户在浏览非归属站点时，对于此站点用户为访客)

    security.isGuest():boolean

# 是否是匿名用户

    security.isAnonymity():boolean

* 返回当前用户(shiro Subject)

    security.currentUser():Subject

    security.isAuthenticated():boolean

* 用户是否在其他站点有权限

    security.isGuestPermitted(String permission):boolean

* 用户是否在归属站点有权限

    security.isUserPermitted(String permission):boolean

    security.isUserPermittedAll(String permissions...):boolean

* 用户是否在当前站点有权限

    security.isPermitted(String permission):boolean

* 用户是否拥有某个角色

    security.hasRole(String role):boolean

* 用户当前站点是否是归属站点

    security.atHome():boolean

### java

java 中可以通过 `com.microwise.common.sys.Security` 类判断权限

* 是否是超级管理员

    Security.isSuperman():boolean

* 是否是站点管理员

    Security.isSiteManager():boolean

* 是否是访客(用户在浏览非归属站点时，对于此站点用户为访客)

    Security.isGuest():boolean

* 是否是匿名用户

    Security.isAnonymity():boolean
