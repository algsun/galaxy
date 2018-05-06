package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.dao.SiteDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans;
import org.hibernate.Query;

import java.util.List;

/**
 * 站点 Dao 实现
 *
 * @author li.jianfei
 * @date 2014-10-09
 */
@Beans.Dao
@Blackhole
public class SiteDaoImpl extends BlackholeBaseDao<Site> implements SiteDao {

    public SiteDaoImpl() {
        super(Site.class);
    }

    @Override
    public List<Site> findAll() {
        String hql = "Select s From Site s left outer join fetch s.areacodeCN ac left join fetch ac.parentAreaCodeCN";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
