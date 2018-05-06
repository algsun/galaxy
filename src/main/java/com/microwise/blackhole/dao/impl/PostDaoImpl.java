package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.Post;
import com.microwise.blackhole.dao.PostDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author gaohui
 * @date 13-5-8 17:23
 */
@Beans.Dao
@Blackhole
public class PostDaoImpl extends BlackholeBaseDao<Post> implements PostDao {
    public PostDaoImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> findLatest(int scope, int max) {
        Criteria criteria = getSession().createCriteria(Post.class);
        if (scope > 0) {
            criteria.add(Restrictions.eq("scope", scope));
        }
        criteria.addOrder(Order.desc("createDate"));
        criteria.setMaxResults(max);

        return criteria.list();
    }

    @Override
    public List<Post> findLatest(int scope, int start, int max) {
        Criteria criteria = getSession().createCriteria(Post.class);
        if (scope > 0) {
            criteria.add(Restrictions.eq("scope", scope));
        }
        criteria.addOrder(Order.desc("createDate"));
        criteria.setFirstResult(start);
        criteria.setMaxResults(max);

        return criteria.list();
    }

    @Override
    public int findCount(int scope) {
        Criteria criteria = getSession().createCriteria(Post.class);
        if (scope > 0) {
            criteria.add(Restrictions.eq("scope", scope));
        }
        criteria.setProjection(Projections.count("id"));

        return ((Long) criteria.uniqueResult()).intValue();
    }

}
