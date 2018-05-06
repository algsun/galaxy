package com.microwise.blackhole.action.theme;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Sessions;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Administrator
 * Date: 13-11-27
 * Time: 上午8:53
 */
public class CssAction {

    private static final int userBg = 1024; //默认的两套分别记做1和2，自定义皮肤为1024

    @Autowired
    private LogicGroupService logicGroupService;
    /**
     * 标题图片路径
     */
    private String titlePath;
    /**
     * 背景图片路径
     */
    private String bgPath;


    @Route("/blackhole/theme/theme.css")
    public String css() {
        if (Sessions.createByAction().currentLogicGroup() != null) {
            //拿到当前logicGroupId
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            //根据当前站点ID拿到当前站点
            LogicGroup logicGroup = logicGroupService.findLogicGroupCarrySite(logicGroupId);
            if (logicGroup.getUseBg() == userBg) {
                bgPath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/logicGroup/" + logicGroup.getBgImage();
                //如果大于0取默认背景
            } else if (logicGroup.getUseBg() > 0) {
                bgPath = "../images/body-bg-" + logicGroup.getUseBg() + ".png";
            } //为避免用户再添加站点时，添加站点的程序没有定义 ，去默认背景1
            else {
                bgPath = "../images/body-bg-1.png";
            }
        }
        //freemarker默认添加的头饰text/html，这里生成css文件不适用，所以将头改成 text/css
        ServletActionContext.getResponse().setContentType("text/css; charset=utf-8");
        return Results.ftl("/blackhole/pages/theme/theme.css");

    }

    public String getTitlePath() {
        return titlePath;
    }

    public void setTitlePath(String titlePath) {
        this.titlePath = titlePath;
    }

    public String getBgPath() {
        return bgPath;
    }

    public void setBgPath(String bgPath) {
        this.bgPath = bgPath;
    }
}
