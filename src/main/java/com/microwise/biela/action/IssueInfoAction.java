package com.microwise.biela.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.biela.sys.Biela;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;

/**
 * @author liuzhu
 * @date 14-2-27
 */
@Beans.Action
@Biela
public class IssueInfoAction {

    @Route("/biela/issueInfo")
    public String execute() {
        LogicGroup logicGroup = Sessions.createByAction().currentLogicGroup();
        return Results.redirect("../blueplanet/dataCenter/charts/toAllBasicSite/" + logicGroup.getId());
    }
}
