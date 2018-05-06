package com.microwise.blackhole.action.app;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出
 * <p/>
 * Date: 12-11-6 Time: 下午3:35
 *
 * @author bastengao
 * @check @wang.yunlong & li.jianfei #484 2012-12-18
 */
@Beans.Action
@Blackhole
public class LogoutAction {
    private static final Logger log = LoggerFactory.getLogger(LogoutAction.class);
    // 最后一次退出的业务系统
    public static final String COOKIE_NAME_LAST_LOGOUT_FROM = "last_logout_from";
    // cookie 的保留时间(7 天)
    private static final int COOKIE_AGE = 604800;

    @Autowired
    private BlackholeLoggerUtil logger;

    public String execute() {
        logger.log("退出", "退出");
        tagIfUserLogined();
        addLogoutFromCookie();

        log.debug("用户执行退出");

        Subject currentUser = SecurityUtils.getSubject();
        //退出并清理 session
        currentUser.logout();

        log.debug("用户退出");

        return Action.SUCCESS;
    }

    /**
     * 如果用户正常退出, 在 session 中标记已记录"退出"日志
     */
    private void tagIfUserLogined() {
        // 使用 Sessions 公共类来操作 Session ，保持程序中对Session的操作一致
        //正常退出, 在 session 中标记已记录"退出"日志
        User user = new Sessions(ActionContext.getContext()).currentUser();
        if (user != null) {
            ActionContext.getContext().getSession().put(Constants.Session.IS_LOGOUT_LOGGED, true);
        }
    }

    /**
     * 添加退出时所在业务系统 cookie
     */
    private void addLogoutFromCookie() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String logoutFrom = request.getParameter("from");
        if (Strings.isNullOrEmpty(logoutFrom)) {
            return;
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        Cookie userNameCookie = new Cookie(COOKIE_NAME_LAST_LOGOUT_FROM, logoutFrom);
        userNameCookie.setPath(request.getContextPath());
        userNameCookie.setMaxAge(COOKIE_AGE);
        response.addCookie(userNameCookie);
    }
}
