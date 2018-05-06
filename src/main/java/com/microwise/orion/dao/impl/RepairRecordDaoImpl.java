package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairRecord;
import com.microwise.orion.dao.RepairRecordDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by Administrator on 15-9-14.
 */

@Beans.Dao
@Orion
public class RepairRecordDaoImpl extends OrionBaseDao<RepairRecord> implements RepairRecordDao {
    public RepairRecordDaoImpl() {
        super(RepairRecord.class);
    }

    public RepairRecord lastRepairRecord() {
        String hql = " from RepairRecord rr order by id desc";
        Query query = getSession().createQuery(hql);
        return (RepairRecord) query.setMaxResults(1).uniqueResult();
    }

    @Override
    public void saveOrUpdate(RepairRecord repairRecord) {
        getSession().saveOrUpdate(repairRecord);
    }

    @Override
    public List<RepairRecord> findBySiteId(String siteId) {
        String hql = " from RepairRecord rr join fetch rr.repairReason where rr.siteId = :siteId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        return query.list();
    }

    @Override
    public List<RepairRecord> findRepairRecords(int index, int pageCount, String siteId) {
        String hql = " from RepairRecord rr join fetch rr.repairReason where rr.siteId = :siteId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        query.setFirstResult((index - 1) * pageCount);
        query.setMaxResults(pageCount);
        return query.list();
    }

    @Override
    public RepairRecord findRepairRecordById(int id) {
        String hql = " from RepairRecord rr join fetch rr.repairReason where rr.id = :id ";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);
        return (RepairRecord)query.uniqueResult();
    }


}
