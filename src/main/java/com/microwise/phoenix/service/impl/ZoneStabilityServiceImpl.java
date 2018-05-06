package com.microwise.phoenix.service.impl;

import com.google.common.math.DoubleMath;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZoneStability;
import com.microwise.phoenix.dao.ZoneStabilityDao;
import com.microwise.phoenix.service.ZoneStabilityService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 区域稳定性Service实现
 *
 * @author zhangpeng
 * @date 13-8-7
 * @check @wang.geng 2013年8月14日 #4950
 */
@Phoenix
@Beans.Service
@Transactional
public class ZoneStabilityServiceImpl implements ZoneStabilityService {

    /**
     * 区域稳定性Dao
     */
    @Autowired
    private ZoneStabilityDao zoneStabilityDao;
    /**
     * 区域Dao
     */
    @Autowired
    private ZoneDao zoneDao;

    @Override
    public List<ZoneStability> findZoneStability(String siteId, int sensorPhysicalid, Date date, int type) {
        // 查询站点下拥有指定指标的设备所在的区域列表，同时ZoneStability的nodeIdArray字段是逗号分隔拼接的设备id字符串
        List<ZoneStability> zoneStabilityList = zoneStabilityDao.findLocations(siteId, sensorPhysicalid);
        caculateStability(sensorPhysicalid, date, type, zoneStabilityList);
        return zoneStabilityList;
    }

    @Override
    public List<ZoneStability> findZoneStabilityByZone(String siteId, String zoneId, int sensorPhysicalid, Date date, int type) {
        List<String> zoneIds = zoneDao.findChildrenIdList(zoneId);
        List<ZoneStability> zoneStabilityList = new ArrayList<ZoneStability>();
        for (String id : zoneIds) {
            List<ZoneStability> zoneStabilityLists = zoneStabilityDao.findLocationsByZone(siteId, id, sensorPhysicalid);
            caculateStability(sensorPhysicalid, date, type, zoneStabilityLists);
            zoneStabilityList.addAll(zoneStabilityLists);
        }
        return zoneStabilityList;
    }


    /**
     * 将方差集合取平均值开根获取一个区域某指标的标准差，即稳定性
     *
     * @param variances 区域某指标的方差集合
     * @date 13-8-7
     */
    private float getStandardDeviation(List<Float> variances) {
        float variance = (float) DoubleMath.mean(variances);
        DecimalFormat df2 = new DecimalFormat("###.00");
        variance = Float.valueOf(df2.format(variance));
        return Float.valueOf(df2.format(Math.sqrt(variance)));
    }

    private void caculateStability(int sensorPhysicalid, Date date, int type, List<ZoneStability> zoneStabilityList) {
        // 获取查询时间段
        Date start = DateTypeGenerator.start(type, date);
        Date end = DateTypeGenerator.end(type, date);
        // 获取区域下每个设备指定时间段的方差，分别用其所有设备的方差和求平均，就是区域的方差，开根获得区域标准差
        Iterator<ZoneStability> it = zoneStabilityList.iterator();
        while (it.hasNext()) {
            ZoneStability zoneStability = it.next();
            List<String> list = zoneStability.getLocationIds();
            List<Float> variances = zoneStabilityDao.findVariances(list, sensorPhysicalid, start, end);
            if (variances.size() > 0) {
                float stability = getStandardDeviation(variances);
                zoneStability.setStability(stability);
            } else {
                it.remove();
            }
        }
    }

}
