package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Era;
import com.microwise.orion.dao.EraDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 文物时代dao 实现
 *
 * @author xubaoji
 * @date 2013-5-17
 * @check 2013-06-04 zhangpeng svn:3608
 */
@Dao
@Orion
public class EraDaoImpl extends OrionBaseDao<Era> implements EraDao {

    public EraDaoImpl() {
        super(Era.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Era> findAllEra() {
        String hql = " from Era e ";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public void deleteByName(String name) {
        String hql = "delete from Era e where e.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public boolean isEraExist(String name) {
        String hql = " from Era e where e.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        return query.list().size() > 0;
    }

    @Override
    public int findIdByName(String name) {
        String hql = "select e.id from Era e where e.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        return (Integer)query.list().get(0);
    }
}
