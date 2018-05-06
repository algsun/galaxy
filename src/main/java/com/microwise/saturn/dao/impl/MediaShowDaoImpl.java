package com.microwise.saturn.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.MediaShowPO;
import com.microwise.saturn.dao.MediaShowDao;
import com.microwise.saturn.sys.Saturn;
import com.microwise.saturn.sys.SaturnBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展示类别Dao实现
 *
 * @author 王耕
 * @date 2015-3-16
 */
@Beans.Dao
@Saturn
public class MediaShowDaoImpl extends SaturnBaseDao implements MediaShowDao {
    @Override
    public void save(MediaShowPO mediaShow) {
        getSqlSession().insert("saturn.mybatis.MediaShowDao.save", mediaShow);
    }

    @Override
    public void delete(int mediaShowId) {
        getSqlSession().delete("saturn.mybatis.MediaShowDao.deleteById", mediaShowId);
    }

    @Override
    public List<MediaShowPO> findAllByPage(int pageIndex, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int begin = (pageIndex - 1) * pageSize;
        paramMap.put("begin", begin);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList("saturn.mybatis.MediaShowDao.findAllByPage", paramMap);
    }

    @Override
    public List<MediaShowPO> findAll() {
        return getSqlSession().selectList("saturn.mybatis.MediaShowDao.findAll");
    }

    @Override
    public List<MediaShowPO> findByCondition(MediaShowPO mediaShow, int pageIndex, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", mediaShow.getId());
        paramMap.put("title", mediaShow.getTitle());
        paramMap.put("remark", mediaShow.getRemark());
        paramMap.put("indexImage", mediaShow.getIndexImage());
        paramMap.put("createOn", mediaShow.getCreateOn());
        paramMap.put("createBy", mediaShow.getCreateBy());
        paramMap.put("siteCode", mediaShow.getSiteCode());

        int begin = (pageIndex - 1) * pageSize;
        paramMap.put("begin", begin);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList("saturn.mybatis.MediaShowDao.findByCondition", paramMap);
    }

    @Override
    public int findByConditionSize(MediaShowPO mediaShow) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", mediaShow.getId());
        paramMap.put("title", mediaShow.getTitle());
        paramMap.put("remark", mediaShow.getRemark());
        paramMap.put("indexImage", mediaShow.getIndexImage());
        paramMap.put("createOn", mediaShow.getCreateOn());
        paramMap.put("createBy", mediaShow.getCreateBy());
        paramMap.put("siteCode", mediaShow.getSiteCode());

        List<MediaShowPO> mediaShows = getSqlSession().selectList("saturn.mybatis.MediaShowDao.findByConditionSize", paramMap);
        return mediaShows == null ? 0 : mediaShows.size();
    }

    @Override
    public void update(MediaShowPO mediaShow) {
        getSqlSession().update("saturn.mybatis.MediaShowDao.update", mediaShow);
    }
}
