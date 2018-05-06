package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.HealthEvaluation;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.dao.HealthEvaluationDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Hibernate;
import org.hibernate.Query;


/**
 * 文物健康评测daoImpl
 *
 * @author bai.weixing
 * @since 2017/7/4.
 */
@Beans.Dao
@Orion
public class HealthEvaluationDaoImpl extends OrionBaseDao<HealthEvaluation> implements HealthEvaluationDao {
    public HealthEvaluationDaoImpl() {
        super(HealthEvaluation.class);
    }

    @Override
    public Relic findHealthEvaluations(int relicId) {
        String hql = " from Relic r left outer join fetch r.healthEvaluations left outer join fetch r.zone where r.id  = :relicId";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        Relic relic = (Relic) query.uniqueResult();
        // 强制加载文物relic实体中属性和集合
        if (relic != null) {
            Hibernate.initialize(relic.getStatusQuos());
        }
        return relic;
    }

    @Override
    public HealthEvaluation find(int id) {

        String hql = " from HealthEvaluation h join fetch h.relic where h.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);
        HealthEvaluation healthEvaluation =(HealthEvaluation) query.uniqueResult();
        // 强制加载文物relic实体中属性和集合
        Relic relic = healthEvaluation.getRelic();
        if (relic != null) {
            Hibernate.initialize(relic.getStatusQuos());
            Hibernate.initialize(relic.getZone());
        }
        return healthEvaluation;
    }

}
