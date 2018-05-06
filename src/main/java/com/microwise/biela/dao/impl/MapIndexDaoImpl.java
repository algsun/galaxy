package com.microwise.biela.dao.impl;

import com.microwise.biela.bean.po.AreaCodePO;
import com.microwise.biela.bean.po.LogicGroupPO;
import com.microwise.biela.bean.po.SensorInfoPO;
import com.microwise.biela.bean.vo.CustomizeVO;
import com.microwise.biela.bean.vo.LocationInfoVO;
import com.microwise.biela.bean.vo.NodeSensorInfoVO;
import com.microwise.biela.dao.MapIndexDao;
import com.microwise.biela.sys.Biela;
import com.microwise.biela.sys.BielaBaseDao;
import com.microwise.blueplanet.bean.po.ZonePO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点地图dao接口实现类
 *
 * @author liuzhu
 * @date 14-1-2
 * @check @wang.geng 2014-1-17 #7690
 */
@Beans.Dao
@Biela
public class MapIndexDaoImpl extends BielaBaseDao implements MapIndexDao {

    @Override
    public List<LocationInfoVO> findGeneralLocationInfoBySite(String siteId) {
        return getSqlSession().selectList("biela.MapIndex.findGeneralLocationInfoBySite", siteId);
    }

    @Override
    public Integer findAreaNameBySiteId(String siteId) {
        return getSqlSession().selectOne("biela.MapIndex.findAreaCodeBySiteId", siteId);
    }

    @Override
    public AreaCodePO findAreaCodePOByAreaCode(int areaCode) {
        return getSqlSession().selectOne("biela.MapIndex.findAreaCodePOByAreaCode", areaCode);
    }

    @Override
    public List<String> findLocationIdsBySite(String siteId) {
        return getSqlSession().selectList("biela.MapIndex.findLocationIdsBySite", siteId);
    }

    @Override
    public Float findVariances(String locationId, int sensorId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        Object objVariances = getSqlSession().selectOne("biela.MapIndex.findVariances", paramMap);

        if (objVariances == null) {
            return 0F;
        }
        Float variancesVal = Float.parseFloat(objVariances.toString());
        if (variancesVal.isNaN()) {
            return 0F;
        }
        return variancesVal;
    }

    @Override
    public List<SensorInfoPO> findSensorInfo(String siteId) {
        return getSqlSession().selectList(
                "biela.MapIndex.findSensorInfo", siteId);
    }

    @Override
    public LogicGroupPO findLogicGroupBySiteId(String siteId) {
        return getSqlSession().selectOne("biela.MapIndex.findLogicGroupBySiteId", siteId);
    }

    @Override
    public ZonePO findZoneById(String zoneId) {
        return getSqlSession().selectOne("biela.MapIndex.findZoneByZoneId", zoneId);
    }

    @Override
    public void saveCustomize(String siteId, String locationId, Integer sensorId, String customizeRemark) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        paramMap.put("customizeRemark", customizeRemark);
        getSqlSession().insert("biela.MapIndex.saveCustomize", paramMap);
    }

    @Override
    public List<CustomizeVO> findCustomizeVOList(String siteId) {
        return getSqlSession().selectList("biela.MapIndex.findCustomizeVOBySiteId", siteId);
    }

    @Override
    public String findSensorNameBySensorId(Integer sensorId) {
        return getSqlSession().selectOne("biela.MapIndex.findSensorNameBySensorId", sensorId);
    }

    @Override
    public void deleteCustomizeById(Integer id) {
        getSqlSession().delete("biela.MapIndex.deleteCustomizeById", id);
    }

    @Override
    public Integer verifyCustomize(String siteId, String locationId, Integer sensorId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        return getSqlSession().selectOne("biela.MapIndex.verifyCustomize", paramMap);
    }

    @Override
    public Integer customizeCount(String siteId) {
        return getSqlSession().selectOne("biela.MapIndex.customizeCount", siteId);
    }

    @Override
    public LocationInfoVO findNodeSensorInfoVOByDeviceSensorId(String locationId, Integer sensorId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        return getSqlSession().selectOne("biela.MapIndex.findNodeSensorInfoVOByDeviceSensorId", paramMap);
    }

    @Override
    public List<LocationVO> findZoneLocationByZoneIdSensorId(String siteId, String zoneId, int sensorId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("zoneId", zoneId);
        paramMap.put("sensorId", sensorId);
        return getSqlSession().selectList("biela.MapIndex.findZoneLocationByZoneIdSensorId", paramMap);
    }

    @Override
    public List<NodeSensorInfoVO> findSiteRealtimeAvg(List<Integer> sensorIds, int logicGroupId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("list", sensorIds);
        paramMap.put("logicGroupId", logicGroupId);
        return getSqlSession().selectList("biela.MapIndex.findSiteRealtimeAvg", paramMap);
    }
}
