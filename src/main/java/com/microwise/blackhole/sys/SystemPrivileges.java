package com.microwise.blackhole.sys;

import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.common.sys.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <pre>
 *     初始化加载系统的各种权限
 * </pre>
 *
 * @author Wang yunlong
 * @date 12-11-22
 */
public class SystemPrivileges {

    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 访问权限(用户在其他站点的权限)
     */
    private static List<Privilege> guestPrivileges;

    /**
     * 系统管理权限
     */
    private static List<Privilege> blackholePrivileges;


    /**
     * 所有权限
     */
    public static List<Privilege> privileges;


    /**
     * <p/>
     * 获取系统管理的权限
     *
     * @return
     */
    public static List<Privilege> getBlackholePrivileges() {
        return blackholePrivileges;
    }

    public static List<Privilege> getPrivileges() {
        return privileges;
    }

    /**
     * 返回访客权限(用户在其他站点的权限)
     *
     * @return
     */
    public static List<Privilege> getGuestPrivileges() {
        return guestPrivileges;
    }

    /**
     * 系统启动 默认初始化函数
     */
    private void init() {
        guestPrivileges = privilegeService.findGuestPrivileges("en_US");
        blackholePrivileges = privilegeService.findPrivilegeListBySubId(Constants.Subsystems.BLACK_HOLE);
        privileges = privilegeService.findAll();
    }
}
