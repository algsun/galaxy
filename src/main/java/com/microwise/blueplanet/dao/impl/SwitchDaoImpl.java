package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.AliasPO;
import com.microwise.blueplanet.bean.po.SwitchChange;
import com.microwise.blueplanet.bean.po.Switches;
import com.microwise.blueplanet.dao.SwitchDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaohui
 * @date 14-2-20 09:34
 */
@Beans.Dao
@Blueplanet
public class SwitchDaoImpl extends BlueplanetBaseDao implements SwitchDao {

    @Override
    public List<Switches> findSwitchBySiteId(String siteId) {
        return getSqlSession().selectList("blueplanet.SwitchDao.findSwitchBySiteId", siteId);
    }

    @Override
    public int findSwitchesCountByDeviceId(String deviceId){
        return getSqlSession().<Integer>selectOne("blueplanet.SwitchDao.findSwitchesCountByDeviceId", deviceId);
    }

    @Override
    public List<Switches> findSwitchesByDeviceId(String deviceId, int start, int pageSize){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("start", start);
        params.put("pageSize", pageSize);
        return getSqlSession().selectList("blueplanet.SwitchDao.findSwitchesByDeviceId", params);
    }

    @Override
    public Switches findLastSwitchByDeviceId(String deviceId) {
        return getSqlSession().selectOne("blueplanet.SwitchDao.findLastSwitchByDeviceId", deviceId);
    }

    @Override
    public List<SwitchChange> findRecentSwitchChangesByDeviceId(String deviceId, int count){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("start", 0);
        params.put("pageSize", count);
        return getSqlSession().selectList("blueplanet.SwitchDao.findRecentSwitchChangesByDeviceId", params);
    }

    @Override
    public int findSwitchChangeCountByDeviceId(String deviceId, Date startTime, Date endTime, int route, int action) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("route", route);
        params.put("action", action);
        return getSqlSession().<Integer>selectOne("blueplanet.SwitchDao.findSwitchChangeCountByDeviceId", params);
    }

    @Override
    public List<SwitchChange> findSwitchChangeByDeviceId(String deviceId, Date startTime, Date endTime,int route, int action, int start, int pageSize){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("route", route);
        params.put("action", action);
        params.put("start", start);
        params.put("pageSize", pageSize);
        return getSqlSession().selectList("blueplanet.SwitchDao.findSwitchChangeByDeviceId", params);
    }

    @Override
    public void insertAlias(String id,String deviceId, int route, String alias) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("deviceId", deviceId);
        params.put("route", route);
        params.put("alias", alias);
        getSqlSession().insert("blueplanet.SwitchDao.insertAlias",params);
    }

    @Override
    public void deleteAlias(String device,int route) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", device);
        params.put("route", route);
        getSqlSession().delete("blueplanet.SwitchDao.deleteAlias",params);
    }

    @Override
    public boolean aliasIsExist(String deviceId, int route) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("route", route);
     return getSqlSession().<Integer>selectOne("blueplanet.SwitchDao.aliasIsExist",params)>0;
    }

    @Override
    public List<AliasPO> findAliasBySite(String siteId,int type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("nodeType", type);
        return getSqlSession().selectList("blueplanet.SwitchDao.findAliasBySite",params);
    }

    @Override
    public List<AliasPO> findAliasByDeviceId(String deviceId) {
        return getSqlSession().selectList("blueplanet.SwitchDao.findAliasByDeviceId",deviceId);
    }
}
