package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Appraisal;
import com.microwise.orion.dao.AppraisalDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 文物鉴定 信息 dao 实现
 *
 * @author xubaoji
 * @date 2013-5-16
 * @check 2013-06-05 zhangpeng svn:4075
 */
@Dao
@Orion
public class AppraisalDaoImpl extends OrionBaseDao<Appraisal> implements
        AppraisalDao {

    public AppraisalDaoImpl() {
        super(Appraisal.class);
    }

    @Override
    public Appraisal findLatestAppraisal(Integer relicId) {
        String hql = "from Appraisal  a  where  a.relic.id = :relicId order by a.appraisalDate desc";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        query.setMaxResults(1);
        return (Appraisal) query.uniqueResult();
    }
}
