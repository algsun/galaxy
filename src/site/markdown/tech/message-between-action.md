## action(struts) 之间传递消息

场景：添加成功后，跳转到列表页面，并提示添加成功。

### 传统实现

要实现上面的场景，传统的方式可以通过 http 参数传递, 由添加 action 执行成功后传递参数给列表 action, 然后在列表页面显示。
最简单的方式是重定向到列表页面时携带参数，如果使用 struts 重定向带参数还需要使用 `${value}` 这种方式动态取值。
同时添加 action 与 列表 action 双方要协商好参数的名称。

### 利用 session

类似于 rails 中的 flash 机制。添加 action 将要传递的消息添加到 session 中, 然后无论用什么方式跳转到列表 action,
然后列表 action 将消息从 session 中取出并删除(如果不删除会消费两次)，最后将消息显示在页面。
使用这种方式 action 之间不需要参数提前协商，不限制 action 的跳转方式，action 之间有比较松的耦合。

### 抽象工具

基于上面 session 方式，我们抽象了 com.microwise.common.action.__ActionMessage__ 。
ActionMessage 将消息分为两个阶段: __生产消息__，__消费消息__ 。生产消息与消费消息是相互独立的。

场景再现：

添加 action

    public class AddXxxAction {
        //...
        public String execute() {
            // 添加成功
            ActionMessage.createByAction()
                .success("添加xxx成功");

            return Action.SUCESS;
        }
    }

列表 action

    public class ListXxxAction {
        //...
        public String execute(){
            ActionMessage.createByAction()
                .consume();

            return Action.SUCESS;
        }
    }

列表页面(freemarker)

    <#if _message??>
        <#if _success>
            <div class="success">${_message}</div>
        <#else>
            <div class="fail">${_message}</div>
        </#if>
    </#if>


#### 生产消息

    sucess(msg)      // 简单消息
    fail(msg)        // 简单消息
    put(key, value)  // 自定义消息

#### 消费消息

    consume()    // 消费简单消息
    consume(key) // 消息自定义消息
    consuemAll() //消费全部消息

#### 页面使用

* 简单消息

    两个值 `_message` 消息内容，类型为 string；`_success` 成功与否, 类型为 boolean

* 自定义消息

    有自定义的消息 `key`, 消息通过 `_message_{key}` 或者 `_message_["key"]` 取值

页面同时也可结合 `/common/pages/message-tooltip.ftl` 使用。
