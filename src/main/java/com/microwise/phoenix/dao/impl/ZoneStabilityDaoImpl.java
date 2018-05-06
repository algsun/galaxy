package com.microwise.phoenix.dao.impl;

import com.google.common.collect.ArrayListMultimap;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZoneStability;
import com.microwise.phoenix.dao.ZoneStabilityDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * 区域稳定性Dao
 *
 * @author zhangpeng
 * @date 13-8-7
 * @check @wang.geng 2013年8月14日 #4927
 */
@Beans.Dao
@Phoenix
public class ZoneStabilityDaoImpl extends PhoenixBaseDao implements ZoneStabilityDao {

    @Override
    public List<ZoneStability> findLocations(String siteId, int sensorPhysicalId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("sensorPhysicalid", sensorPhysicalId);
        //TODO findLocations
        List<Map<String, Object>> locations = getSqlSession().selectList("phoenix.mybatis.ZoneStabilityDao.findDevices", paramMap);
        // zoneId => device
        Map<String, ZoneStability> zoneStabilityMap = getStringZoneStabilityMap(locations);

        return new ArrayList<ZoneStability>(zoneStabilityMap.values());
    }

    @Override
    public List<ZoneStability> findLocationsByZone(String siteId, String zoneId, int sensorPhysicalId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("zoneId", zoneId);
        paramMap.put("sensorPhysicalid", sensorPhysicalId);
        //TODO findLocations
        List<Map<String, Object>> locations = getSqlSession().selectList("phoenix.mybatis.ZoneStabilityDao.findDevicesByZone", paramMap);
        Map<String, ZoneStability> zoneStabilityMap = getStringZoneStabilityMap(locations);


        return new ArrayList<ZoneStability>(zoneStabilityMap.values());
    }

    private Map<String, ZoneStability> getStringZoneStabilityMap(List<Map<String, Object>> locations) {
        // zoneId => device
        Map<String, ZoneStability> zoneStabilityMap = new HashMap<String, ZoneStability>();
        for (Map<String, Object> device : locations) {
            ZoneStability zoneStability = new ZoneStability();
            zoneStability.setZoneId((String) device.get("zoneId"));
            zoneStability.setZoneName((String) device.get("zoneName"));
            zoneStabilityMap.put((String) device.get("zoneId"), zoneStability);
        }
        // zoneId => List<deviceId>
        ArrayListMultimap<String, String> devicesOfZone = ArrayListMultimap.create();
        for (Map<String, Object> device : locations) {
            devicesOfZone.put((String) device.get("zoneId"), (String) device.get("locationId"));
        }
        for (Map.Entry<String, ZoneStability> entry : zoneStabilityMap.entrySet()) {
            entry.getValue().setLocationIds(devicesOfZone.get(entry.getKey()));
        }
        return zoneStabilityMap;
    }

    @Override
    public List<Float> findVariances(List<String> locationIdList, int sensorPhysicalid, Date startDate, Date endDate) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, true);//用于批量update
        List<Float> variances = new ArrayList<Float>();
        for (String locationId : locationIdList) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("locationId", locationId);
            paramMap.put("sensorPhysicalid", sensorPhysicalid);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            Float variance = session.selectOne("phoenix.mybatis.ZoneStabilityDao.findVariances", paramMap);
            if (variance != null) {
                variances.add(variance);
            }
        }
        session.commit();
        session.clearCache();
        session.close();
        return variances;
    }

}
