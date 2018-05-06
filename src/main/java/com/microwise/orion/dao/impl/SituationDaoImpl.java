package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Situation;
import com.microwise.orion.dao.SituationDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 修复情况DAO
 *
 * @author 王耕
 * @date 15-9-24
 */
@Orion
@Beans.Dao
public class SituationDaoImpl extends OrionBaseDao<Situation> implements SituationDao {

    public SituationDaoImpl() {
        super(Situation.class);
    }

    @Override
    public Situation findByRepairRecordId(int repairRecordId) {
        String hql = "select s from Situation as s where s.repairRecord.id = :repairRecordId";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId", repairRecordId);
        return (Situation) query.uniqueResult();
    }

    @Override
    public void saveOrUpdate(Situation situation) {
        getSession().saveOrUpdate(situation);
    }
}
