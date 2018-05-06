package com.microwise.halley.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.halley.bean.vo.ExhibitionVO;
import com.microwise.halley.dao.ExhibitionDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外展 Dao 实现
 *
 * @author li.jianfei
 * @date 2013-10-08
 * @check @wang.geng #5847 2013-10-10
 */
@Dao
@Halley
public class ExhibitionDaoImpl extends HalleyBaseDao implements ExhibitionDao {

    @Override
    public void syncExhibitions(String siteId) {
        getSqlSession().insert("halley.mybatis.ExhibitionDao.syncExhibitions", siteId);
    }

    @Override
    public List<ExhibitionVO> findExhibitionList(String siteId, int state) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("state", state);
        return getSqlSession().selectList("halley.mybatis.ExhibitionDao.findExhibitionList", params);
    }

    @Override
    public List<ExhibitionVO> findExhibitionListNotEnd(String siteId) {
        return getSqlSession().selectList("halley.mybatis.ExhibitionDao.findExhibitionListNotEnd", siteId);
    }

    @Override
    public List<ExhibitionVO> findExhibitionListCount(String siteId, int state) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("state", state);
        return getSqlSession().selectList("halley.mybatis.ExhibitionDao.findExhibitionListCount", params);
    }

    @Override
    public ExhibitionVO findExhibition(int exhibitionId) {
        return getSqlSession().selectOne("halley.mybatis.ExhibitionStateDao.findExhibition", exhibitionId);
    }

    @Override
    public void updateBeginTime(int exhibitionId, Date beginTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("beginTime", beginTime);
        params.put("exhibitionId", exhibitionId);

        getSqlSession().update("halley.mybatis.ExhibitionDao.updateBeginTime", params);
    }

    @Override
    public void updateEndTime(int exhibitionId, Date endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("endTime", endTime);
        params.put("exhibitionId", exhibitionId);

        getSqlSession().update("halley.mybatis.ExhibitionDao.updateEndTime", params);
    }

    @Override
    public void updateEndTime(int exhibitionId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("endTime", new Date());
        params.put("exhibitionId", exhibitionId);

        getSqlSession().update("halley.mybatis.ExhibitionDao.updateEndTime", params);
    }
}
