package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.Department;
import com.microwise.common.sys.hibernate.BaseDao;

import java.util.List;

/**
 * 部门dao
 *
 * @author xu.baoji
 * @date 2013-8-17
 */
public interface DepartmentDao extends BaseDao<Department> {

    /**
     * 查询所有部门
     *
     * @param logicGroupId 逻辑站点编号
     * @return List<Department> 站点下所有部门
     * @author xu.baoji
     * @date 2013-8-17
     */
    public List<Department> findAll(int logicGroupId);

    /**
     * 通过部门名称查询部门
     *
     * @param name         部门名称
     * @param logicGroupId 逻辑站点编号
     * @return Department 部门实体
     * @author xu.baoji
     * @date 2013-8-19
     */
    public Department findByName(String name, int logicGroupId);
}
