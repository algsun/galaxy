package com.microwise.uma.dao.impl;


import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.uma.bean.ZoneBean;
import com.microwise.uma.dao.ZoneDao;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaBaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域 DAO 实现
 *
 * @author li.jianfei
 * @date 2013-4-18
 * @check @wang yunlong 2013-04-28 #2873
 */
@Dao
@Uma
public class ZoneDaoImpl extends UmaBaseDao implements ZoneDao {

    @Override
    public List<ZoneBean> findZonesHasDevice(String siteId) {
        return getSqlSession().selectList("uma.mybatis.ZoneDao.findZonesHasDevice", siteId);
    }

    @Override
    public List<ZoneBean> findZoneListByParentId(String siteId, String parentId, int hourInterval) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("parentId", parentId);
        map.put("hourInterval", hourInterval);
        return getSqlSession().selectList("uma.mybatis.ZoneDao.findZoneListByParentId", map);
    }

    @Override
    public List<ZoneBean> findPeoplesInZone(String zoneId, int hourInterval) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("zoneId", zoneId);
        map.put("hourInterval", hourInterval);
        return getSqlSession().selectList("uma.mybatis.ZoneDao.findPeoplesInZone", map);
    }

    @Override
    public List<ZoneBean> countGoTimesOfZone(String siteId) {
        if (siteId == null) {
            return new ArrayList<ZoneBean>();
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("siteId", siteId);
            return getSqlSession().selectList("uma.mybatis.ZoneDao.countGobackTimesOfZone", params);
        }
    }

    @Override
    public List<ZoneBean> findZoneList(String siteId, String parentId) {
        if (siteId == null) {
            return new ArrayList<ZoneBean>();
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("siteId", siteId);
            params.put("parentId", parentId);
            return getSqlSession().selectList("uma.mybatis.ZoneDao.findZoneList", params);
        }
    }

    @Override
    public List<ZoneBean> findPeopleInZone(String siteId, String zoneId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("siteId", siteId);
        params.put("zoneId", zoneId);
        return getSqlSession().selectList("uma.mybatis.ZoneDao.findPeopleInfZoneByZoneId", params);
    }

    @Override
    public ZoneBean findZoneById(String zoneId) {
        return getSqlSession().selectOne("uma.mybatis.ZoneDao.findZoneById", zoneId);
    }

    @Override
    public List<ZoneBean> findSubZoneById(String zoneId) {
        return getSqlSession().selectList("uma.mybatis.ZoneDao.findSubZoneById", zoneId);
    }
}
