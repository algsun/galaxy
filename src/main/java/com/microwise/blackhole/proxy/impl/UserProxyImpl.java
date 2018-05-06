package com.microwise.blackhole.proxy.impl;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.proxy.UserProxy;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户信息代理层. User: xiedeng Date: 13-5-28
 */

@Component
@Scope("prototype")
@Blackhole
public class UserProxyImpl implements UserProxy {

    @Autowired
    private UserService userService;

    @Override
    public List<User> findUserList(int logicGroupId, String userName,
                                   int index, int max) {
        return userService.findUserLists(logicGroupId, userName, index, max);
    }

    @Override
    public Integer findUserCount(int logicGroupId, String userName) {
        return userService.findUserCount(logicGroupId, userName);
    }

    @Override
    public List<User> findUserList(int logicGroupId) {
        return userService.findUserLists(logicGroupId);
    }
}
