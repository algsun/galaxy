package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.ImageDatum;
import com.microwise.orion.dao.ImageDatumDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 影像资料dao 实现
 *
 * @author liuzhu
 * @date 2015-11-10
 */
@Orion
@Beans.Dao
public class ImageDatumDaoImpl extends OrionBaseDao<ImageDatum> implements ImageDatumDao {

    public ImageDatumDaoImpl() {
        super(ImageDatum.class);
    }

    @Override
    public List<ImageDatum> findImageDatums(int repairRecordId) {
        String hql = " from ImageDatum id where id.repairRecord.id = :repairRecordId order by id.id DESC";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId", repairRecordId);
        return query.list();
    }

    @Override
    public ImageDatum findNewImageDatum() {
        String hql = " from ImageDatum i order by i.id DESC ";
        Query query = getSession().createQuery(hql);
        query.setMaxResults(1);
        return (ImageDatum) query.uniqueResult();
    }

    @Override
    public ImageDatum findImageDatum(int id) {
        String hql = " from ImageDatum i join fetch i.repairRecord where i.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);
        return (ImageDatum) query.uniqueResult();
    }
}
