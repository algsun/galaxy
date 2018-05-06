package com.microwise.blackhole.action.app;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
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
 * 忘记密码
 *
 * @author bastengao
 * @date 12-11-20 17:53
 * @check @li.jianfei #343 2012-11-29
 */
@Beans.Action
@Blackhole
public class ForgetPasswordAction {
    private static final Logger log = LoggerFactory.getLogger(ForgetPasswordAction.class);
    /**
     * 忘记密码
     */
    private static final String VERIFY_CODE_NAME = "forget_password";

    @Autowired
    private UserService userService;

    //input
    /**
     * 找回密码邮箱
     */
    private String email;
    /**
     * 邮箱登录地址
     */
    private String emailLoginUrl;
    /**
     * 验证码
     */
    private String verifyCode;

    public String view() {
        return Action.SUCCESS;
    }

    public String execute() {
        try {
            if (Strings.isNullOrEmpty(email)) {
                ActionMessage.createByAction().fail("邮箱不能为空").consume();
                return Action.SUCCESS;
            }

            // 判断用户是否存在
            User user = userService.findUserByEmail(email);
            if (user == null) {
                ActionMessage.createByAction().fail("邮箱不存在").consume();
                return Action.SUCCESS;
            }
            //判断用户是否激活。用户激活和修改密码用的是同一个token，若是用户还没激活就重置密码那么激活的token将会被覆盖，就会导致用户永远无法激活。
            if (!user.isActive()) {
                ActionMessage.createByAction().fail("该用户未激活, 不能重置密码").consume();
                return Action.SUCCESS;
            }

            ActionContext actionContext = ActionContext.getContext();
            String realVerifyCode = VerifyCodeAction.getVerifyCode(actionContext, VERIFY_CODE_NAME);
            try {
                if (!realVerifyCode.equalsIgnoreCase(verifyCode)) {
                    ActionMessage.createByAction().fail("验证码不正确").consume();
                    return Action.SUCCESS;
                }
            } catch (Exception e) {
                ActionMessage.createByAction().fail("验证码不正确").consume();
                return Action.SUCCESS;
            }
            String token = TokenUtil.createToken();
            HttpServletRequest request = ServletActionContext.getRequest();
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/blackhole/resetPassword.action?token=" + token;
            userService.sendResetPasswordEmail(email, token, url);
            emailLoginUrl = EmailUtil.getLoginUrl(email);
            ActionMessage.createByAction().success("邮件已发送").consume();
        } catch (Exception e) {
            log.error("发送邮件", e);
            ActionMessage.createByAction().fail("不好意思，邮件发送失败，请稍后重试。").consume();
        }
        return Action.SUCCESS;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailLoginUrl() {
        return emailLoginUrl;
    }

    public void setEmailLoginUrl(String emailLoginUrl) {
        this.emailLoginUrl = emailLoginUrl;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
