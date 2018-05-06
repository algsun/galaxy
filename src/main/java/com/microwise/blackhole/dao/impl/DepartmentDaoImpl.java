package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.Department;
import com.microwise.blackhole.dao.DepartmentDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Query;

import java.util.List;

/**
 * @author xu.baoji
 * @date 2013-8-17
 */
@Dao
@Blackhole
public class DepartmentDaoImpl extends BlackholeBaseDao<Department> implements DepartmentDao {

    public DepartmentDaoImpl() {
        super(Department.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Department> findAll(int logicGroupId) {
        String hql = " from Department d where d.logicGroup.id = :logicGroupId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        return query.list();
    }

    public Department findByName(String name, int logicGroupId) {
        String hql = " from Department d where d.name = :name and d.logicGroup.id = :logicGroupId";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        query.setParameter("logicGroupId", logicGroupId);
        return (Department) query.uniqueResult();
    }


}
