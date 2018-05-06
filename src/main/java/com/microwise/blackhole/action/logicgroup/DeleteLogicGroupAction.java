package com.microwise.blackhole.action.logicgroup;

import com.microwise.blackhole.action.app.VerifyCodeAction;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除站点组
 *
 * @author li.jianfei
 * @date 2012-12-3
 * @check @wang.yunlong #568 2012-12-05
 */
@Beans.Action
@Blackhole
public class DeleteLogicGroupAction {

    public static final Logger log = LoggerFactory.getLogger(DeleteLogicGroupAction.class);

    @Autowired
    private BlackholeLoggerUtil logger;
    // 验证码名称
    public static final String VERIFY_CODE_NAME = "deleteLogicGroup";

    /**
     * 站点组信息 service
     */
    @Autowired
    private LogicGroupService logicGroupService;

    // Input
    /**
     * 站点组id
     */
    private int logicGroupId;

    /**
     * 验证码
     */
    private String verifyCode;

    // Output
    /**
     * 操作是否成功
     */
    private boolean success;

    /**
     * 反馈消息
     */
    private String message;


    /**
     * 删除站点组
     *
     * @return 操作标识
     */
    public String execute() {
        try {

            ActionContext actionContext = ActionContext.getContext();

            if (VerifyCodeAction.hasVerifyCode(actionContext, VERIFY_CODE_NAME)) {
                String realVerifyCode = VerifyCodeAction.getVerifyCode(actionContext, VERIFY_CODE_NAME);
                if (!realVerifyCode.equalsIgnoreCase(verifyCode)) {
                    success = false;
                    message = "验证码不正确";
                    return Action.SUCCESS;
                }
            }
            logicGroupService.deleteLogicGroup(logicGroupId);
            success = true;
            message = "删除站点成功";
            logger.log("站点管理", "删除站点");
        } catch (Exception e) {
            success = false;
            message = "删除站点失败";
            logger.logFailed("站点管理", "删除站点");
            log.error("", e);
            return Action.SUCCESS;
        }

        return Action.SUCCESS;

    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
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

