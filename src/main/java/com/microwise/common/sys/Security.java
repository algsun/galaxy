package com.microwise.common.sys;

import com.microwise.common.sys.freemarker.shiro.Helper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


/**
 * 安全工具类
 * <p/>
 * TODO 补充其他权限判断方法 @gaohui 2013-05-15
 *
 * @author gaohui
 * @date 13-5-14 14:47
 */
public class Security {

    /**
     * 当前用户是否是匿名用户
     *
     * @return
     */
    public static boolean isAnonymity() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.hasRole(Constants.Roles.ANONYMITY);
    }

    /**
     * 是否是超级管理员
     *
     * @return
     */
    public static boolean isSuperman() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.hasRole(Constants.Roles.SUPERMAN);
    }

    /**
     * 是否是站点管理员
     *
     * @return
     */
    public static boolean isSiteManager() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.hasRole(Constants.Roles.SITE_MANAGER);
    }

    /**
     * 是否是访客(用户切换到其他站点时，当前站点不是归属站点，对于当前站点该用户为访客)
     *
     * @return
     */
    public static boolean isGuest() {
        return Helper.isGuest(SecurityUtils.getSubject());
    }
}
