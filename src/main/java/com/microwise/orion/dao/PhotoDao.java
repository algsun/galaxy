package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Photo;

import java.util.List;
import java.util.Set;

/**
 * 相片 dao
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3576
 */
public interface PhotoDao extends BaseDao<Photo> {
    Set<Photo> findByRelicId(int relicId);
}
