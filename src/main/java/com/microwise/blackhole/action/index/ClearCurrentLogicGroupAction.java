package com.microwise.blackhole.action.index;

import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;

/**
 * 清楚当前站点相关 session 并跳转到指定页面
 *
 * @author gaohui
 * @date 12-12-17 10:07
 */
@Beans.Action
@Blackhole
public class ClearCurrentLogicGroupAction {
    /**
     * 跳转到的页面, 链接地址相对于 "{host}:{port}/{webContext}"
     */
    private String forward;

    public String execute() {
        Sessions sessions = Sessions.createByAction();
        sessions.clearCurrentLogicGroupAll();
        return Action.SUCCESS;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }
}
