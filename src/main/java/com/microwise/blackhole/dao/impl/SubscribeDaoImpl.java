package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.dao.SubscribeDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Query;

import java.util.List;

/**
 * 订阅DAO 实现
 *
 * @author xubaoji
 * @date 2013-6-13
 * @check li.jianfei 2013-06-20 #4227
 */
@Blackhole
@Dao
public class SubscribeDaoImpl extends BlackholeBaseDao<Subscribe> implements
        SubscribeDao {

    public SubscribeDaoImpl() {
        super(Subscribe.class);
    }

    @Override
    public void deleteSubscribe(Integer userId, Integer type, String siteId, String locationId) {
        String hql = " delete  from Subscribe s where  user.id = :userId and subscribeType = :type and siteId = :siteId and locationId = :locationId ";
        getSession().createQuery(hql).setParameter("userId", userId)
                .setParameter("type", type).setParameter("siteId", siteId).setParameter("locationId", locationId)
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subscribe> findSubscribeBySite(String siteId, Integer type) {
        String hql = " from Subscribe s left join fetch s.user where s.siteId = :siteId and s.subscribeType = :type ";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        query.setParameter("type", type);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subscribe> findSubscribeByUser(String siteId, int userId) {
        String hql = " from Subscribe s where s.siteId = :siteId and s.user.id = :userId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        query.setParameter("userId", userId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subscribe> findSubscribes(int subscribeType) {
        String hql = " from Subscribe s where s.subscribeType = :subscribeType ";
        Query query = getSession().createQuery(hql);
        query.setParameter("subscribeType", subscribeType);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subscribe> findSubscribe(int userId, String locationId, int subscribeType) {
        String hql = " from Subscribe s where s.locationId = :locationId and s.user.id = :userId and s.subscribeType = :subscribeType ";
        Query query = getSession().createQuery(hql);
        query.setParameter("locationId", locationId);
        query.setParameter("userId", userId);
        query.setParameter("subscribeType", subscribeType);
        return query.list();
    }
}
