package com.microwise.blackhole.action.logicgroup.current;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.User;
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

import java.util.List;

/**
 * <pre>
 *     logicgroup信息
 * </pre>
 *
 * @author: Wang yunlong
 * @time: 12-12-3 上午11:12
 * @check @li.jianfei   #637 2012-12-05
 */
@Beans.Action
@Blackhole
public class CurrentLogicGroupInfoAction {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(CurrentLogicGroupInfoAction.class);
    @Autowired
    private LogicGroupService logicGroupService;

    @Autowired
    private BlackholeLoggerUtil logger;
    /**
     * logicGroupId
     */
    private int id;

    /**
     * logicGroup名称
     */
    private String logicGroupName;


    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 地址
     */
    private String orgAddress;

    /**
     * 邮编
     */
    private String orgZipcode;

    /**
     * 网址
     */
    private String orgWebsite;

    /**
     * 联系电话
     */
    private String orgTel;

    /**
     * 传真
     */
    private String orgFax;

    //output
    /**
     * 是否可删除. 默认不可删除
     */
    private boolean canBeDelete = false;

    /**
     * 站点
     */
    private LogicGroup logicGroup;

    public String view() {
        try {
            int currentLogicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            logicGroup = logicGroupService.findLogicGroupCarrySite(currentLogicGroupId);

            // 查询当前站点子站点
            List<LogicGroup> subLogicGroupList = logicGroupService.findSubLogicGroupList(currentLogicGroupId);

            // 未激活在站点均可删除
            if (logicGroup.getActiveState() != LogicGroup.LOGICGROUP_IS_ACTIVESTATE) {
                canBeDelete = true;
            }
            // 没有绑定 site 并且没有子站点的站点可被删除
            else if (logicGroup.getSite() == null && subLogicGroupList.size() <= 0) {
                canBeDelete = true;
            }

        } catch (Exception e) {
            logicGroup = new LogicGroup();
        }
        return Action.SUCCESS;
    }

    public String execute() {
        int currentLogicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        User currentUser = Sessions.createByAction().currentUser();
        LogicGroup logicGroup = Sessions.createByAction().currentLogicGroup();
        logicGroup.setId(id);
        logicGroup.setLogicGroupName(logicGroupName);
        logicGroup.setOrgCode(Strings.emptyToNull(orgCode));
        logicGroup.setOrgAddress(Strings.emptyToNull(orgAddress));
        logicGroup.setOrgFax(Strings.emptyToNull(orgFax));
        logicGroup.setOrgTel(Strings.emptyToNull(orgTel));
        logicGroup.setOrgWebsite(Strings.emptyToNull(orgWebsite));
        logicGroup.setOrgZipcode(Strings.emptyToNull(orgZipcode));
        try {
            logicGroupService.updateLogicGroup(logicGroup);
            this.logicGroup = logicGroupService.findLogicGroupCarrySite(currentLogicGroupId);
            Sessions.createByAction().setCurrentLogicGroup(this.logicGroup);
            logger.log("站点编辑", currentUser.getUserName() + "编辑所在站点信息");
            ActionMessage.createByAction().success("修改成功").consume();
        } catch (Exception e) {
            logger.logFailed("站点编辑", currentUser.getUserName() + "编辑所在站点信息");
            ActionMessage.createByAction().fail("修改失败").consume();
            log.error("站点编辑", e);
        }
        return Action.SUCCESS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogicGroupName() {
        return logicGroupName;
    }

    public void setLogicGroupName(String logicGroupName) {
        this.logicGroupName = logicGroupName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgZipcode() {
        return orgZipcode;
    }

    public void setOrgZipcode(String orgZipcode) {
        this.orgZipcode = orgZipcode;
    }

    public String getOrgWebsite() {
        return orgWebsite;
    }

    public void setOrgWebsite(String orgWebsite) {
        this.orgWebsite = orgWebsite;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public LogicGroup getLogicGroup() {
        return logicGroup;
    }

    public void setLogicGroup(LogicGroup logicGroup) {
        this.logicGroup = logicGroup;
    }

    public boolean isCanBeDelete() {
        return canBeDelete;
    }

    public void setCanBeDelete(boolean canBeDelete) {
        this.canBeDelete = canBeDelete;
    }
}
