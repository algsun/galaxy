package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairPhoto;
import com.microwise.orion.dao.RepairPhotoDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * @author 王耕
 * @date 15-10-22
 */
@Beans.Dao
@Orion
public class RepairPhotoDaoImpl extends OrionBaseDao<RepairPhoto> implements RepairPhotoDao {
    public RepairPhotoDaoImpl(){
        super(RepairPhoto.class);
    }

    @Override
    public void saveRepairPhoto(RepairPhoto repairPhoto) {
        getSession().save(repairPhoto);
    }

    @Override
    public List<RepairPhoto> findByRepairRecordId(int repairRecordId,int rtype) {
        String hql = "from RepairPhoto r where r.repairRecord.id = :repairRecordId and r.type = :rtype";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId",repairRecordId);
        query.setParameter("rtype",rtype);
        return query.list();
    }

    @Override
    public void deleteById(int id) {
        RepairPhoto r = new RepairPhoto();
        r.setId(id);
        getSession().delete(r);
    }

    @Override
    public RepairPhoto findById(int id) {
        return super.findById(id);
    }
}
