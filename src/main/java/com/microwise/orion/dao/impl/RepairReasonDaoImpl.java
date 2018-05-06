package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairReason;
import com.microwise.orion.dao.RepairReasonDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * @author 王耕
 * @date 15-9-9
 */
@Beans.Dao
@Orion
public class RepairReasonDaoImpl extends OrionBaseDao<RepairReason> implements RepairReasonDao {

    public RepairReasonDaoImpl() {
        super(RepairReason.class);
    }

    @Override
    public void saveOrUpdateRepairReason(RepairReason repairReason) {
        getSession().saveOrUpdate(repairReason);
    }

    @Override
    public void deleteRepairReason(int id) {
        getSession().delete(new RepairReason(id));
    }

    @Override
    public List<RepairReason> findAll(String siteId) {
        String hql = " from RepairReason r where r.siteId=:siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        return query.list();
    }

    @Override
    public RepairReason findReasonById(int id) {
        // TODO super.findById()
        String hql = "from RepairReason r where r.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);
        return (RepairReason) query.uniqueResult();
    }

    @Override
    public RepairReason findReasonByReason(String reason, int id) {
        String hql = "from RepairReason r where r.reason=:reason and r.id!=:id";
        Query query = getSession().createQuery(hql);
        query.setParameter("reason", reason);
        query.setParameter("id", id);
        return (RepairReason) query.uniqueResult();
    }
}
