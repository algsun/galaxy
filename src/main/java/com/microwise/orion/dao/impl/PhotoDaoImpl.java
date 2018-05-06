package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Photo;
import com.microwise.orion.dao.PhotoDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.HashSet;
import java.util.Set;

/**
 * 相片dao实现
 *
 * @author xubaoji
 * @date 2013-5-21
 * @check 2013-06-04 zhangpeng svn:3608
 */
@Dao
@Orion
public class PhotoDaoImpl extends OrionBaseDao<Photo> implements PhotoDao {

    public PhotoDaoImpl() {
        super(Photo.class);
    }

    @Override
    public Set<Photo> findByRelicId(int relicId) {
        String hql = "from Photo p where p.relic.id = :relicId";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        return new HashSet<Photo>(query.list());
    }
}
