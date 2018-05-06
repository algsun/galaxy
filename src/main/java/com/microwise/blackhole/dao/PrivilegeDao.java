package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.Privilege;

import java.util.List;
import java.util.Set;

/**
 * 权限Dao
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-11-28 xubaoji svn：440
 */
public interface PrivilegeDao {

    /**
     * 查询所有权限 （已根据父权限id和分组序列排序，携带父权限id）
     *
     * @return List<Privilege> 权限列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public List<Privilege> findAll();

    /**
     * 根据角色id查询角色拥有的权限列表 （已根据父权限id和分组序列排序）
     *
     * @param roleId 角色id
     * @return List<Privilege> 权限列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public List<Privilege> findPrivilegeListByRoleId(int roleId);

    /**
     * 根据角色id查询角色拥有的权限列表-携带父权限信息（已根据父权限id和分组序列排序）
     *
     * @param roleId 角色id
     * @return List<Privilege> 携带父权限信息的权限列表
     * @author zhangpeng
     * @date 2012-11-22
     */
    public List<Privilege> findPrivilegesCarryParent(int roleId, String language);

    /**
     * 根据业务系统id查询可选权限（已根据父权限id和分组序列排序）
     *
     * @param subsystemId 业务系统 编号id
     * @param set         可能包含“可选权限类型”的权限状态set
     * @return List<Privilege> 业务系统权限列表
     * @author zhangpeng
     * @date 2012-11-22
     */
    public List<Privilege> findPrivilegeListBySubId(int subsystemId,
                                                    Set<Integer> set);

    /**
     * 根据角色id查询角色拥有的叶子权限（已根据父权限id和分组序列排序）
     *
     * @param roleId 角色id编号
     * @return List<Privilege> 角色叶子权限列表
     * @author zhangpeng
     * @date 2012-11-22
     */
    public List<Privilege> findLeafPrivilegesByRoleId(int roleId);

    /**
     * 根据权限状态set查询权限列表
     *
     * @param set 权限状态set
     * @return List<Privilege> 必选权限列表
     * @author zhangpeng
     * @date 2012-11-28
     */
    public List<Privilege> findPrivilegesByStateSet(Set<Integer> set);

    /**
     * 根据权限状态set查询权限列表获取英文权限名称
     *
     * @param set 权限状态set
     * @return List<Privilege> 必选权限列表
     * @author chenyaofei
     * @date 2016-02-22
     */
    public List<Privilege> findPrivilegesByStateSetByLanguage(Set<Integer> set, String language);

}
