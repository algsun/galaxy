/**
 *
 */
package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.dao.InfraredDVPlaceDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * <pre>
 * 红外热像仪点位信息数据库访问层的实现
 * </pre>
 *
 * @author zhangpeng
 * @date 2012-7-9
 */
@Dao
@Proxima
public class InfraredDVPlaceDaoImpl extends BaseDaoImpl<InfraredDVPlaceBean>
        implements InfraredDVPlaceDao {

    public InfraredDVPlaceDaoImpl() {
        super(InfraredDVPlaceBean.class);
    }

    @Override
    public List<InfraredDVPlaceBean> findAll(String siteId) {
        String hql = " select dvPlace from InfraredDVPlaceBean dvPlace  inner JOIN fetch dvPlace.zone as zone where zone.site.siteId = :siteId ORDER BY dvPlace.createTime desc ";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        return query.list();
    }

    @Override
    public List<InfraredDVPlaceBean> findAllByZoneId(String zoneId) {
        String hql = " select dvPlace from InfraredDVPlaceBean dvPlace  inner JOIN fetch dvPlace.zone as zone where zone.id = :zoneId ORDER BY dvPlace.createTime desc ";
        Query query = getSession().createQuery(hql);
        query.setParameter("zoneId", zoneId);
        return query.list();
    }


    @Override
    public List<InfraredDVPlaceBean> findAllEnable(boolean enable) {
        String hql = " select i from InfraredDVPlaceBean i where i.enable= "
                + enable + " ORDER BY i.createTime desc ";
        Query query = getSession().createQuery(hql);
        List<InfraredDVPlaceBean> infraredDVPlaceList = query.list();
        return infraredDVPlaceList;
    }

    @Override
    public List<InfraredDVPlaceBean> findAllWithMonitorPoint() {
        Query query = getSession()
                .createQuery(
                        "select dvPlace from InfraredDVPlaceBean dvPlace INNER JOIN fetch dvPlace.monitorPoint ORDER BY dvPlace.createTime DESC");
        return query.list();
    }

    @Override
    public List<InfraredDVPlaceBean> findByMonitorPointId(int monitorPointId) {
        Session session = getSession();
        String hql = "select dvPlace from InfraredDVPlaceBean dvPlace  where monitorPointId = :monitorPointId";
        Query query = session.createQuery(hql);
        query.setParameter("monitorPointId", monitorPointId);
        return (List<InfraredDVPlaceBean>) query.list();
    }

    @Override
    public InfraredDVPlaceBean findById(String id) {
        Query query = getSession()
                .createQuery(
                        "select dv from InfraredDVPlaceBean dv inner JOIN fetch dv.zone inner JOIN fetch dv.ftp where dv.id = :id");
        query.setParameter("id", id);
        return (InfraredDVPlaceBean) query.uniqueResult();
    }
}
