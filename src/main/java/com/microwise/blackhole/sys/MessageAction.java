package com.microwise.blackhole.sys;

import com.microwise.common.sys.Constants;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

/**
 * 可传递消息 action
 * <p/>
 * <p>
 * success() 或 fail() 产生消息, consume() 来消费消息, 页面上通过 _message 和 _success 来取消息
 * </p>
 * <p/>
 * <pre>
 *     public class ProducerAction extends MessageAction {
 *         public String execute(){
 *             //产生消息
 *             success("操作成功");
 *             return EventAction.SUCCESS;
 *         }
 *     }
 *
 *
 *     // ProducerAction 可以以任何方法跳转到 ConsumerAction
 *     // 在ConsumerAction 消费之前, 消息不会消失
 *     // 消费之后可以通过 _message:String 和 _success:boolean 获取在页面显示
 *     public class ConsumerAction extends MessageAction {
 *         public String execute(){
 *             //消费消息
 *             consume();
 *             return EventAction.SUCCESS;
 *         }
 *     }
 * </pre>
 * <p/>
 * <p/>
 * 注意：可以通过非继承的方式 @code{com.microwise.common.action.ActionMessage} 实现与此类同样的效果
 * <p/>
 * <p/>
 * Date: 12-10-24 Time: 上午10:15
 *
 * @author gaohui
 */
public abstract class MessageAction extends LoggingAction {
    private String _message;
    private boolean _success;

    public void success(String message) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put(Constants.Actions.MESSAGE, message);
        session.put(Constants.Actions.SUCCESS, true);
    }

    public void fail(String message) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put(Constants.Actions.MESSAGE, message);
        session.put(Constants.Actions.SUCCESS, false);
    }

    public void consume() {
        Map<String, Object> session = ActionContext.getContext().getSession();

        _message = (String) session.remove(Constants.Actions.MESSAGE);
        Object success = session.remove(Constants.Actions.SUCCESS);
        if (success != null) {
            _success = (Boolean) success;
        }
    }

    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public boolean is_success() {
        return _success;
    }

    public void set_success(boolean _success) {
        this._success = _success;
    }
}
