package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.dao.AreaCodeDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Query;

import java.util.List;

/**
 * 地区行政 dao 实现
 *
 * @author xubaoji
 * @date 2012-11-29
 * @check 2012-12-05 zhangpeng svn:642
 */
@Dao
@Blackhole
public class AreaCodeDaoImpl extends BlackholeBaseDao<AreaCodeCN> implements
        AreaCodeDao {

    public AreaCodeDaoImpl() {
        super(AreaCodeCN.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AreaCodeCN> findAllArea() {
        String hql = " from AreaCodeCN ac where ac.filte =? ";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, false);
        return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AreaCodeCN> findAreaListByAreaCode(int areaCode) {
        String hql;
        if (areaCode != 0) {
            hql = " from  AreaCodeCN ac where ac.parentAreaCodeCN.areaCode=? and ac.filte =? ";
        } else {
            hql = " from  AreaCodeCN ac where ac.parentAreaCodeCN.areaCode is null and ac.filte =?";
        }
        Query q = getSession().createQuery(hql);
        if (areaCode != 0) {
            q.setParameter(0, areaCode);
            q.setParameter(1, false);
        } else {
            q.setParameter(0, false);
        }
        return q.list();
    }

}
