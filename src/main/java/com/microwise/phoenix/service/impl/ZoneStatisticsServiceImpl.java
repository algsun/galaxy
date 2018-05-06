package com.microwise.phoenix.service.impl;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.dao.ThresholdDao;
import com.microwise.blueplanet.proxy.SensorinfoProxy;
import com.microwise.blueplanet.proxy.ZoneProxy;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.bean.po.ZoneData;
import com.microwise.phoenix.bean.vo.ZoneStatistics;
import com.microwise.phoenix.dao.ZoneStatisticsDao;
import com.microwise.phoenix.service.ZoneStatisticsService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * 环境监控： 区域统计分析 service 实现
 *
 * @author xu.baoji
 * @date 2013-7-4
 * @check 2013-07-08 许保吉  svn:4407
 * @check 2013-07-08 @gaohui #4417
 */
@Service
@Transactional
@Phoenix
public class ZoneStatisticsServiceImpl implements ZoneStatisticsService {

    /**
     * 环境监控区域代理
     */
    @Autowired
    private ZoneProxy zoneProxy;

    /**
     * 区域统计分析 dao
     */
    @Autowired
    private ZoneStatisticsDao zoneStatisticsDao;

    @Autowired
    private ThresholdDao thresholdDao;

    /**
     * 监测指标代理
     */
    @Autowired
    private SensorinfoProxy sensorinfoProxy;

    @Override
    public List<ZoneStatistics> findZoneStatistics(String zoneId, Date date, int type) {

        // 获得 区域下的所有子孙区域
        List<String> zoneIds = zoneProxy.findChildrenIdList(zoneId);
        zoneIds.add(zoneId);
        // 查询基本数据
        List<ZoneStatistics> zoneStatistics = findBaseData(zoneIds);
        if (zoneStatistics != null) {
            Date startDate = DateTypeGenerator.start(type, date);
            Date endDate = DateTypeGenerator.end(type, date);
            findData(zoneStatistics, zoneIds, startDate, endDate, type);
        }
        return zoneStatistics;
    }

    @Override
    public List<ZoneData> findRangStatsOfZones(String siteId, int sensorPhysicalId, Date date, int type) {
        Date start = DateTypeGenerator.start(type, date);
        Date end = DateTypeGenerator.end(type, date);
        return getPrecision(zoneStatisticsDao.findRangeStatsOfZones(siteId, sensorPhysicalId, start, end), sensorPhysicalId);
    }

    private List<ZoneData> getPrecision(List<ZoneData> zoneDatas, int sensorId) {
        SensorinfoVO sensorinfoVO = sensorinfoProxy.findByPhysicalid(sensorId);
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(sensorinfoVO.getSensorPrecision());
        for (ZoneData zoneData : zoneDatas) {
            zoneData.setWaveValue(Double.parseDouble(df.format(zoneData.getWaveValue()).replace(",", "")));
        }
        return zoneDatas;
    }

    @Override
    public List<Map<SensorinfoVO, List<ZoneData>>> findRangStatsOfZones(String siteId, Date date, int type) {
        // 自定义监测 指标列表 ：无任何科学依据
        int[] sensorPhysicalIds = new int[]{32, 33, 36, 37, 35, 34};

        List<Map<SensorinfoVO, List<ZoneData>>> resultMaps = new ArrayList<Map<SensorinfoVO, List<ZoneData>>>();
        int num = 0;
        for (int sensorPhysicalId : sensorPhysicalIds) {
            // 查询监测指标信息
            SensorinfoVO sensorinfoVO = sensorinfoProxy.findByPhysicalid(sensorPhysicalId);
            // 查询 监测指标区域统计
            List<ZoneData> zoneDatas = findRangStatsOfZones(siteId, sensorPhysicalId, date, type);
            // 如果该监测指标有数据
            if (zoneDatas.size() > 0) {
                Map<SensorinfoVO, List<ZoneData>> resultMap = new HashMap<SensorinfoVO, List<ZoneData>>();
                resultMap.put(sensorinfoVO, zoneDatas);
                resultMaps.add(resultMap);
                // 记录数 加1
                num++;
            }
            // 有三个监测指标有数据时 跳出
            if (num == 3) {
                continue;
            }
        }
        return resultMaps;
    }

    /**
     * 查询图表基本数据
     *
     * @param zoneIds
     * @return
     * @author xu.baoji
     * @date 2013-7-4
     */
    private List<ZoneStatistics> findBaseData(List<String> zoneIds) {
        return zoneStatisticsDao.findBaseData(zoneIds);
    }

