package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.dao.FollowerDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.HashMap;
import java.util.Map;

/**
 * author : chenyaofei
 * date : 2016-07-19
 */
@Blueplanet
@Beans.Dao
public class FollowerDaoImpl extends BlueplanetBaseDao implements FollowerDao{

    public int findFollower(int userId, String locationId) {
        Map prams = new HashMap();
        prams.put("userId",userId);
        prams.put("locationId", locationId);
        return getSqlSession().selectOne("blueplanet.mybatis.FollowerDao.findFollower", prams);
    }

    public void deleteFollower(int userId, String locationId) {
        Map prams = new HashMap();
        prams.put("userId",userId);
        prams.put("locationId",locationId);
        getSqlSession().delete("blueplanet.mybatis.FollowerDao.deleteFollower",prams);
    }

    public void addFollower(int userId, String locationId) {
        Map prams = new HashMap();
        prams.put("userId",userId);
        prams.put("locationId",locationId);
        getSqlSession().insert("blueplanet.mybatis.FollowerDao.addFollower",prams);
    }
}
