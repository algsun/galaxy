package com.microwise.blackhole.action.user;

import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除或禁用/启用用户
 *
 * @author li.jianfei
 * @date 2012-11-22
 * @check wang.yunlong #376 12-11-49 09:49
 * @check @gaohui #472 2012-11-29 16:25
 */

@Beans.Action
@Blackhole
public class EditUserAction {

    public static final Logger log = LoggerFactory.getLogger(EditUserAction.class);

    /**
     * 用户信息 Service
     */
    @Autowired
    private UserService userService;

    @Autowired
    private BlackholeLoggerUtil logger;

    // Input
    /**
     * 用户id
     */
    private int userId;

    /**
     * 删除用户
     *
     * @return 操作标识
     */
    public String delete() {

        try {
            userService.deleteUserById(userId);
            ActionMessage.createByAction().success("删除用户成功");
            logger.log("用户管理", "删除用户");
        } catch (Exception ex) {
            ActionMessage.createByAction().fail("删除用户失败");
            logger.logFailed("用户管理", "删除用户");
            log.error("", ex);
        }

        return Action.SUCCESS;
    }


    /**
     * 禁用/启用用户
     *
     * @return 操作标识
     */
    //暂时有问题 @gaohui 2012-12-10
    //@RequiresPermissions({"blackhole:user:disable"})
    public String execute() {

        try {
            userService.changeUserDisableState(userId);
            MessageActionUtil.success("修改用户状态成功");
            logger.log("用户管理", "修改用户状态-禁用");
        } catch (Exception ex) {
            MessageActionUtil.fail("修改用户状态失败");
            logger.logFailed("用户管理", "修改用户状态");
            log.error("", ex);
        }

        return Action.SUCCESS;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
