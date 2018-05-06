package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.InstitutionRoom;
import com.microwise.orion.dao.InstitutionRoomDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by Administrator on 15-9-10.
 */
@Beans.Dao
@Orion
public class InstitutionRoomImpl extends OrionBaseDao<InstitutionRoom> implements InstitutionRoomDao {
    public InstitutionRoomImpl() {
        super(InstitutionRoom.class);
    }

    @Override
    public void add(InstitutionRoom institutionRoom) {
        getSession().save(institutionRoom);
    }

    @Override
    public void deleteInstitutionRoom(InstitutionRoom institutionRoom) {
        getSession().delete(institutionRoom);
    }

    @Override
    public List<InstitutionRoom> findByInstitutionId(int institutionId) {
        String hql = " from InstitutionRoom ir where ir.institutionId = :institutionId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("institutionId", institutionId);
        return query.list();
    }

    @Override
    public void deleteByInstitutionId(int id) {
        String hql = " delete InstitutionRoom ir where ir.institutionId = :institutionId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("institutionId", id);
        query.executeUpdate();
    }
}
