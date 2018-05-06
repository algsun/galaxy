package com.microwise.common.action;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.microwise.common.sys.Constants;
import com.opensymphony.xwork2.ActionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * action 之间或者 action 与页面之间传递消息辅助类
 * <p/>
 * 生产消息:
 *
 * sucess(msg)      // 简单消息
 * fail(msg)        // 简单消息
 * put(key, value)  // 自定义消息
 *
 *
 * 消费消息:
 *
 * consume()    // 消费简单消息
 * consume(key) // 消息自定义消息
 * consuemAll() //消费全部消息
 *
 *
 * 页面使用:
 *
 * 简单消息
 * 两个值 `_message` 消息内容，类型为 string；`_success` 成功与否, 类型为 boolean
 *
 * 自定义消息
 * 有自定义的消息 `key`, 消息通过 `_message_{key}` 或者 `_message_["key"]` 取值
 *
 * @author gaohui
 * @date 12-12-14 13:35
 */
public class ActionMessage {
    public static final String CUSTOM_MESSAGE = "_galaxy_custom_message";
    public static final String SUCCESS_OR_FAIL_MESSAGE = "_message";
    public static final String SUCCESS_OR_FAIL = "_success";

    /**
     * 创建 ActionMessage
     *
     * @param actionContext
     * @return
     */
    public static ActionMessage create(ActionContext actionContext) {
        return new ActionMessage(actionContext);
    }

    /**
     * 创建 ActionMessage , 此方法强烈建议只在 action 执行线程被调用
     *
     * @return
     */
    public static ActionMessage createByAction() {
        return create(ActionContext.getContext());
    }


    private ActionContext actionContext;

    protected ActionMessage(ActionContext actionContext) {
        this.actionContext = actionContext;
    }

    /**
     * 产生自定义消息
     *
     * @param key
     * @param value
     * @return
     */
    public ActionMessage put(String key, Object value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key));

        Map<String, Object> customMessage = customMessage();
        customMessage.put(key, value);
        return this;
    }

    /**
     * 消费自定义消息
     * <p/>
     * 如果有消息，可以通过 _message_{key} 或者 _message_["key"] 在页面使用
     *
     * @param key
     * @return
     */
    public ActionMessage consume(String key) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key));

        Map<String, Object> customMessage = customMessage();
        if (customMessage.containsKey(key)) {
            Object value = customMessage.get(key);
            String actionContextName = String.format("_message_%s", key);
            putToCurrentActionContext(actionContextName, value);
            putCustomMessageToCurrentActionContext(key, value);

            customMessage.remove(key);
        }
        return this;
    }

    /**
     * 成功消息
     * <p/>
     * 通过 _message 和 _success 在页面使用,
     *
     * @param message
     * @return
     */
    public ActionMessage success(String message) {
        successOrFail(message, true);
        return this;
    }

    /**
     * 失败消息
     * <p/>
     * 通过 _message 和 _success 在页面使用,
     *
     * @param message
     * @return
     */
    public ActionMessage fail(String message) {
        successOrFail(message, false);
        return this;
    }


    /**
     * 消费消息
     * <p/>
     * 如果有消息，通过 _message 和 _success 在页面使用,
     *
     * @return
     */
    public ActionMessage consume() {
        Map<String, Object> session = session();

        String _message = (String) session.remove(Constants.Actions.MESSAGE);
        if (_message != null) {
            putToCurrentActionContext(SUCCESS_OR_FAIL_MESSAGE, _message);
        }

        Object success = session.remove(Constants.Actions.SUCCESS);
        if (success != null) {
            Boolean _success = (Boolean) success;
            putToCurrentActionContext(SUCCESS_OR_FAIL, _success);
        }
        return this;
    }

    /**
     * 成功或失败消息
     *
     * @param message
     * @param successOrFail
     */
    private void successOrFail(String message, boolean successOrFail) {
        Map<String, Object> session = session();
        session.put(Constants.Actions.MESSAGE, message);
        session.put(Constants.Actions.SUCCESS, successOrFail);
    }

    /**
     * 将 value 按 key 添加到 action context 中
     *
     * @param key
     * @param value
     */
    private void putToCurrentActionContext(String key, Object value) {
        actionContext.put(key, value);
    }

    /**
     * 将自定义消息添加到 action context 中
     *
     * @param key
     * @param value
     */
    private void putCustomMessageToCurrentActionContext(String key, Object value) {
        Map<String, Object> customMessageInContext = (Map<String, Object>) actionContext.get("_message_");
        if (customMessageInContext == null) {
            customMessageInContext = new HashMap<String, Object>();
            actionContext.put("_message_", customMessageInContext);
        }

        customMessageInContext.put(key, value);
    }

    /**
     * 保证自定义消息容器存在
     */
    private Map<String, Object> ensureCustomMessageExists() {
        Map<String, Object> session = session();
        if (!session.containsKey(CUSTOM_MESSAGE)) {
            session.put(CUSTOM_MESSAGE, new HashMap<String, Object>());
        }

        return (Map<String, Object>) session.get(CUSTOM_MESSAGE);
    }

    /**
     * @return 返回自定义消息 Map
     */
    private Map<String, Object> customMessage() {
        return ensureCustomMessageExists();
    }

    /**
     * @return 返回 session 对应 Map
     */
    private Map<String, Object> session() {
        return this.actionContext.getSession();
    }
}

