package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.ZonePO;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.SiteDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.apache.struts2.ServletActionContext;

import java.util.*;

/**
 * 站点Dao实现
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1731
 * @check 2013-04-22 xubaoji svn:2568
 * @check 2013-11-14 xiedeng svn:6621
 */
@Dao
@Blueplanet
public class SiteDaoImpl extends BlueplanetBaseDao implements SiteDao {

    @Override
    public List<DeviceVO> findAllDevicesBySiteId(String siteId) {
        return getSqlSession().selectList(
                "blueplanet.mybatis.SiteDao.findAllDevicesBySiteId", siteId);
    }

    /**
     * 设备 状态信息（设备实时数据）
     *
     * @param siteId               站点编号
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return
     */
    @Override
    public List<RealtimeDataVO> findNodeinfo(String siteId,
                                             List<Integer> sensorPhysicalidList) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("sensorPhysicalidList", sensorPhysicalidList);
        return getSqlSession().selectList(
                "blueplanet.mybatis.SiteDao.findNodeinfo", paramMap);
    }

    @Override
    public List<RealtimeDataVO> findLocationInfo(String siteId,
                                                 List<Integer> sensorPhysicalidList) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("sensorPhysicalidList", sensorPhysicalidList);
        return getSqlSession().selectList(
                "blueplanet.mybatis.SiteDao.findLocationInfo", paramMap);
    }

    @Override
    public SiteVO findSiteById(String siteId) {
        return getSqlSession().selectOne(
                "blueplanet.mybatis.SiteDao.findSiteById", siteId);
    }

    @Override
    public List<SiteVO> findSite() {
        return getSqlSession().selectList(
                "blueplanet.mybatis.SiteDao.findSite");
    }

    @Override
    public List<SensorinfoVO> findSensorinfo(String siteId) {
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("language", LocaleBundleTools.appLocale());
        return getSqlSession().selectList("blueplanet.mybatis.SiteDao.findSensorinfo", paramMap);
    }

    @Override
    public List<SensorinfoVO> findDeviceSensorInfo(String siteId) {
        return getSqlSession().selectList(
                "blueplanet.mybatis.SiteDao.findDeviceSensorInfo", siteId);
    }

    @Override
    public List<SiteVO> findSiteHasSubscribeUser() {
        return getSqlSession().selectList(
                "blueplanet.mybatis.SiteDao.findSiteHasSubscribeUser");
    }

    @Override
    public List<LocationDataVO> findAvgLocationById(Date startDate, String siteId, String zoneId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", startDate);
        paramMap.put("siteId", siteId);
        paramMap.put("zoneId", zoneId);
        paramMap.put("language", LocaleBundleTools.appLocale());
        return getSqlSession().selectList("blueplanet.mybatis.SiteDao.findAvgLocationById", paramMap);
    }

    @Override
    public List<ZonePO> findZonePOList(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.SiteDao.findZoneBySiteIdParent", siteId);
    }

    @Override
    public List<SiteVO> findAllSite() {
        return getSqlSession().selectList("blueplanet.mybatis.SiteDao.findAllSite");
    }

    @Override
    public List<SensorUsedDataVO> findSensorUsedInfo(String siteId, String language) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("siteId", siteId);
        map.put("language", language);
        return getSqlSession().selectList("blueplanet.mybatis.SensorinfoDao.findSensorUsedInfo", map);
    }

}
