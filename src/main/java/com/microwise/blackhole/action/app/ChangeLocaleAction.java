package com.microwise.blackhole.action.app;

import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;

import java.util.Locale;

/**
 * Created by lijianfei on 15/12/15.
 */
@Beans.Action
@Blackhole
public class ChangeLocaleAction {

    @Route("changeLocale")
    public String changeLocale() {
        Locale locale = Locale.getDefault();
        ActionContext.getContext().getSession().put("WW-TRANS-I18N-LOCALE", locale);
        return "redirectTo";
    }
}
