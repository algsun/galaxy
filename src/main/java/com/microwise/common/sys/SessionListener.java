package com.microwise.common.sys;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.SysLog;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * 用户 session 监听器
 *
 * @author bastengao
 * @date 12-11-23 14:00
 * @check @wang.yunlong & li.jianfei   #484   2012-12-18
 */
public class SessionListener implements HttpSessionListener {
    private static final Logger log = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.debug("session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.debug("session destroyed");

        //用户 session 超时后，记录用户退出日志
        recordLogoutIfSessionTimeout(httpSessionEvent);
    }

    /**
     * 记录退出日志, 如果 session 超时
     *
     * @param httpSessionEvent
     */
    private void recordLogoutIfSessionTimeout(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Boolean logged = (Boolean) session.getAttribute(Constants.Session.IS_LOGOUT_LOGGED);
        //查看是否是正常退出，还是超时
        if (logged == null || !logged) {
            log.debug("用户 session 超时");

            User user = (User) session.getAttribute(Constants.Session.USER_OF_SESSION);
            //如果用户没有登录, 则不需要记录日志
            if(user == null){
                return ;
            }

            LogicGroup currentLogicGroup = (LogicGroup) session.getAttribute(Constants.Session.CURRENT_LOGIC_GROUP);
            if(currentLogicGroup == null){
                return ;
            }

            //获取 logService
            LogService logService = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()).getBean(LogService.class);

            SysLog sysLog = new SysLog();
            sysLog.setUserName(user.getUserName());
            sysLog.setUserEmail(user.getEmail());
            sysLog.setLogicGroupId(currentLogicGroup.getId());
            sysLog.setLogicGroupName(currentLogicGroup.getLogicGroupName());
            sysLog.setSubsystemId(Constants.Subsystems.BLACK_HOLE);
            sysLog.setLogTime(new Date());
            sysLog.setLogState(true);
            sysLog.setLogName("退出");
            sysLog.setLogContent("退出");

            logService.saveLog(sysLog);
        }

    }
}
