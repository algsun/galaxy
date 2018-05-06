package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Level;
import com.microwise.orion.dao.LevelDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 文物级别dao 实现
 *
 * @author xubaoji
 * @date 2013-5-17
 * @check 2013-06-04 zhangpeng svn:3608
 */
@Dao
@Orion
public class LevelDaoImpl extends OrionBaseDao<Level> implements LevelDao {

    public LevelDaoImpl() {
        super(Level.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Level> findAllLevel() {
        String hql = " from Level l";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public void deleteByName(String name) {
        String hql = "delete from Level l where l.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public boolean isLevelExist(String name) {
        String hql = " from Level l where l.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        return query.list().size() > 0;
    }

    @Override
    public int findIdByName(String name) {
        String hql = "select l.id from Level l where l.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        return (Integer)query.list().get(0);
    }
}
