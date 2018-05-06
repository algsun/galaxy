package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.DrawRegister;
import com.microwise.orion.dao.DrawRegisterDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by Administrator on 15-9-23.
 */
@Beans.Dao
@Orion
public class DrawRegisterDaoImpl extends OrionBaseDao<DrawRegister> implements DrawRegisterDao{

    public DrawRegisterDaoImpl() {
        super(DrawRegister.class);
    }
    @Override
    public List<DrawRegister> findDrawRegisters(int repairRecordId) {
        String hql = " from DrawRegister dr join fetch dr.drawingPerson where dr.repairRecord.id = :repairRecordId order by dr.id DESC";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId", repairRecordId);
        return query.list();
    }

    @Override
    public DrawRegister findNewDrawRegister(int repairRecordId) {
        String hql = " from DrawRegister dr join fetch dr.repairRecord WHERE dr.repairRecord.id = :repairRecordId  order by dr.id DESC ";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId", repairRecordId);
        query.setMaxResults(1);
        return (DrawRegister)query.uniqueResult();
    }

    @Override
    public DrawRegister findDrawRegister(int id) {
        String hql = " from DrawRegister dr join fetch dr.repairRecord where dr.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);
        return (DrawRegister)query.uniqueResult();
    }
}
