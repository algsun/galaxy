package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.DeviceState;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.DeviceDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备dao 实现
 *
 * @author xubaoji
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1770
 * @check 2013-04-22 xubaoji svn:2629
 */
@Dao
@Blueplanet
public class DeviceDaoImpl extends BlueplanetBaseDao implements DeviceDao {

    @Override
    public List<DeviceVO> findSlaveModule(String masterModuleDeviceId) {
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findSlaveModule",
                masterModuleDeviceId);
    }

    @Override
    public List<DeviceVO> findDeviceList(String siteId, String deviceId, int deviceType, int index, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("deviceId", deviceId);
        map.put("deviceType", deviceType);
        map.put("start", (index - 1) * pageSize);
        map.put("pageSize", pageSize);
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findDeviceList", map);
    }

    @Override
    public int findDeviceListCount(String siteId, String deviceId, int deviceType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("deviceId", deviceId);
        map.put("deviceType", deviceType);
        return getSqlSession().<Integer>selectOne(
                "blueplanet.mybatis.DeviceDao.findDeviceListCount", map);
    }

    @Override
    public long findDataVersion(String deviceId) {
        return getSqlSession().<Long>selectOne(
                "blueplanet.mybatis.DeviceDao.findDataVersion", deviceId);
    }

    @Override
    public DeviceVO findDeviceById(String deviceId) {
        return getSqlSession().selectOne(
                "blueplanet.mybatis.DeviceDao.findDeviceById", deviceId);
    }

    @Override
    public List<SensorinfoVO> findSensorinfo(String deviceId,
                                             Integer activeState, String language) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceId", deviceId);
        map.put("activeState", activeState);
        map.put("language", language);
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findSensorinfo", map);
    }

    @Override
    public List<Integer> findSensorPhysicalid(String nodeId, Integer activeState) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nodeId", nodeId);
        map.put("activeState", activeState);
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findSensorPhysicalid", map);
    }

    @Override
    public List<DeviceDataVO> findNodesensor(String nodeId,
                                             List<Integer> sensorPhysicalidList) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("sensorPhysicalIdList", sensorPhysicalidList);
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findNodesensor", paramMap);
    }

    @Override
    public List<DeviceDataVO> findHistoryNodesensor(String nodeId,
                                                    Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findHistoryNodesensor", paramMap);
    }

    @Override
    public Map<String, Date> findMaxAndMinTime(String nodeId, Date startTime,
                                               Date endTime, Integer index, Integer pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("endTime", endTime);
        if (index != null && pageSize != null) {
            paramMap.put("start", (index - 1) * pageSize);
            paramMap.put("pageSize", pageSize);
        }
        paramMap.put("startTime", startTime);
        return getSqlSession().selectOne(
                "blueplanet.mybatis.DeviceDao.findMaxAndMinTime", paramMap);
    }

    @Override
    public List<HistoryDataVO> findHistoryData(String nodeId, Date startTime,
                                               Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("endTime", endTime);
        paramMap.put("startTime", startTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findHistoryData", paramMap);
    }

    @Override
    public int findHistoryDataCount(String nodeId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("endTime", endTime);
        paramMap.put("startTime", startTime);
        return getSqlSession().<Integer>selectOne(
                "blueplanet.mybatis.DeviceDao.findHistoryDataCount", paramMap);
    }

    @Override
    public void updateDevices(List<DeviceVO> devices) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,
                true);
        for (DeviceVO deviceVO : devices) {
            session.update("blueplanet.mybatis.DeviceDao.updateDevices",
                    deviceVO);
        }
        session.commit();
        session.clearCache();
        session.close();
    }

    @Override
    public void deleteDevice(String nodeId) {
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.updateLocationNodeIdToNull", nodeId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteDeviceNodeSensor", nodeId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteDeviceNodeInfo", nodeId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteDeviceNodeInfoMemory", nodeId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteDeviceCustomFormula", nodeId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteDeviceCustomFormulaParam", nodeId);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("nodeId", nodeId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.dropDeviceStatusTable", param);
        deleteControlModuleData(nodeId);
    }

    /**
     * 删除控制模块相关数据
     *
     * @param deviceId
     */
    private void deleteControlModuleData(String deviceId) {
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteControlModuleStatus", deviceId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteControlModuleConditionRefl", deviceId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteControlModuleSwitch", deviceId);
        getSqlSession().delete("blueplanet.mybatis.DeviceDao.deleteControlModuleSwitchChange", deviceId);
    }

    @Override
    public void updateDevicesInterval(DeviceVO device) {
        getSqlSession().update("blueplanet.mybatis.DeviceDao.updateDevicesInterval", device);
    }

    @Override
    public void updateDeviceType(String deviceId, int deviceType) {
        Map<String, Object> paramMap = new HashedMap();
        paramMap.put("deviceId", deviceId);
        paramMap.put("deviceType", deviceType);
        getSqlSession().update("blueplanet.mybatis.DeviceDao.updateDeviceType", paramMap);
    }

    @Override
    public List<DeviceVO> findDevicesByType(String siteId, int type) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("deviceType", type);
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findDevicesByType", paramMap);
    }

    @Override
    public List<DeviceVO> findDevices(String siteId, int nodeVersion, int parentIP) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("nodeVersion", nodeVersion);
        paramMap.put("parentIP", parentIP);
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findDevicesByVersionAndParentIP", paramMap);
    }

    @Override
    public List<DeviceVO> findV13Devices(String siteId, int subNet, int parentIP) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("subNet", subNet);
        paramMap.put("parentIP", parentIP);
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findDevicesBySubNetAndParentIP", paramMap);
    }

    @Override
    public List<DeviceVO> findDevicesBySiteId(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findDevicesBySiteId", siteId);
    }

    @Override
    public List<DeviceVO> findDevicesByZoneId(String zoneId) {
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findDevicesByZoneId", zoneId);
    }

    @Override
    public String findRainDeviceState(String deviceId, Date createTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("createTime", createTime);
        return getSqlSession().selectOne("blueplanet.mybatis.DeviceDao.findRainDeviceState", paramMap);
    }

    @Override
    public List<DeviceState> findHistoryLowVoltage(String deviceId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("endTime", endTime);
        paramMap.put("startTime", startTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findHistoryLowVoltage", paramMap);
    }

    @Override
    public List<ProductStateVO> findProductStateVO(String siteId) {
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findProductState", siteId);
    }

    @Override
    public List<ProductStateVO> findProductStateVONoSn(String siteId) {
        return getSqlSession().selectList(
                "blueplanet.mybatis.DeviceDao.findProductStateNoSn", siteId);
    }

    @Override
    public String findDeviceId(String sn) {
        return getSqlSession().selectOne("blueplanet.mybatis.DeviceDao.findDeviceId", sn);
    }

    @Override
    public String findContent(String deviceId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        return getSqlSession().selectOne("blueplanet.mybatis.DeviceDao.findContent", paramMap);
    }

    @Override
    public String findLastFaultCode(String deviceId) {
        Map<String, Object> paramMap = new HashedMap();
        paramMap.put("deviceId", deviceId);
        return getSqlSession().selectOne("blueplanet.mybatis.DeviceDao.findLastFaultCode", paramMap);
    }

    @Override
    public List<String> findContents(String deviceId, int count) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("count", count);
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findContents", paramMap);
    }

    @Override
    public List<String> findHumidities(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findHumidities", siteId);
    }

    @Override
    public List<String> findAirConditioners(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findAirConditioners", siteId);
    }

    @Override
    public List<String> findControlDevices(String siteId, int deviceType) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("siteId", siteId);
        paramMap.put("deviceType", deviceType);
        return getSqlSession().selectList("blueplanet.mybatis.DeviceDao.findControlDevices", paramMap);
    }
}
