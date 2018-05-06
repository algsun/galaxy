package com.microwise.common.sys.freemarker.shiro;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.common.sys.Constants;
import org.apache.shiro.subject.Subject;

import java.util.Set;

/**
 * 工具类
 *
 * @author gaohui
 * @date 12-11-28 18:26
 */
public class Helper {


    /**
     * 是否是访客
     *
     * @param currentUser
     * @return
     */
    public static boolean isGuest(Subject currentUser) {
        //用户是否在归属站点
        if(atHomeLogicGroup(currentUser)){
            return false;
        }

        return true;
    }

    /**
     * 用户是否在归属站点
     *
     * 注意: 超级管理员总是返回 true, 换句话说任何站点都是超级管理员的归属站点
     * 匿名用户同上
     *
     * @param currentUser
     * @return
     */
    public static boolean atHomeLogicGroup(Subject currentUser) {
        if(isAnonymity(currentUser)){
            return true;
        }

        if(isSuperman(currentUser)){
            return true;
        }

        //用户归属站点
        LogicGroup userLogicGroup = (LogicGroup) currentUser.getSession().getAttribute(Constants.Session.USER_LOGIC_GROUP);
        if (userLogicGroup == null) {
            return false;
        }

        //当前站点
        LogicGroup currentLogicGroup = (LogicGroup) currentUser.getSession().getAttribute(Constants.Session.CURRENT_LOGIC_GROUP);
        if (currentLogicGroup == null) {
            return false;
        }

        //判断当前站点是否是归属站点
        if (userLogicGroup.getId() == currentLogicGroup.getId()) {
            return true;
        }

        return false;
    }


    /**
     * 访客是否有某个权限
     *
     * @param subject
     * @param permission
     * @return
     */
    public static boolean isGuestPermitted(Subject subject, String permission) {
        Set<String> privileges = (Set<String>) subject.getSession().getAttribute(Constants.Session.GUEST_PRIVILEGES);
        return privileges.contains(permission);
    }


    /**
     * 是否是超级管理员
     *
     * @param currentUser
     * @return
     */
    public static boolean isSuperman(Subject currentUser){
        return currentUser.hasRole(com.microwise.common.sys.Constants.Roles.SUPERMAN);
    }

    /**
     * 是否是匿名用户
     *
     * @param currentUser
     * @return
     */
    public static boolean isAnonymity(Subject currentUser){
        return currentUser.hasRole(com.microwise.common.sys.Constants.Roles.ANONYMITY);
    }
}
