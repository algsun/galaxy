package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.dao.UserDistributionDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Phoenix
@Dao
public class UserDistributionDaoImpl extends PhoenixBaseDao implements
        UserDistributionDao {

    @Override
    public List<Map<String, Object>> getDistrictInfo(String siteId) {
        return getSqlSession().selectList(
                "phoenix.mybatis.UserDistributionDao.getDistrictInfo", siteId);
    }

    @Override
    public List<Map<String, Object>> getUserDistriData(long startTime, long endTime, String siteId) {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("startTime", startTime);
        parm.put("endTime", endTime);
        parm.put("siteId", siteId);
        List<Map<String, Object>> list = getSqlSession().selectList(
                "phoenix.mybatis.UserDistributionDao.getUserDistriData", parm);
        return list;
    }

    @Override
    public Map<String, Object> getMaxActiveAreaAndCount(long startTime,
                                                        long endTime, String siteId) {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("startTime", startTime);
        parm.put("endTime", endTime);
        parm.put("siteId", siteId);
        Map<String, Object> info = getSqlSession().selectOne("phoenix.mybatis.UserDistributionDao.getMaxActiveAreaAndCount", parm);
        return info;
    }

    @Override
    public Map<String, Object> getMaxActiveTimeAndCount(long startTime,
                                                        long endTime, String siteId) {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("startTime", startTime);
        parm.put("endTime", endTime);
        parm.put("siteId", siteId);
        Map<String, Object> info = getSqlSession().selectOne("phoenix.mybatis.UserDistributionDao.getMaxActiveTimeAndCount", parm);
        return info;
    }

    @Override
    public Map<String, Object> getMaxActiveTimeAreaAndCount(long startTime,
                                                            long endTime, String siteId) {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("startTime", startTime);
        parm.put("endTime", endTime);
        parm.put("siteId", siteId);
        Map<String, Object> info = getSqlSession().selectOne("phoenix.mybatis.UserDistributionDao.getMaxActiveTimeAreaAndCount", parm);
        return info;
    }


}
