package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Department;
import com.microwise.blackhole.dao.DepartmentDao;
import com.microwise.blackhole.service.DepartmentService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门service 实现
 *
 * @author xu.baoji
 * @date 2013-8-17
 */
@Blackhole
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    /**
     * 部门dao
     */
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> findAll(int logicGroupId) {
        return departmentDao.findAll(logicGroupId);
    }

}
