package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Scheme;
import com.microwise.orion.dao.SchemeDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * @author 王耕
 * @date 15-9-9
 */
@Orion
@Beans.Dao
public class SchemeDaoImpl extends OrionBaseDao<Scheme> implements SchemeDao {

    public SchemeDaoImpl() {
        super(Scheme.class);
    }

    @Override
    public void saveOrUpdateScheme(Scheme scheme){
        getSession().saveOrUpdate(scheme);
    }

    @Override
    public void deleteScheme(int id) {
        getSession().delete(new Scheme(id));
    }

    @Override
    public List<Scheme> findAll(String siteId) {
        String hql = "select s from Scheme as s join fetch s.institution where s.siteId= :siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId",siteId);
        return query.list();
    }

    @Override
    public Scheme findSchemeById(int id){
        // TODO super.findById()
        String hql = "from Scheme s where s.id=:id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id",id);
        return (Scheme)query.uniqueResult();
    }
}
