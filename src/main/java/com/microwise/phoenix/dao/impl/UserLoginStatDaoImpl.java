package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.UserLogin;
import com.microwise.phoenix.dao.UserLoginStatDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统管理： 用户登录习惯统计 dao 实现
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #4959
 */
@Phoenix
@Dao
public class UserLoginStatDaoImpl extends PhoenixBaseDao implements UserLoginStatDao {

    @Override
    public Integer findUserLoginCount(int logicGroupId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectOne("phoenix.mybatis.UserLoginStatDao.findUserLoginCount",
                paramMap);
    }

    @Override
    public List<Map<String, Object>> findUserLoginDayStat(int logicGroupId, int loginCount,
                                                          Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("loginCount", loginCount);
        return getSqlSession().selectList("phoenix.mybatis.UserLoginStatDao.findUserLoginDayStat",
                paramMap);
    }

    @Override
    public List<Map<String, Object>> findUserLoginWeekStat(int logicGroupId, int loginCount,
                                                           Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("loginCount", loginCount);
        return getSqlSession().selectList("phoenix.mybatis.UserLoginStatDao.findUserLoginWeekStat",
                paramMap);
    }

    @Override
    public List<UserLogin> findUserBySite(int logicGroupId) {
        return getSqlSession().selectList("phoenix.mybatis.UserLoginStatDao.findUserBySite", logicGroupId);
    }

    @Override
    public List<UserLogin> findUserLoginByEmail(int logicGroupId, String email, Date startDate,
                                                Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("email", email);
        return getSqlSession().selectList("phoenix.mybatis.UserLoginStatDao.findUserLoginByEmail", paramMap);
    }
}
