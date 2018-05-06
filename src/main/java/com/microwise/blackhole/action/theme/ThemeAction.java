package com.microwise.blackhole.action.theme;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: xu.yuexi
 * @date: 13-11-25
 */

@Beans.Action
@Blackhole
public class ThemeAction {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(ThemeAction.class);

    /**
     * 默认的两套分别记做1和2，自定义皮肤为1024
     */
    private static final int userBg = 1024;

    @Autowired
    private BlackholeLoggerUtil logger;
    @Autowired
    private LogicGroupService logicGroupService;
    //input

    /**
     * 判断当前使用主题
     */
    private int isUsed;
    /**
     * 宣传图片是否使用自定义，使用自定义在页面显示出来
     */
    private String define;
    /**
     * 背景是否使用自定义，使用自定义在页面显示出来
     */
    private String defineBg;

    public String execute() {
        try {
            //拿到当前logicGroupId
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            //根据当前站点ID拿到当前站点
            LogicGroup logicGroup = logicGroupService.findLogicGroupCarrySite(logicGroupId);
            //如果背景和宣传一致并且不是自定义
            if (logicGroup.getUseBg() == logicGroup.getUseTitle() && logicGroup.getUseBg() != 1024) {
                //获得使用了哪一套，为了页面判断
                isUsed = logicGroup.getUseBg();
            } else {
                //否则，是否使用为自定义
                isUsed = userBg;
                //如果宣传图片自定义的话，获取自定义路径的图片
                if (logicGroup.getUseTitle() == userBg) {
                    define = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/logicGroup/" + logicGroup.getTitleImage();
                } else {
                    //如果宣传图片默认背景自定义的话，获取默认图片
                    define = "images/index/content-" + logicGroup.getUseTitle() + ".jpg";
                }
                //如果背景自定义同宣传图片注释
                //宣传和背景分开写是因为 可以单独上传
                if (logicGroup.getUseBg() == userBg) {
                    defineBg = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/logicGroup/" + logicGroup.getBgImage();
                } else {
                    defineBg = "images/body-bg-" + logicGroup.getUseBg() + ".png";
                }
            }
            //跳转至更换皮肤页面
            logger.log("站点管理", "更换皮肤");
        } catch (Exception e) {
            logger.logFailed("站点管理", "更换皮肤");
            log.error("", e);
        }
        ActionMessage.createByAction().consume();
        return Action.SUCCESS;
    }

    public int getUsed() {
        return isUsed;
    }

    public void setUsed(int used) {
        isUsed = used;
    }

    public String getDefine() {
        return define;
    }

    public void setDefine(String define) {
        this.define = define;
    }

    public String getDefineBg() {
        return defineBg;
    }

    public void setDefineBg(String defineBg) {
        this.defineBg = defineBg;
    }
}
