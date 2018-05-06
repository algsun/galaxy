package com.microwise.halley.dao.impl;

import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.dao.ExhibitionStateDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外展状态 Dao 实现
 *
 * @author li.jianfei
 * @date 2013-09-29
 * @check @wang.geng @li.jianfei  2013-10-25  #6165
 */
@Dao
@Halley
public class ExhibitionStateDaoImpl extends HalleyBaseDao implements ExhibitionStateDao {

    @Override
    public void addState(ExhibitionStateVO exhibitionState) {
        getSqlSession().insert("halley.mybatis.ExhibitionStateDao.addState", exhibitionState);
    }

    @Override
    public ExhibitionStateVO findCurrentState(int exhibitionId) {
        return getSqlSession().selectOne("halley.mybatis.ExhibitionStateDao.findCurrentState", exhibitionId);
    }


    @Override
    public List<ExhibitionStateVO> getHistoryState(int exhibitionId) {
        return getSqlSession().selectList("halley.mybatis.ExhibitionStateDao.getHistoryState", exhibitionId);
    }

    @Override
    public PathPO getStartDestination(int exhibitionId) {
        return getSqlSession().selectOne("halley.mybatis.ExhibitionStateDao.getStartDestination", exhibitionId);
    }


    @Override
    public void alterHistoryItemEndTime(int exhibitionId, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exhibitionId", exhibitionId);
        map.put("endTime", endTime);
        getSqlSession().update("halley.mybatis.ExhibitionStateDao.alterHistoryItemEndTime", map);
    }

    @Override
    public User findUser(int id) {
        return getSqlSession().selectOne("halley.mybatis.ExhibitionStateDao.findUser", id);
    }

    @Override
    public List<PathPO> getALLPathPO(int ExhibitionId) {
        return getSqlSession().selectList("halley.mybatis.ExhibitionStateDao.getALLPathPO", ExhibitionId);
    }

    @Override
    public Date findExhibitionBeginTime(int exhibitionId) {
        return getSqlSession().selectOne("halley.mybatis.ExhibitionStateDao.findExhibitionBeginTime", exhibitionId);
    }


}