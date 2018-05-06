package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.dao.PictureDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * 图片数据库访问层实现
 *
 * @author zhang.licong
 * @date 2012-7-6
 */
@Dao
@Proxima
public class PictureDaoImpl extends BaseDaoImpl<PictureBean> implements
        PictureDao {

    public PictureDaoImpl() {
        super(PictureBean.class);
    }

    @Override
    public List<PictureBean> findPictures(String dvPlaceId) {
        Query query = super
                .getSession()
                .createQuery(
                        "select picture from PictureBean picture where picture.dv.id = :dvPlaceId order by picture.saveTime DESC");

        query.setParameter("dvPlaceId", dvPlaceId);
        return query.list();
    }

    @Override
    public List<PictureBean> findByDVPlace(String dvPlaceId, int start, int max) {
        return null; // To change body of implemented methods use File |
        // Settings | File Templates.
    }

    @Override
    public List<PictureBean> findByTime(String dvPlaceId, Date startTime,
                                        Date endTime) {
        Query query = super
                .getSession()
                .createQuery(
                        "select p from PictureBean p where p.dv.id=:dvPlaceId and p.saveTime <= :endTime and p.saveTime >= :startTime ORDER BY p.saveTime DESC");
        query.setParameter("dvPlaceId", dvPlaceId);
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        return query.list();
    }

    @Override
    public DVPlaceBean findDVPlaceByPictureId(String picId) {
        Session session = super.getSession();
        Query query = session
                .createQuery("select dvPlace from PictureBean pic join pic.dv as dvPlace where pic.id = :picId ");
        query.setParameter("picId", picId);
        query.setMaxResults(1);
        return (DVPlaceBean) query.uniqueResult();
    }

    @Override
    public List<PictureBean> findPicturesAfter(String dvPlaceId, Date dateAfter,
                                               int max) {
        Query query = super
                .getSession()
                .createQuery(
                        "select picture from PictureBean picture where picture.dv.id = :dvPlaceId and picture.saveTime <= :dateAfter ORDER BY picture.saveTime DESC");

        query.setParameter("dvPlaceId", dvPlaceId);
        query.setParameter("dateAfter", dateAfter);
        query.setMaxResults(max);

        return query.list();
    }

    @Override
    public List<PictureBean> findRecentPictures(String dvPlaceId, int count) {
        Query query = super
                .getSession()
                .createQuery(
                        "select p from PictureBean p where p.dv.id=:dvPlaceId order by p.saveTime desc");
        query.setParameter("dvPlaceId", dvPlaceId);
        query.setMaxResults(count);
        return query.list();
    }

}
