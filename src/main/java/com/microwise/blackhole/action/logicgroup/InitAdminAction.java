package com.microwise.blackhole.action.logicgroup;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.TokenUtil;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 初始化管理员
 *
 * @author li.jianfei
 * @date 2012-11-30
 * @check @wang.yunlong #593 2012-12-05
 */
@Beans.Action
@Blackhole
public class InitAdminAction {

    public static final Logger log = LoggerFactory
            .getLogger(InitAdminAction.class);

    /**
     * 用户信息 service
     */
    @Autowired
    private UserService userService;

    /**
     * 站点组信息 service
     */
    @Autowired
    private LogicGroupService logicGroupService;

    @Autowired
    private BlackholeLoggerUtil logger;

    // Input
    /**
     * 站点组id
     */
    private int logicGroupId;

    /**
     * 邮箱
     */
    private String email;

    // Output
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 反馈消息
     */
    private String message;

    /**
     * 初始化管理员
     *
     * @return 操作标识
     * @author li.jianfei
     * @date 2012-11-30
     */
    public String execute() {

        String token;
        email = email.toLowerCase();
        try {
            User user = userService.findActiveUserByEmail(email);
            if (user != null) {
                success = false;
                message = "邮箱已被使用";
                logger.logFailed("站点管理", "初始化管理员");
                return Action.SUCCESS;
            }

            token = TokenUtil.createToken();
            // 用户激活连接
            HttpServletRequest request = ServletActionContext.getRequest();
            String url = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath()
                    + "/" + Constants.Blackhole.USER_ACTIVATE_ACTION + "?token=" + token;
            userService.deleteUserByLogicGroupId(logicGroupId);
            logicGroupService.initAdminToLogicGroup(email, logicGroupId, token,
                    url);
            success = true;
            message = "初始化管理员邮件发送成功";
            logger.log("站点管理", "初始化管理员");
        } catch (Exception e) {
            success = false;
            message = "初始化管理员邮件发送失败";
            logger.logFailed("站点管理", "初始化管理员");
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
