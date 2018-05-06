package com.microwise.blackhole.action.profile;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 重置用户个性化参数
 *
 * @author li.jianfei
 * @date 2012-11-19
 * @check @gaohui  #379 2012-11-27
 */

@Beans.Action
@Blackhole
public class ResetIndividualAction {

    /**
     * 用户信息 Service
     */
    @Autowired
    private UserService userService;
    @Autowired
    private BlackholeLoggerUtil logger;

    /**
     * 操作是否成功
     */
    private boolean success;

    /**
     * 反馈消息
     */
    private String message;

    /**
     * 重置个性化参数
     *
     * @return 操作标识
     */
    public String execute() {

        try {
            // 从 session 获取用户信息
            Sessions sessions = new Sessions(ActionContext.getContext());
            User user = sessions.currentUser();

            // 重置用户个性化参数
            userService.resetDefalultSetting(user.getId());

            success = true;
            message = "重置个性化参数成功";
            logger.log("个人信息", "重置个性化参数");
            logger.logCurrentInSubsystem("个人信息", "重置个性化参数", true);
        } catch (Exception ex) {
            success = false;
            message = "重置修改化参数失败";
            logger.logCurrentInSubsystem("个人信息", "重置个性化参数", false);
        }

        return Action.SUCCESS;
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
