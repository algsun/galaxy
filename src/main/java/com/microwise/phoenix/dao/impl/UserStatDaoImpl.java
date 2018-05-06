package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.uma.UserStat;
import com.microwise.phoenix.dao.UserStatDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员管理：人员统计 dao 实现
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @li.jianfei 2013.09.02 #5215
 */
@Dao
@Phoenix
public class UserStatDaoImpl extends PhoenixBaseDao implements UserStatDao {

    @Override
    public List<UserStat> findUserFrequencyOfActivitiesStat(int logicGroupId, Date startDate, Date endDate,
                                                            int size, boolean isDesc) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("size", size);
        paramMap.put("isDesc", isDesc);
        return getSqlSession().
                selectList("phoenix.mybatis.UserStatDao.findUserFrequencyOfActivitiesStat", paramMap);
    }

    @Override
    public Integer findUserCountByActivityCount(int logicGroupId, Date startDate, Date endDate, int activityCount) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("activityCount", activityCount);
        return getSqlSession().selectOne("phoenix.mybatis.UserStatDao.findUserCountByActivityCount", paramMap);
    }

    @Override
    public boolean hasData(int logicGroupId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        Integer count = getSqlSession().selectOne("phoenix.mybatis.UserStatDao.hasData", paramMap);
        return count == 0 ? false : true;
    }

    @Override
    public List<Map<String, Object>> findUserMorningAndEveningStat(int logicGroupId,
                                                                   Date startDate, Date endDate, boolean isEvening, int size) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate.getTime());
        paramMap.put("endDate", endDate.getTime());
        paramMap.put("isDesc", isEvening);
        paramMap.put("size", size);
        return getSqlSession().selectList("phoenix.mybatis.UserStatDao.findUserMorningAndEveningStat", paramMap);
    }

    @Override
    public Map<String, Object> findMorningAndEveningUser(int logicGroupId, Date startDate,
                                                         Date endDate, boolean isEvening) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate.getTime());
        paramMap.put("endDate", endDate.getTime());
        paramMap.put("isDesc", isEvening);
        return getSqlSession().selectOne("phoenix.mybatis.UserStatDao.findMorningAndEveningUser", paramMap);
    }
}
