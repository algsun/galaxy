package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairAssessment;
import com.microwise.orion.dao.RepairAssessmentDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * Created by Administrator on 15-9-23.
 */
@Beans.Dao
@Orion
public class RepairAssessmentDaoImpl extends OrionBaseDao<RepairAssessment> implements RepairAssessmentDao {

    public RepairAssessmentDaoImpl() {
        super(RepairAssessment.class);
    }

    @Override
    public RepairAssessment findByRepairRecordId(int repairRecordId) {
        String hql = " from RepairAssessment r where r.repairRecord.id=:repairRecordId";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId", repairRecordId);
        return (RepairAssessment) query.uniqueResult();
    }

    @Override
    public void delete(int repairRecordId) {
        String hql = " Delete RepairAssessment r where r.repairRecord.id=:repairRecordId";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId", repairRecordId);
        query.executeUpdate();
    }
}
