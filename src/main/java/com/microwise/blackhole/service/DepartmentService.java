package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Department;

import java.util.List;

/**
 * 部门接口 service
 *
 * @author xu.baoji
 * @date 2013-8-17
 */
public interface DepartmentService {

    /**
     * 查询所有部门
     *
     * @param logicGroupId 逻辑站点编号
     * @return List<Department> 站点下所有部门
     * @author xu.baoji
     * @date 2013-8-17
     */
    public List<Department> findAll(int logicGroupId);

}
