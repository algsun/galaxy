package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Property;
import com.microwise.orion.dao.PropertyDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 文物字典信息 dao 实现
 *
 * @author xubaoji
 * @date 2013-5-22
 * @check 2013-06-04 zhangpeng svn:3610
 */
@Dao
@Orion
public class PropertyDaoImpl extends OrionBaseDao<Property> implements PropertyDao {

    public PropertyDaoImpl() {
        super(Property.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Property> findAllProperty() {
        String hql = "from Property p order by p.orderNum";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public Property findByEnName(String enName) {
        String hql = "from Property p where p.enName = :enName";
        Query query = getSession().createQuery(hql);
        query.setParameter("enName", enName);
        return (Property)query.uniqueResult();
    }

}