    /**
     * 查询 月 统计数据
     *
     * @param zoneIds
     * @param startDate
     * @param endDate
     * @return
     * @author xu.baoji
     * @date 2013-7-4
     */
    private void findData(List<ZoneStatistics> zoneStatistics,
                          List<String> zoneIds, Date startDate, Date endDate, int type) {
        // 查询每一个 监测指标的图表数据
        for (ZoneStatistics zs : zoneStatistics) {
            List<ZoneData> zoneDatas = zoneStatisticsDao.findZoneData(zoneIds,
                    zs.getSensorPhysicalid(), startDate, endDate);
            Map<Object, ZoneData> maxDatas = new TreeMap<Object, ZoneData>();
            Map<Object, ZoneData> minDatas = new TreeMap<Object, ZoneData>();
            // 获得 同一个 时间点的最大值和 最小值 对象
            if (zoneDatas != null) {
                disposeMinAndMaxData(zoneDatas, maxDatas, minDatas, type);
            }
            getChartData(zs, maxDatas, minDatas);
            // 处理 平均值数据
            zs.setAveDatas(getAvgData(zs.getSensorPhysicalid(), zoneIds,
                    startDate, endDate, type));
        }
    }

    private void disposeMinAndMaxData(List<ZoneData> zoneDatas,
                                      Map<Object, ZoneData> maxDatas, Map<Object, ZoneData> minDatas,
                                      int type) {
        for (ZoneData zoneData : zoneDatas) {
            Object key = null;
            if (type == Constants.FIND_TYPE_MONTH) {
                key = zoneData.getDate();
            } else if (type == Constants.FIND_TYPE_YEAR) {
                key = new DateTime(zoneData.getDate()).getMonthOfYear();
            }
            ZoneData max = maxDatas.get(key);
            ZoneData min = minDatas.get(key);

            // maxValue 为 null, zoneData 不小于 max
            if (max == null || max.getMaxValue() == null) {
                maxDatas.put(key, zoneData);
            }
            // zoneData 比 max 大
            else if (zoneData.getMaxValue() != null && max.getMaxValue() < zoneData.getMaxValue()) {
                maxDatas.put(key, zoneData);
            }

            // minValue 为 null, zoneData 不大于 min
            if (min == null || min.getMinValue() == null) {
                minDatas.put(key, zoneData);
            }
            // zoneData 比 min 小
            else if (zoneData.getMinValue() != null && min.getMinValue() > zoneData.getMinValue()) {
                minDatas.put(key, zoneData);
            }
        }

    }

    /**
     * 获得图表数据
     *
     * @param zs
     * @param maxDatas
     * @param minDatas
     * @param minDatas
     * @return
     * @author xu.baoji
     * @date 2013-7-4
     */
    private void getChartData(ZoneStatistics zs,
                              Map<Object, ZoneData> maxDatas, Map<Object, ZoneData> minDatas) {
        // 将最大值和最小值组装成 返回对象
        List<List<Object>> maxAndMinData = new ArrayList<List<Object>>();
        List<List<Object>> maxAndMinDates = new ArrayList<List<Object>>();
        Set<Object> maxKeySet = maxDatas.keySet();

        //@xu.baoji  说明 最大值和最小值map 使用相同key 使用entrySet 同样不能避免要取map 可以
        for (Object key : maxKeySet) {
            if (maxDatas.get(key) != null) {
                zs.setHasData(true);
            }
            ZoneData max = maxDatas.get(key);
            ZoneData min = minDatas.get(key);
            List<Object> maxAndMin = new ArrayList<Object>();
            maxAndMin.add(max.getDate().getTime());
            maxAndMin.add(max.getMaxValue());
            maxAndMin.add(min.getMinValue());

            maxAndMinData.add(maxAndMin);

            List<Object> maxAndMinDate = new ArrayList<Object>();
            maxAndMinDate.add(max.getMaxDate());
            maxAndMinDate.add(min.getMinDate());

            maxAndMinDates.add(maxAndMinDate);
        }
        zs.setMaxAndMinDatas(maxAndMinData);
        zs.setMaxAndMinDate(maxAndMinDates);
    }

    /**
     * 查询 平均 值数据
     *
     * @param sensorPhysicalid
     * @param zoneIds
     * @return
     * @author xu.baoji
     * @date 2013-7-4
     */
    private List<List<Object>> getAvgData(int sensorPhysicalid,
                                          List<String> zoneIds, Date startDate, Date endDate, int type) {
        List<Map<String, Object>> avgMap = zoneStatisticsDao.findAvgData(
                zoneIds, sensorPhysicalid, startDate, endDate, type);
        List<List<Object>> avgList = new ArrayList<List<Object>>();
        for (Map<String, Object> avg : avgMap) {
            List<Object> avgs = new ArrayList<Object>();
            avgs.add(((Date) avg.get("date")).getTime());
            avgs.add(avg.get("avgValue"));
            avgList.add(avgs);
        }
        return avgList;
    }
}
