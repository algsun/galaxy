package com.microwise.blackhole.proxy;

import com.microwise.blackhole.bean.User;

import java.util.List;

/**
 * 用户信息代理层. User: xiedeng Date: 13-5-28
 */
public interface UserProxy {

    /**
     * 分页查询站点下 已经激活的可用的用户列表
     *
     * @param logicGroupId 用户组ID
     * @param userName     用户名（模糊）
     * @param index        当前页码
     * @param max          分页单位
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013-6-17
     */
    public List<User> findUserList(int logicGroupId, String userName,
                                   int index, int max);

    /**
     * 查询 站点下 已激活的可用 用户的数量
     *
     * @param logicGroupId 站点编号
     * @param userName     用户名（模糊）
     * @return integer 数量
     * @author 许保吉
     * @date 2013-6-18
     */
    public Integer findUserCount(int logicGroupId, String userName);

    /**
     * 查询站点下 已经激活的可用的用户列表
     *
     * @param logicGroupId 用户组ID
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013-6-17
     */
    public List<User> findUserList(int logicGroupId);

}
