package com.microwise.blackhole.service;

import java.util.List;

/**
 * 站点操作权限service
 *
 * @author liuzhu
 * @date 2014/3/31
 */
public interface PrivilegeOperateService {

    /**
     * 禁用权限
     *
     * @param privilegeList 权限idList
     * @param logicGroupId  站点组id
     * @author liuzhu
     * @date 2014-3-31
     */
    public void disable(List<String> privilegeList, int logicGroupId);

    /**
     * 根据权限id查询是否被禁用
     *
     * @param privilegeId 权限id
     * @author liuzhu
     * @date 2014-3-31
     */
    public boolean isDisableById(String privilegeId, int logicGroupId);

    /**
     * 根据站点组id清除禁用的权限
     *
     * @param logicGroupId 站点组id
     * @author liuzhu
     * @date 2014-3-31
     */
    public void clearDisable(int logicGroupId);

    /**
     * 根据logicGroupId查找禁用的业务系统
     *
     * @return 业务系统名称集合
     * @author liuzhu
     * @date 2014-4-1
     */
    public List<String> findDisableSubsystemId(int logicGroupId);

    /**
     * 根据站点组获取所有禁用的权限
     *
     * @param logicGroupId
     * @return
     */
    public List<String> findDisablePrivilegeId(int logicGroupId);
}
