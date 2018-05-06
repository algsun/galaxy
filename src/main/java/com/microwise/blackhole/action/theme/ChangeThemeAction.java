package com.microwise.blackhole.action.theme;

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
public class ChangeThemeAction {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(ChangeThemeAction.class);
    @Autowired
    private LogicGroupService logicGroupService;
    @Autowired
    private BlackholeLoggerUtil logger;

    /**
     * 站点皮肤
     */
    private String titleImage;

    /**
     * 站点背景
     */
    private String bgImage;


    /**
     * 标记选择背景
     */
    private int filePathFlag;

    public String execute() {
        try {
            //拿到当前logicGroupId
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            //修改站点的useTitle标记是1使用默认的一号2使用默认的2号1024自定义，其他使用默认一号
            logicGroupService.updateLogicGroupUseTitle(filePathFlag, logicGroupId);
            logicGroupService.updateLogicGroupUseBg(filePathFlag, logicGroupId);
            ActionMessage.createByAction().success("主题更换成功");
            logger.log("站点管理", "更换皮肤");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("主题更换失败");
            logger.logFailed("站点管理", "更换皮肤");
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public int getFilePathFlag() {
        return filePathFlag;
    }

    public void setFilePathFlag(int filePathFlag) {
        this.filePathFlag = filePathFlag;
    }
}
