package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.WindRoseVO;
import com.microwise.blueplanet.dao.ChartDao;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.dao.SensorinfoDao;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.Constants.ChartConstants;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图表service 实现
 *
 * @author xubaoji
 * @date 2013-2-27
 * @check 2013-03-08 zhangpeng svn:1991
 */
@Service
@Transactional
@Blueplanet
public class ChartServiceImpl implements ChartService {

    /**
     * chartDao 图表dao
     */
    @Autowired
    private ChartDao chartDao;
    /**
     * chartDao 图表dao
     */
    @Autowired
    private LocationDao locationDao;
    /**
     * 监测指标dao
     */
    @Autowired
    private SensorinfoDao sensorinfoDao;

    @Override
    public List<ChartVO> findBasicChart(String locationId,
                                        List<Integer> sensorInfoList, Date startTime, Date endTime) {
        List<ChartVO> chartDataList = new ArrayList<ChartVO>();
        for (int sensorInfoId : sensorInfoList) {
            SensorinfoVO sensorinfoVO = sensorinfoDao.findByPhysicalid(sensorInfoId);
            ChartVO chartVO = new ChartVO(sensorInfoId,
                    sensorinfoVO.getCnName(),
                    sensorinfoVO.getEnName(),
                    sensorinfoVO.getUnits(),
                    sensorinfoVO.getSensorPrecision()
            );
            // 图表数据 的总数量
            int count = chartDao.findBasicChartCount(locationId, sensorInfoId, startTime, endTime);
            if (count == 0) {// 如果当前条件下 没有图表数据跳出本次循环
                continue;
            }
            // 图表数据 临界 量
            int basicChartModulo = Integer.parseInt(ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL).get("BasicChart.module"));
            // 对图表数据取模处理的变量
            int moduloConstant = ChartConstants.MODULE_NONE;
            if (count > basicChartModulo) {
                moduloConstant = Math.round(count / (float) basicChartModulo);
            }
            List<Map<String, Object>> chartData = chartDao.findBasicChart(locationId, sensorInfoId, startTime, endTime, moduloConstant);
            chartVO.setChartData(chartData);
            chartDataList.add(chartVO);
        }
        return chartDataList;
    }

    @Override
    public List<ChartVO> findAverageChart(String locationId, List<Integer> sensorinfoList, Date startTime, Date endTime) {
        List<ChartVO> chartDataList = new ArrayList<ChartVO>();
        for (Integer sensorinfoId : sensorinfoList) {
            SensorinfoVO sensorinfoVO = sensorinfoDao.findByPhysicalid(sensorinfoId);
            ChartVO chartVO = new ChartVO(sensorinfoId,
                    sensorinfoVO.getCnName(),
                    sensorinfoVO.getEnName(),
                    sensorinfoVO.getUnits(),
                    sensorinfoVO.getSensorPrecision()
            );
            // 图表数据 的总数量
            Integer count = locationDao.findAvgData(sensorinfoId, startTime, endTime, locationId).size();
            if (count == 0) {// 如果当前条件下 没有图表数据跳出本次循环
                continue;
            }
            //为使用通用接口，将chartData的map的key由date和avgValue替换成time，data
            List<Map<String, Object>> chartData = locationDao.findAvgData(sensorinfoId, startTime, endTime, locationId);
            List<Map<String, Object>> chartData1 = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> map : chartData) {
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("time", map.get("date"));
                map1.put("data", map.get("avgValue"));
                chartData1.add(map1);
            }
            chartVO.setChartData(chartData1);
            chartDataList.add(chartVO);
        }
        return chartDataList;
    }

    @Override
    public List<ChartVO> findRainfall(String locationId,
                                      List<Integer> sensorinfoList, Integer dateType, Date time) {
        List<ChartVO> chartDataList = new ArrayList<ChartVO>();
        // 判断监测指标列表 中是否存在降雨量指标
        if (sensorinfoList.contains(ChartConstants.SENSORINFO_RB)) {
            // 首先获得降雨量 图表数据信息
            findRainfull(ChartConstants.SENSORINFO_RB, locationId, dateType,
                    time, chartDataList);
            // 如果当前查询条件下降雨量图表没有数据 返回 空集合
            if (chartDataList.size() == 0
                    || chartDataList.get(0).getChartData().size() == 0) {
                return chartDataList;
            }
            // 将降雨量指标从监测指标列表移除
            sensorinfoList.remove(ChartConstants.SENSORINFO_RB);
            // 获得其他监测指标的图表数据 并添加到集合中
            for (Integer sensorPhysicalid : sensorinfoList) {
                findRainfull(sensorPhysicalid, locationId, dateType, time,
                        chartDataList);
            }
        }
        return chartDataList;
    }

    /**
     * 封装查询降雨量相关业务
     */
    private void findRainfull(Integer sensorPhysicalid, String locationId,
                              Integer dateType, Date time, List<ChartVO> chartDataList) {
        SensorinfoVO sensorinfoVO = sensorinfoDao
                .findByPhysicalid(sensorPhysicalid);
        ChartVO chartVO = new ChartVO(sensorPhysicalid,
                sensorinfoVO.getCnName(), sensorinfoVO.getEnName(),
                sensorinfoVO.getUnits(), sensorinfoVO.getSensorPrecision());
        DateTime dateTime = new DateTime(time);
        Integer year = dateTime.getYear();
        Integer month = dateTime.getMonthOfYear();
        Integer day = dateTime.getDayOfMonth();

        List<Map<String, Object>> chartData;
        if (sensorPhysicalid.equals(ChartConstants.SENSORINFO_RB)) {
            chartData = findRainfallChartData(locationId, dateType, year, month, day);
        } else {
            chartData = findCurveDataForRainfall(locationId, sensorPhysicalid, dateType, year, month, day, sensorinfoVO.getSensorPrecision());
        }
        chartVO.setChartData(chartData);
        chartDataList.add(chartVO);
    }

    /**
     * 获得降雨量指标的图表数据
     *
     * @param locationId 位置点id
     * @param dateType   查询类型
     * @param year       查询日期
     * @return List<Map<String, Object>> 降雨量指标的 图表数据
     * @author liuzhu
     * @date 2014-7-1
     */
    private List<Map<String, Object>> findRainfallChartData(String locationId,
                                                            int dateType, int year, int month, int day) {
        List<Map<String, Object>> chartData = null;
        switch (dateType) {
            case Constants.FIND_TYPE_DAY:
                chartData = chartDao.findDayRainfall(locationId, new DateTime(year,
                        month, day, 8, 0, 0).toDate(), getRainEndDateTime(year, month,
                        day + 1).toDate());
                break;
            case Constants.FIND_TYPE_MONTH:
                chartData = chartDao.findMonthRainfall(locationId, new DateTime(year,
                        month, 1, 8, 0).toDate(), getRainEndDateTime(year, month + 1, 1).minusDays(1).toDate());
                break;
            case Constants.FIND_TYPE_YEAR:
                String years = String.valueOf(year);
                Date date = new DateTime(years).toDate();
                Date startTime = DateTimeUtil.startOfYear(date);
                Date endTime = DateTimeUtil.endOfYear(date);
                chartData = disposeDataTime(
                        chartDao.findYearRainfall(locationId, startTime, endTime),
                        "yyyy-MM");
                break;
        }
        return chartData;
    }

    /**
     * 处理降雨量图片 结束时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     * @author 许保吉
     * @date 2013-4-3
     */
    private DateTime getRainEndDateTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (month > 12 ? 11 : month - 1));
        if (day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            day = 1;
            month = month + 1;
        }
        if (month > 12) {
            year = year + 1;
            month = 1;
            day = 1;
        }
        return new DateTime(year, month, day, 8, 0);
    }

    /**
     * 获得降雨量图表 的其他指标 数据
     *
     * @param locationId       位置点id
     * @param sensorPhysicalid 监测指标标识
     * @param dateType         查询类型
     * @param year             查询日期
     * @return List<Map<String, Object>> 降雨量 其他指标的 图表数据
     * @author liuzhu
     * @date 2014-7-1
     */
    private List<Map<String, Object>> findCurveDataForRainfall(String locationId,
                                                               int sensorPhysicalid, int dateType, int year,
                                                               int month, int day, int sensorPrecision) {
        List<Map<String, Object>> chartData = null;
        switch (dateType) {
            case Constants.FIND_TYPE_DAY:
                chartData = disposeDataTime(chartDao.findBasicForDayRainfall(
                        locationId, sensorPhysicalid, new DateTime(year, month, day,
                        8, 0, 0).toDate(), getRainEndDateTime(year, month,
                        day + 1).toDate(), sensorPrecision
                ),
                        "yyyy-MM-dd HH"
                );
                break;
            case Constants.FIND_TYPE_MONTH:
                chartData = chartDao.findBasicForMonthRainfall(locationId,
                        sensorPhysicalid, new DateTime(year, month, 1, 8, 0)
                        .toDate(), getRainEndDateTime(year, month + 1, 1)
                        .minusDays(1).toDate()
                );
                break;
            case Constants.FIND_TYPE_YEAR:
                Date yearTime = new DateTime(String.valueOf(year)).toDate();
                Date startTime = DateTimeUtil.startOfYear(yearTime);
                Date endTime = DateTimeUtil.endOfYear(yearTime);
                chartData = disposeDataTime(chartDao.findBasicForYearRainfall(
                        locationId, sensorPhysicalid, startTime, endTime,
                        sensorPrecision), "yyyy-MM");
                break;
        }
        return chartData;
    }

    @Override
    public ChartVO findEvaporation(String locationId, Integer dateType, Date startTime, Date endTime) {
        try {
            SensorinfoVO sensorinfo = sensorinfoDao
                    .findByPhysicalid(ChartConstants.SENSORINFO_EVAP);
            List<Map<String, Object>> chartDataList = new ArrayList<Map<String, Object>>();
            switch (dateType) {
                //当事件类型为Constants.FIND_TYPE_DAY时，即自定义时间段，按照天累计，
                //使用按月查找的接口，区别不处理时间
                case Constants.FIND_TYPE_DAY:
                    chartDataList = chartDao.findMonthEvaporation(
                            locationId, startTime, endTime);
                    changeDateFormat(chartDataList, "yyyy-MM-dd");
                    break;
                case Constants.FIND_TYPE_MONTH:
                    Date startOfMonth = DateTimeUtil.startOfMonth(endTime);
                    Date endOfMonth = DateTimeUtil.endOfMonth(endTime);
                    chartDataList = chartDao.findMonthEvaporation(
                            locationId, startOfMonth, endOfMonth);
                    changeDateFormat(chartDataList, "yyyy-MM-dd");
                    break;
                case Constants.FIND_TYPE_YEAR:
                    Date startOfYear = DateTimeUtil.startOfYear(endTime);
                    Date endOfYear = DateTimeUtil.endOfYear(endTime);
                    chartDataList = chartDao.findYearEvaporation(
                            locationId, startOfYear, endOfYear);
                    changeDateFormat(chartDataList, "yyyy-MM");
                    break;
            }
            ChartVO chartVO = new ChartVO(ChartConstants.SENSORINFO_EVAP,
                    sensorinfo.getCnName(), sensorinfo.getEnName(),
                    sensorinfo.getUnits(), sensorinfo.getSensorPrecision());
            chartVO.setChartData(chartDataList);
            return chartVO;
        } catch (Exception e) {
            return null;
        }
    }


    private void changeDateFormat(List<Map<String, Object>> chartDataList, String dateFormat) throws ParseException {
        for (Map<String, Object> map : chartDataList) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            Date d = format.parse(map.get("time").toString());
            map.put("time", d);
        }
    }

    @Override
    public ChartVO findLight(String locationId, int dateType, Date time) {
        SensorinfoVO sensorinfo = sensorinfoDao
                .findByPhysicalid(ChartConstants.SENSORINFO_LX);
        ChartVO chartVO = new ChartVO(sensorinfo.getId(),
                ChartConstants.LX_H_NAME, sensorinfo.getEnName(),
                sensorinfo.getUnits() + "·h", sensorinfo.getSensorPrecision());
        List<Map<String, Object>> chartData = null;
        Date startTime;
        Date endTime;
        switch (dateType) {
            case Constants.FIND_TYPE_DAY:
                startTime = DateTimeUtil.startOfDay(time);
                endTime = DateTimeUtil.endOfDay(time);
                chartData = chartDao.findDayLight(locationId, startTime, endTime);
                break;
            case Constants.FIND_TYPE_MONTH:
                startTime = DateTimeUtil.startOfMonth(time);
                endTime = DateTimeUtil.endOfMonth(time);
                chartData = chartDao.findMonthLight(locationId, startTime, endTime);
                break;
            case Constants.FIND_TYPE_YEAR:
                startTime = DateTimeUtil.startOfYear(time);
                endTime = DateTimeUtil.endOfYear(time);
                chartData = disposeDataTime(chartDao.findYearLight(locationId, startTime, endTime),
                        "yyyy-MM");
                break;
        }
        chartVO.setChartData(chartData);
        return chartVO;
    }

    @Override
    public WindRoseVO findWindRose(String locationId, int dateType, Date time, Date startDate, Date endDate) {
        WindRoseVO windRoseVO = null;
        switch (dateType) {
            case Constants.FIND_TYPE_DAY:
                windRoseVO = chartDao.findDayWindRose(locationId, startDate, endDate);
                break;
            case Constants.FIND_TYPE_MONTH:
                Date startOfMonth = DateTimeUtil.startOfMonth(time);
                Date endOfMonth = DateTimeUtil.endOfMonth(time);
                windRoseVO = chartDao.findDayWindRose(locationId, startOfMonth, endOfMonth);
                break;
            case Constants.FIND_TYPE_YEAR:
                Date startOfYear = DateTimeUtil.startOfYear(time);
                Date endOfYear = DateTimeUtil.endOfYear(time);
                windRoseVO = chartDao.findDayWindRose(locationId, startOfYear, endOfYear);
                break;
        }
        return windRoseVO;
    }

    /**
     * 按指定格式处理 图表数据的时间类型
     *
     * @param chartData  图表原始数据
     * @param dateFormat 数据需要的时间格式
     * @return List<Map<String, Object>> 处理格式之后的数据
     * @author 许保吉
     * @date 2013-3-5
     */
    private List<Map<String, Object>> disposeDataTime(
            List<Map<String, Object>> chartData, String dateFormat) {
        for (Map<String, Object> map : chartData) {
            map.put(ChartConstants.CHARTDATA_KEY_TIME,
                    DateTimeUtil.dateFormatReDate(dateFormat,
                            (Date) map.get(ChartConstants.CHARTDATA_KEY_TIME))
            );
        }
        return chartData;
    }

    @Override
    public ChartVO findEvaporationRecentDay(String locationId, int dayCount) {
        Date endTime = DateTime.now().toDate();
        Date startTime = DateTime.now().minusDays(dayCount).toDate();
        ChartVO chartVO = getChartVO(locationId, endTime, startTime);
        return chartVO;
    }

    @Override
    public ChartVO findEvaporationRecentMonth(String locationId, int monthCount) {
        Date endTime = DateTime.now().toDate();
        Date startTime = DateTime.now().minusMonths(monthCount).toDate();
        ChartVO chartVO = getChartVO(locationId, endTime, startTime);
        return chartVO;
    }

    private ChartVO getChartVO(String locationId, Date endTime, Date startTime) {
        SensorinfoVO sensorinfo = sensorinfoDao
                .findByPhysicalid(ChartConstants.SENSORINFO_EVAP);
        List<Map<String, Object>> chartDataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = chartDao.findRecentEvaporation(locationId, startTime, endTime);
        chartDataList.add(map);
        ChartVO chartVO = new ChartVO(ChartConstants.SENSORINFO_EVAP,
                sensorinfo.getCnName(), sensorinfo.getEnName(),
                sensorinfo.getUnits(), sensorinfo.getSensorPrecision());
        if (map != null) {
            chartVO.setChartData(chartDataList);
        }
        return chartVO;
    }

}
