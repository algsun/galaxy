package com.microwise.cybertron.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Volume;
import com.microwise.cybertron.dao.VolumeDao;
import com.microwise.cybertron.sys.Cybertron;
import com.microwise.cybertron.sys.CybertronBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 卷 Dao 实现
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@Beans.Dao
@Cybertron
public class VolumeDaoImpl extends CybertronBaseDao<Volume> implements VolumeDao {
    public VolumeDaoImpl() {
        super(Volume.class);
    }

    @Override
    public List<Volume> findVolumeList() {
        String hql = "From Volume";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public Volume findVolume(int volumeCode) {
        String hql = "From Volume where volumeCode = ?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0,volumeCode);
        return (Volume)query.uniqueResult();
    }
}
