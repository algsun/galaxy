package com.microwise.blackhole.action.index;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 显示业务系统
 *
 * @author bastengao
 * @Date 12-10-22 Time: 上午9:51
 * @check @wang.yunlong & li.jianfei   #79     2012-12-18
 */
@Beans.Action("subsystemsAction")
@Blackhole
public class SubsystemsAction {

    @Autowired
    private LogicGroupService logicGroupService;
    /**
     * 标题图片路径
     */
    private String titlePath;

    /**
     * 首页 模版
     */
    private String template;

    /**
     * 系统 模版
     */
    private String mode;

    /**
     * UI定制样式
     */
    private String appLocale;

    public String execute() {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        mode = appConfig.get(Constants.Config.GALAXY_MODE);
        appLocale = appConfig.get(Constants.Config.APP_LOCALE);
        LogicGroup currentLogicGroup = Sessions.createByAction().currentLogicGroup();
        if (currentLogicGroup == null) {
            return Action.ERROR;
        }
        //拿到当前logicGroupId
        int logicGroupId = currentLogicGroup.getId();
        //根据当前站点ID拿到当前站点
        LogicGroup logicGroup = logicGroupService.findLogicGroupCarrySite(logicGroupId);
        //如果站点的useTitle为1024表示使用的是自定义取自定义
        if (logicGroup.getUseTitle() == 1024) {
            titlePath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/logicGroup/" + logicGroup.getTitleImage();
            //如果选择默认主题，取默认主题图片
        } else if (logicGroup.getUseTitle() > 0) {
            titlePath = "images/index/content-" + logicGroup.getUseTitle() + ".jpg";
            //为避免用户再添加站点时，添加站点的程序没有定义useTitle,使用默认
        } else {
            titlePath = "../images/index/content-1.jpg";
        }

        template = logicGroupService.findTemplate(logicGroupId);
        if (template == null) {
            template = "template_1";
        }
        return Action.SUCCESS;
    }

    public String getTitlePath() {
        return titlePath;
    }

    public void setTitlePath(String titlePath) {
        this.titlePath = titlePath;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAppLocale() {
        return appLocale;
    }

    public void setAppLocale(String appLocale) {
        this.appLocale = appLocale;
    }
}
