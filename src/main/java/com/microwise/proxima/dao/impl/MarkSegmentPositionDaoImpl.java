package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.dao.MarkSegmentPositionDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * MarkSegmentPositionBean dao implements
 *
 * @author gaohui
 * @date 2012-7-13
 */
@Dao
@Proxima
public class MarkSegmentPositionDaoImpl extends
        BaseDaoImpl<MarkSegmentPositionBean> implements MarkSegmentPositionDao {

    public MarkSegmentPositionDaoImpl() {
        super(MarkSegmentPositionBean.class);
    }

    @Override
    public List<MarkSegmentPositionBean> findByPicId(String picId) {
        Query query = super
                .getSession()
                .createQuery(
                        "select msp from MarkSegmentPositionBean msp left join fetch msp.markSegment  where msp.picture.id = :picId");
        query.setParameter("picId", picId);
        return (List<MarkSegmentPositionBean>) query.list();
    }

    @Override
    public List<MarkSegmentPositionBean> findActiveByPicId(String picId) {
        Query query = super
                .getSession()
                .createQuery(
                        "select msp from MarkSegmentPositionBean msp left join fetch msp.markSegment  where msp.picture.id = :picId and msp.markSegment.cancel = false");
        query.setParameter("picId", picId);
        return (List<MarkSegmentPositionBean>) query.list();
    }

    @Override
    public List<MarkSegmentPositionBean> findLatestBefore(String picId) {
        PictureBean lastedEditedPicture = findLastedEditedPictureBefore(picId);

        if (lastedEditedPicture != null) {
            return findByPicId(lastedEditedPicture.getId());
        }
        return Collections.emptyList();
    }

    @Override
    public List<MarkSegmentPositionBean> findLatestActiveBefore(String picId) {
        PictureBean lastedEditedPicture = findLastedEditedPictureBefore(picId);

        if (lastedEditedPicture != null) {
            return findActiveByPicId(lastedEditedPicture.getId());
        }
        return Collections.emptyList();
    }

    /**
     * 查询某个图片之前，最后一次修改的图片
     *
     * @param picId
     * @return
     */
    @Override
    public PictureBean findLastedEditedPictureBefore(String picId) {
        PictureBean picture = (PictureBean) super.getSession().get(
                PictureBean.class, picId);
        String dvPlaceId = picture.getDv().getId();
        // 查询最后一次编辑的图片 id
        Query query0 = super
                .getSession()
                .createQuery(
                        "select picture from MarkSegmentPositionBean msp join msp.picture as picture where picture.saveTime < ("
                                + "select pic.saveTime from PictureBean pic where pic.id = :picId"
                                + ") and picture.dv.id = :dvPlaceId order by picture.saveTime DESC");
        query0.setParameter("picId", picId);
        query0.setParameter("dvPlaceId", dvPlaceId);
        query0.setMaxResults(1);
        PictureBean lastedEditedPicture = (PictureBean) query0.uniqueResult();
        return lastedEditedPicture;
    }

    /**
     * 查询某个图片之后，修改的第一个图片
     *
     * @param picId
     * @return
     */
    @Override
    public PictureBean findLastedEditedPictureAfter(String picId) {
        PictureBean picture = (PictureBean) super.getSession().get(
                PictureBean.class, picId);
        String dvPlaceId = picture.getDv().getId();
        // 查询之后一次编辑的图片 id
        Query query0 = super
                .getSession()
                .createQuery(
                        "select picture from MarkSegmentPositionBean msp join msp.picture as picture where picture.saveTime > ("
                                + "select pic.saveTime from PictureBean pic where pic.id = :picId"
                                + ") and picture.dv.id = :dvPlaceId order by picture.saveTime ASC");
        query0.setParameter("picId", picId);
        query0.setParameter("dvPlaceId", dvPlaceId);
        query0.setMaxResults(1);
        PictureBean lastedEditedPicture = (PictureBean) query0.uniqueResult();
        return lastedEditedPicture;
    }

    @Override
    public PictureBean findLatestEditedPicture(String dvPlaceId) {
        Session session = super.getSession();

        Query query = session
                .createQuery("select picture from MarkSegmentPositionBean msp join msp.picture as picture where picture.dv.id = :dvPlaceId order by picture.saveTime DESC");
        query.setParameter("dvPlaceId", dvPlaceId);
        query.setMaxResults(1);

        return (PictureBean) query.uniqueResult();

    }

    @Override
    public MarkSegmentPositionBean find(String markSegmentId, String picId) {
        Session session = super.getSession();

        Query query = session
                .createQuery("select msp from MarkSegmentPositionBean msp where msp.picture.id = :picId and msp.markSegment.id = :markSegmentId");
        query.setParameter("markSegmentId", markSegmentId);
        query.setParameter("picId", picId);
        query.setMaxResults(1);
        return (MarkSegmentPositionBean) query.uniqueResult();
    }

    @Override
    public List<MarkSegmentPositionBean> findAllMarkedSegmentData(
            String dvPlaceId, String markId, Date startDate, Date endDate) {
        Query query = super.getSession().
                createQuery("select msp from MarkSegmentPositionBean msp join fetch msp.picture p join fetch msp.markSegment ms where p.dv.id = :dvPlaceId and ms.id = :markId and p.saveTime >= :startDate and p.saveTime <= :endDate order by ms.name,p.saveTime");

        query.setParameter("dvPlaceId", dvPlaceId);
        query.setParameter("markId", markId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.list();
    }

    @Override
    public List<MarkSegmentPositionBean> findMarkedSegmentData(String markId, Date startDate, Date endDate) {
        Query query = super.getSession().
                createQuery("select msp from MarkSegmentPositionBean msp join fetch msp.picture p join fetch msp.markSegment ms where ms.id = :markId and p.saveTime >= :startDate and p.saveTime <= :endDate order by ms.name,p.saveTime");
        query.setParameter("markId", markId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.list();
    }

    @Override
    public List<MarkSegmentPositionBean> findAllMarkPositionData(String dvPlaceId,
                                                                 Date startDate, Date endDate) {
        Session session = super.getSession();

        Query query = session
                .createQuery("select msp from MarkSegmentPositionBean msp join fetch msp.picture p join fetch msp.markSegment ms where p.dv.id = :dvPlaceId and p.saveTime >= :startDate and p.saveTime <= :endDate order by ms.name,p.saveTime");
        query.setParameter("dvPlaceId", dvPlaceId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.list();
    }

    @Override
    public Integer[] findMaxXY(String dvPlaceId, Date startDate, Date endDate) {

        Session session = super.getSession();

        Query query = session.createQuery("select max(msp.positionX), max(msp.positionX2), max(msp.positionY), max(msp.positionY2), min(msp.positionX), min(msp.positionX2), min(msp.positionY), min(msp.positionY2) from MarkSegmentPositionBean msp join msp.picture p join msp.markSegment ms where p.dv.id = :dvPlaceId and p.saveTime >= :startDate and p.saveTime <= :endDate order by ms.name,p.saveTime");
        query.setParameter("dvPlaceId", dvPlaceId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        Object[] result = (Object[]) query.uniqueResult();

        Integer[] data = new Integer[8];
        for (int i = 0; i < result.length; i++) {
            if (result[i] == null) {
                return null;
            }
            data[i] = (Integer) result[i];
        }

        return data;
    }

}
