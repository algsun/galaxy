package com.microwise.blackhole.action.logicgroup.current;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.AreaCodeService;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 调整站点结构
 *
 * @author li.jianfei
 * @date 2012-12-03
 * @check @li.jianfei #631 2012-12-05
 */
@Beans.Action
@Blackhole
public class ChangeLogicGroupSiteAndParentAction {
    /**
     * 区域选择 顶级地区id
     */
    private static final int AREA_CODE_ROOT_ID = 0;
    public static final Logger logger = LoggerFactory.getLogger(ChangeLogicGroupSiteAndParentAction.class);
    @Autowired
    private AreaCodeService areaCodeService;
    @Autowired
    private LogicGroupService logicGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    //input
    /**
     * 当前logicgroup id
     */
    private int currentId;
    /**
     * 若绑定site 绑定site id
     */
    private String siteId;

    /**
     * 上级logicgroup id
     */
    private Integer parentId;

    private LogicGroup logicGroup;
    private LogicGroup parentLogicGroup;
    /**
     * 地区(省)
     */
    private List<AreaCodeCN> areaCodeCNs;

    /**
     * 跳转到查看页面
     */
    public String view() {
        try {
            LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();
            logicGroup = logicGroupService.findLogicGroupCarrySite(currentLogicGroup.getId());
            if (currentLogicGroup.getParent() != null) {
                parentLogicGroup = logicGroupService.findLogicGroupById(currentLogicGroup.getParent().getId());
            }
            areaCodeCNs = areaCodeService.findAreaListByAreaCode(AREA_CODE_ROOT_ID);
        } catch (Exception e) {
            logger.error("", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 执行修改
     */
    public String execute() {
        try {
            //如果为 -1 ，则为没有 parentLogicGroup
            parentId = (parentId == -1 ? null : parentId);
            logicGroupService.changeParentLogicGroup(currentId, parentId);
            // 如果 siteId 值有效，则更改绑定的 site
            if (!Strings.isNullOrEmpty(siteId)) {
                logicGroupService.changeSiteOfLogicGroup(currentId, Strings.emptyToNull(siteId));
            }

            ActionMessage.createByAction().success("修改成功").consume();
        } catch (Exception e) {
            ActionMessage.createByAction().fail("修改失败").consume();
            logger.error("站点结构调整", e);
        }

        // 更改好，保持与 session 一致 @gaohui 2012-12-04
        // session 更新当前站点的直接子站点 @gaohui 2012-12-04
        Sessions sessions = new Sessions(ActionContext.getContext());
        logicGroup = sessions.refreshCurrentLogicGroupAndSub(currentId, logicGroupService, userService, logicGroupSubsystemDisableService);
        if (logicGroup.getParent() != null) {
            parentLogicGroup = logicGroupService.findLogicGroupById(logicGroup.getParent().getId());
        }
        areaCodeCNs = areaCodeService.findAreaListByAreaCode(AREA_CODE_ROOT_ID);

        return Action.SUCCESS;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public LogicGroup getParentLogicGroup() {
        return parentLogicGroup;
    }

    public void setParentLogicGroup(LogicGroup parentLogicGroup) {
        this.parentLogicGroup = parentLogicGroup;
    }

    public List<AreaCodeCN> getAreaCodeCNs() {
        return areaCodeCNs;
    }

    public void setAreaCodeCNs(List<AreaCodeCN> areaCodeCNs) {
        this.areaCodeCNs = areaCodeCNs;
    }

    public LogicGroup getLogicGroup() {
        return logicGroup;
    }

    public void setLogicGroup(LogicGroup logicGroup) {
        this.logicGroup = logicGroup;
    }
}
