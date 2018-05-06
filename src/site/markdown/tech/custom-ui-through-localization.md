## 通过本地化定制界面

界面中经常遇到需要定制的地方，如 xxx馆 在实施的时候系统名称需要换成自己的，logo 图片也有自己的要求。
但是在项目中不能仅为 xxx馆 写死代码，其他馆同样也很可能有自己的需求。
所以我们引入 java 中本地化机制，但我们并不是针对语言与地区的本地化，而是某项目实施的定制“本地化”，原理上是一样的。


### 本地资源配置

基于上面需求，我们对 java 本地化进行封装实现不同项目实现的“本地化”。本地资源文件在 `resources/custom/` 目录下，
默认为 `ui.properites`，定制的资源命名为 `ui_xxx.properties` ，同时使用此资源的 `locale` 为 `xxx`。
定制的资源文件中可覆盖默认配置，或者加新的配置，未变化的地方不需要照抄默认资源文件。

例如：

ui.properties

    app.name=银河
    app.logo=galaxy.png

ui_xxx.properties

    app.name=xxx馆管理系统
    app.title=xxx独有配置

上面配置 xxx 定制覆盖了 `app.name` ，并且添加 `app.title` 配置。

我们拥有n多定制后，系统默认使用默认UI，如果需要切换到指定定制要在 `resources/config.properites` 文件中修改 `app.locale` 配置。
如切换到 xxx 定制，则 `app.locale=xxx`。

### freemarker 使用

因为定制的大多在页面我们同时提供 freemarker 获取本地资源配置信息接口(参考 `resources/common/freemaker/share-variables.properties`)。

接口列表：

* localeUI.contains(String key):boolean
* localeUI.getStr(String key):String
* localeUI.getInt(String key):int
* localeUI.getBool(String key):boolean
* localeUI.getStrs(String key):List&lt;String&gt;
