package com.microwise.blackhole.action.app;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Privilege;

import java.util.Collection;

/**
 * 权限初始化监听
 *
 * @author gaohui
 * @date 13-7-31 08:45
 */
public interface BeforeInitPrivilegesListener {

    /**
     * 在初始化用户权限之前
     *
     * @param privileges 用户拥有的所以权限
     */
    public void beforeInit(Collection<Privilege> privileges, LogicGroup logicGroup);
}
