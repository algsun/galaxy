package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Role;

import java.util.List;

/**
 * 角色Service
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-12-13 zhangpeng svn:809
 */
public interface RoleService {

    /**
     * 添加角色
     *
     * @param logicGroupId    logicGroup 编号
     * @param roleName        角色名称
     * @param privilegeIdList 权限id列表
     * @author xubaoji
     * @date 2012-11-19
     */
    public void saveRole(int logicGroupId, String roleName,
                         List<String> privilegeIdList);

    /**
     * 判断同一站点下是否存在同名角色
     *
     * @param logicGroupId logicGroup 编号
     * @param roleId       角色编号：0表示添加判断，1表示修改当前角色时的判断
     * @param roleName     角色名称
     * @return boolean true存在；false不存在
     * @author xubaoji
     * @date 2012-11-19
     */
    public boolean hasSameRole(int logicGroupId, int roleId, String roleName);

    /**
     * 根据角色id删除角色
     *
     * @param roleId 角色编号
     * @author xubaoji
     * @date 2012-11-19
     */
    public void deleteRoleById(int roleId);

    /**
     * 修改角色信息
     *
     * @param role            角色对象
     * @param privilegeIdList 角色拥有的权限Id列表
     * @author xubaoji
     * @date 2012-11-19
     */
    public void updateRole(Role role, List<String> privilegeIdList);

    /**
     * 查询当前站点下的角色列表
     *
     * @param logicGroupId 逻辑站点id
     * @param isManger     是否 是管理员 ： true:是管理员；false不是管理员；Null全部
     * @return List<Role> 角色列表
     * @author xubaoji
     * @date 2012-11-19
     */
    public List<Role> findRoleListByLogicGroupId(int logicGroupId,
                                                 Boolean isManger);

    /**
     * 查询当前站点下的角色列表 （分页 查询）
     *
     * @param logicGroupId 逻辑站点id
     * @param roleName     角色名
     * @param stat         当前页
     * @param max          单位
     * @return List<Role> 角色列表
     * @author xubaoji
     * @date 2012-11-21
     */
    public List<Role> findRoleListByLogicGroupId(int logicGroupId,
                                                 String roleName, int stat, int max);

    /**
     * 查询当前站点下的角色数量
     *
     * @param logicGroupId 逻辑站点id
     * @param roleName     角色名
     * @return Integer 当前logicGroup 下 角色总记录数
     * @author xubaoji
     * @date 2012-11-21
     */
    public Integer findRoleCountByLogicGroupId(int logicGroupId, String roleName);

    /**
     * 根据用户编号查询角色
     *
     * @param userId 用户编号
     * @return List<Role> 角色列表
     */
    public List<Role> findRoleListByUserId(int userId);

    /**
     * 通过角色id查询角色
     *
     * @param id 角色编号
     * @return role 角色实体对象
     * @author 许保吉
     * @date 2012-11-22
     */
    public Role findRoleById(int id);

    /**
     * 查找匿名用户角色并带权限, 不存在返回 null
     *
     * @return 用户角色
     */
    public Role findAnonymityRole();
}
