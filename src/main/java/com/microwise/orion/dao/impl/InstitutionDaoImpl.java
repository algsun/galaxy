package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Institution;
import com.microwise.orion.dao.InstitutionDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 单位dao
 *
 * @author liuzhu
 * @date 2015-9-8
 */

@Beans.Dao
@Orion
public class InstitutionDaoImpl extends OrionBaseDao<Institution> implements InstitutionDao {

    public InstitutionDaoImpl() {
        super(Institution.class);
    }

    @Override
    public List<Institution> findBySiteId(String siteId) {
        String hql = " from Institution it where it.siteId = :siteId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        return query.list();
    }

    @Override
    public List<Institution> findInstitutions(String siteId, int institutionType) {
        String hql = " from Institution it where it.siteId = :siteId and institutionType = :institutionType ";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        query.setParameter("institutionType", institutionType);
        return query.list();
    }
}

