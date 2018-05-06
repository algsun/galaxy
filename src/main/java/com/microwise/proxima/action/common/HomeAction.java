package com.microwise.proxima.action.common;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-3-22 上午10:59
 */
@Beans.Action
@Proxima
public class HomeAction {
    public String execute() {
        return Action.SUCCESS;
    }
}
