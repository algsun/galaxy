package com.microwise.blackhole.sys;

import com.microwise.common.sys.Constants;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

/**
 * 与 MessageAction 配合使用
 * <p/>
 * <p/>
 * 注意：推荐使用通过 @code{com.microwise.common.action.ActionMessage} 实现与此类同样的效果
 *
 * @author bastengao
 * @date 12-10-24 Time: 上午10:2
 * @check @wang.yunlong & li.jianfei    #833   2012-12-18
 */
public final class MessageActionUtil {
    private MessageActionUtil() {
    }

    /**
     * 产生成功消息
     *
     * @param message
     */
    public static void success(String message) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put(Constants.Actions.MESSAGE, message);
        session.put(Constants.Actions.SUCCESS, true);
    }

    /**
     * 产生失败消息
     *
     * @param message
     */
    public static void fail(String message) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put(Constants.Actions.MESSAGE, message);
        session.put(Constants.Actions.SUCCESS, false);
    }
}
