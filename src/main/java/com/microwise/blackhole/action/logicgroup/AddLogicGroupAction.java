package com.microwise.blackhole.action.logicgroup;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.EmailUtil;
import com.microwise.common.util.TokenUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 添加站点action
 * </pre>
 *
 * @author: Wang yunlong
 * @time: 12-11-30 下午1:42
 * @check @li.jianfei #620 2012-12-05
 */

@Beans.Action
@Blackhole
public class AddLogicGroupAction {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AddLogicGroupAction.class);
    @Autowired
    private LogicGroupService logicGroupService;
    @Autowired
    private BlackholeLoggerUtil logger;
    /**
     * logic group名称
     */
    private String name;

    /**
     * 站点管理员邮箱
     */
    private String managerEmail;
    /**
     * 物理站点id
     */
    private String siteId;
    /**
     * 是否创建的是基层站点
     */
    private boolean baseSite;

    /**
     * 邮箱登陆地址
     */
    private String emailLoginUrl;

    public String view() {
        initIsAtSupermanPage(ActionContext.getContext());
        return Action.SUCCESS;
    }

    public String execute() {
        try {
            int currentLogicGroupId = -1;
            LogicGroup logicGroup = Sessions.createByAction().currentLogicGroup();
            if (logicGroup != null) {
                currentLogicGroupId = logicGroup.getId();
            }
            String token = TokenUtil.createToken();
            HttpServletRequest request = ServletActionContext.getRequest();
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/blackhole/activeUser.action?token=" + token;
            emailLoginUrl = EmailUtil.getLoginUrl(managerEmail);
            if (baseSite) {
                logicGroupService.saveLogicGroup(name, managerEmail, currentLogicGroupId, siteId, token, url);
            } else {
                logicGroupService.saveLogicGroup(name, managerEmail, currentLogicGroupId, token, url);
            }
            ActionMessage.createByAction().success("添加成功");
            logger.log("站点管理", "添加站点");
            return Action.SUCCESS;
        } catch (Exception e) {
            ActionMessage.createByAction().fail("添加失败");
            logger.logFailed("站点管理", "添加站点");
            log.error("", e);
            return Action.ERROR;
        }
    }

    /**
     *
     * @param actionContext
     */
    public static void initIsAtSupermanPage(ActionContext actionContext) {
        LogicGroup currentLogicGroup = new Sessions(actionContext).currentLogicGroup();
        if (currentLogicGroup != null) {
            actionContext.put("atSupermanPage", false);
        } else {
            actionContext.put("atSupermanPage", true);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public boolean isBaseSite() {
        return baseSite;
    }

    public void setBaseSite(boolean baseSite) {
        this.baseSite = baseSite;
    }

    public String getEmailLoginUrl() {
        return emailLoginUrl;
    }

    public void setEmailLoginUrl(String emailLoginUrl) {
        this.emailLoginUrl = emailLoginUrl;
    }
}
