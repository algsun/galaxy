package com.microwise.blackhole.action.theme;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 首页模版  action
 *
 * @author: xu.baoji
 * @date: 13-11-25
 */

@Beans.Action
@Blackhole
public class TemplateAction {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(TemplateAction.class);

    @Autowired
    private BlackholeLoggerUtil logger;
    @Autowired
    private LogicGroupService logicGroupService;

    private int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();


    /**
     * 数据库中 当前 站点 首页 模版
     */
    private String template;

    @Route("/blackhole/template.action")
    public String execute() {
        try {
            template = logicGroupService.findTemplate(logicGroupId);
            if (template == null) {
                template = "template_1";
            }
            logger.log("站点管理", "进入更换模版");
        } catch (Exception e) {
            logger.logFailed("站点管理", "进入更换首页模版");
            log.error("", e);
        }
        ActionMessage.createByAction().consume();
        return Results.ftl("/blackhole/pages/theme/template.ftl");
    }

    @Route("/blackhole/updateTemplate.action")
    public String update() {
        try {
            // 获得当前站点
            logicGroupService.updateTemplate(logicGroupId, template);
            ActionMessage.createByAction().success("更换模版成功");
            logger.log("站点管理", "更换首页模版");
        } catch (Exception e) {
            ActionMessage.createByAction().success("更换模版出错");
            logger.logFailed("站点管理", "更换首页模版");
            log.error("", e);
        }
        return Results.redirect("/blackhole/template.action");
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
