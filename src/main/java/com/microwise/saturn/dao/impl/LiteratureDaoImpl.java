package com.microwise.saturn.dao.impl;

import com.google.common.collect.Maps;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.Literature;
import com.microwise.saturn.dao.LiteratureDao;
import com.microwise.saturn.sys.Saturn;
import com.microwise.saturn.sys.SaturnBaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created by lijianfei on 15-3-16.
 */
@Beans.Dao
@Saturn
public class LiteratureDaoImpl extends SaturnBaseDao implements LiteratureDao {
    @Override
    public void save(Literature literature) {
        getSqlSession().insert("saturn.mybatis.LiteratureDao.save", literature);
    }

    @Override
    public void update(Literature literature) {
        getSqlSession().insert("saturn.mybatis.LiteratureDao.update", literature);
    }

    @Override
    public List<Literature> findAll(String siteId) {
        return getSqlSession().selectList("saturn.mybatis.LiteratureDao.findAll", siteId);
    }

    @Override
    public Literature findById(int literatureId) {
        return getSqlSession().selectOne("saturn.mybatis.LiteratureDao.findById", literatureId);
    }

    @Override
    public void delete(int literatureId) {
        getSqlSession().delete("saturn.mybatis.LiteratureDao.delete", literatureId);
    }

    @Override
    public List<Literature> findLiteratures(String siteId, String title, String keywords, int index, int pageSize) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("siteId", siteId);
        params.put("title", title);
        params.put("keywords", keywords);
        params.put("start", (index - 1) * pageSize);
        params.put("pageSize", pageSize);
        return getSqlSession().selectList("saturn.mybatis.LiteratureDao.findLiteratures", params);
    }

    @Override
    public int countLiteratures(String siteId, String title, String keywords) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("siteId", siteId);
        params.put("title", title);
        params.put("keywords", keywords);
        return getSqlSession().<Integer>selectOne("saturn.mybatis.LiteratureDao.countLiteratures", params);
    }
}
