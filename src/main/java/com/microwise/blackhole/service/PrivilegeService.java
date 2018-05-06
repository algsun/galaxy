package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Privilege;

import java.util.List;

/**
 * 权限Service
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-11-28 xubaoji svn:440
 */
public interface PrivilegeService {

    /**
     * 查询除必选权限外的所有权限 （已根据父权限id和分组序列排序，携带父权限id）
     *
     * @return List<Privilege> 权限列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public List<Privilege> findAll();

    /**
     * 根据角色id查询角色拥有的权限列表 （已根据父权限id和分组序列排序，携带父权限id）
     *
     * @param roleId 角色id
     * @return List<Privilege> 权限列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public List<Privilege> findPrivilegeListByRoleId(int roleId);

    /**
     * 根据角色id查询角色拥有的权限列表-携带父权限信息（已根据父权限id和分组序列排序，携带父权限id）
     *
     * @param roleId 角色id
     * @return List<Privilege> 携带父权限信息的权限列表
     * @author zhangpeng
     * @date 2012-11-22
     */
    public List<Privilege> findPrivilegesCarryParent(int roleId,String language);

    /**
     * 根据业务系统id查询可选权限（已根据父权限id和分组序列排序，携带父权限id）
     *
     * @param subsystemId 业务系统 id
     * @return List<Privilege> 业务系统权限列表
     * @author zhangpeng
     * @date 2012-11-22
     */
    public List<Privilege> findPrivilegeListBySubId(int subsystemId);

    /**
     * 根据角色id查询角色拥有的叶子权限（已根据父权限id和分组序列排序，携带父权限id）
     *
     * @param roleId 角色id
     * @return List<Privilege> 角色叶子权限列表
     * @author zhangpeng
     * @date 2012-11-22
     */
    public List<Privilege> findLeafPrivilegesByRoleId(int roleId);

    /**
     * 查询超级管理员权限列表（已根据父权限id和分组序列排序，携带父权限id）
     *
     * @return List<Privilege> 超级管理员权限列表
     * @author zhangpeng
     * @date 2012-11-28
     */
    public List<Privilege> findSupermanPrivileges(String language);

    /**
     * 查询站点管理员权限列表（已根据父权限id和分组序列排序，携带父权限id）
     *
     * @return List<Privilege> 站点管理员权限列表
     * @author zhangpeng
     * @date 2012-11-28
     */
    public List<Privilege> findManagerPrivileges(String language);

    /**
     * 查询访客权限列表（已根据父权限id和分组序列排序，携带父权限id）
     *
     * @return List<Privilege> 访客权限列表
     * @author zhangpeng
     * @date 2012-11-28
     */
    public List<Privilege> findGuestPrivileges(String language);

    /**
     * 查询必选权限（已根据父权限id和分组序列排序，携带父权限id）
     *
     * @return List<Privilege> 必选权限列表
     * @author zhangpeng
     * @date 2012-11-28
     */
    public List<Privilege> findRequiredPrivileges(String language);

    /**
     * 查询除必选权限外的所有权限英文名 （已根据父权限id和分组序列排序，携带父权限id）
     *
     * @return List<Privilege> 权限列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public List<Privilege> findRequiredPrivilegesByLanguage(String language);

}
