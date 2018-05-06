package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.EvaluateCriterionPO;
import com.microwise.blueplanet.bean.po.ReplaceSensorPO;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.QCMVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.dao.QCMDao;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.QCMService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import org.elasticsearch.common.Strings;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * QCM service实现
 *
 * @author liuzhu
 * @date 2015-5-7.
 */
@Beans.Service
@Transactional
@Blueplanet
public class QCMServiceImpl implements QCMService {

    @Autowired
    private QCMDao qcmDao;

    @Autowired
    private LocationService locationService;

    @Override
    public List<QCMVO> findQCM(String locationId, Date startDate, Date endDate, int sensorId) {
        return qcmDao.findQCM(locationId, startDate, endDate, sensorId);
    }

    @Override
    public Map<String, List<ReplaceSensorPO>> assembleQCMLevel(String locationId, Date start, Date end) {

        //换探头时间集合
        List<Date> dateList = qcmDao.findQCMReplaceSensorDate(locationId);

        //最大时间 最小时间
        DateTime minTime = new DateTime(start);
        DateTime maxTime = new DateTime(end);

        DateTime maxTimeTemp = maxTime;

        //月份个数
        int monthNum = 0;

        //最终返回的map对象
        Map<String, List<ReplaceSensorPO>> stringListMap = new TreeMap<String, List<ReplaceSensorPO>>().descendingMap();
        if (dateList.size() == 0) {//没有换过单位探头，一个生命周期（m_replace_sensor表中，该位置点没有数据）
            //默认30天统计一次
            Period period = new Period(minTime, maxTime, PeriodType.days());
            int day = period.getDays();
            String stringStartDate = DateTimeUtil.dateFormatReString("yyyy-MM-dd HH:mm", minTime.toDate());
            String stringEndDate = DateTimeUtil.dateFormatReString("yyyy-MM-dd HH:mm", maxTime.toDate());
            String key = stringStartDate.trim() + "--" + stringEndDate.trim();
            if (day <= 30) {
                List<ReplaceSensorPO> replaceSensors = getReplaceSensorPOs(locationId, minTime, maxTime);

                stringListMap.put(key, replaceSensors);
            } else {
                List<ReplaceSensorPO> replaceSensors = new ArrayList<ReplaceSensorPO>();
                monthNum = getMonth(monthNum, day);
                Date endDate = maxTime.toDate();
                DateTime startTimeTemp = minTime;
                handleMonthData(locationId, monthNum, replaceSensors, endDate, startTimeTemp);
                for (ReplaceSensorPO rs : replaceSensors) {
                    getReplaceSensor(locationId, rs);
                }
                stringListMap.put(key, replaceSensors);
            }
        } else {//换过探头
            // dateList 为m_replace_sensor的数据 位置点的生命周期为 dateList.size
            for (int k = 0; k <= dateList.size(); k++) {
                Date endDate;
                if (k == 0) {
                    endDate = dateList.get(k);
                } else {
                    minTime = maxTime;
                    if (k >= dateList.size()) {
                        endDate = maxTimeTemp.toDate();
                    } else {
                        endDate = dateList.get(k);
                    }
                }
                maxTime = new DateTime(endDate);
                Period period = new Period(minTime, maxTime, PeriodType.days());
                int day = period.getDays();

                String stringStartDate = DateTimeUtil.dateFormatReString("yyyy-MM-dd HH:mm", minTime.toDate());
                String stringEndDate = DateTimeUtil.dateFormatReString("yyyy-MM-dd HH:mm", maxTime.toDate());
                String key = stringStartDate.trim() + "--" + stringEndDate.trim();

                if (day < 30) {
                    // 组织30天内数据
                    List<ReplaceSensorPO> replaceSensors = getReplaceSensorPOs(locationId, minTime, maxTime);
                    stringListMap.put(key, replaceSensors);
                } else {
                    List<ReplaceSensorPO> replaceSensors = new ArrayList<ReplaceSensorPO>();
                    monthNum = getMonth(monthNum, day);
                    DateTime startTimeTemp = minTime;
                    handleMonthData(locationId, monthNum, replaceSensors, endDate, startTimeTemp);
                    for (ReplaceSensorPO rs : replaceSensors) {
                        getReplaceSensor(locationId, rs);
                    }
                    stringListMap.put(key, replaceSensors);
                }
            }
        }
        for (String key : stringListMap.keySet()) {
            List<ReplaceSensorPO> replaceSensorPOs = stringListMap.get(key);
            if (replaceSensorPOs.size() != 1) {
                List<ReplaceSensorPO> replaceSensorList = new ArrayList<ReplaceSensorPO>();
                for (int i = replaceSensorPOs.size()-1; i >= 0; i--) {
                    replaceSensorList.add(replaceSensorPOs.get(i));
                }
                stringListMap.put(key, replaceSensorList);
            }
        }
        return stringListMap;
    }

    @Override
    public List<EvaluateCriterionPO> findEvaluateCriterions() {
        return qcmDao.findEvaluateCriterions();
    }

    @Override
    public List<Date> findQCMMinMaxDate(String locationId) {
        return qcmDao.findQCMMinMaxDate(locationId);
    }

    /**
     * 处理月份数据
     *
     * @param monthNum
     * @param replaceSensors
     * @param endDate
     * @param startTimeTemp
     */
    private void handleMonthData(String locationId, int monthNum, List<ReplaceSensorPO> replaceSensors, Date endDate, DateTime startTimeTemp) {
        DateTime endTimeTemp = new DateTime(endDate);
        for (int i = 1; i <= monthNum; i++) {
            if (i == 1) {
                endTimeTemp = startTimeTemp.plusDays(30);
            } else {
                startTimeTemp = endTimeTemp;
                endTimeTemp = startTimeTemp.plusDays(30);
                if (endDate.before(endTimeTemp.toDate())) {
                    endTimeTemp = new DateTime(endDate);
                }
            }
            handleMinTimeMaxTime(locationId, startTimeTemp, endTimeTemp, replaceSensors);
        }
    }

    /**
     * 计算月份数
     *
     * @param monthNum
     * @param day
     * @return
     */
    private int getMonth(int monthNum, int day) {
        int month = day % 30;
        if (month != 0) {
            monthNum = day / 30 + 1;
        } else {
            monthNum = day / 30;
        }
        return monthNum;
    }

    /**
     * 处理最后一个阶段天数据不够5天（5天为一个工作周期）方法，
     * 假设1-7号，只有一包数据，那么统计的时候，显示时间戳范围1-7 而不是1-5
     *
     * @param startTime
     * @param endTime
     * @param replaceSensors
     * @param locationId
     */
    private void handleMinTimeMaxTime(String locationId, DateTime startTime, DateTime endTime, List<ReplaceSensorPO> replaceSensors) {
        ReplaceSensorPO replaceSensor = new ReplaceSensorPO();
        Period p = new Period(startTime, endTime, PeriodType.days());
        int dayNum = p.getDays();
        LocationVO locationVO = locationService.findLocationById(locationId);
        DeviceVO device = locationVO.getDevice();
        int second = dayNum * 24 * 3600;
        if (second < device.getInterval()) {
            //将本次不够一个周期的时间，更新到上一个周期的结束时间
            replaceSensors.get(replaceSensors.size() - 1).setEndDate(endTime.toDate());
        } else {
            replaceSensor.setStartDate(startTime.toDate());
            replaceSensor.setEndDate(endTime.toDate());
            replaceSensors.add(replaceSensor);
        }
    }

    /**
     * 不够30天的数据处理(30天为一个单位)
     *
     * @param locationId 位置点id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    private List<ReplaceSensorPO> getReplaceSensorPOs(String locationId, DateTime startTime, DateTime endTime) {
        List<ReplaceSensorPO> replaceSensors = new ArrayList<ReplaceSensorPO>();
        ReplaceSensorPO replaceSensor = new ReplaceSensorPO();
        replaceSensor.setStartDate(startTime.toDate());
        replaceSensor.setEndDate(endTime.toDate());
        getReplaceSensor(locationId, replaceSensor);
        replaceSensors.add(replaceSensor);
        return replaceSensors;
    }

    /**
     * 根据查值的和，评估等级
     *
     * @param locationId    位置点id
     * @param replaceSensor po对象
     * @return
     */
    private ReplaceSensorPO getReplaceSensor(String locationId, ReplaceSensorPO replaceSensor) {
        List<EvaluateCriterionPO> evaluateCriterionList = qcmDao.findEvaluateCriterions();
        float opSum = 0, ipSum = 0, spSum = 0;
        try {
            opSum = qcmDao.findSensorNum(locationId, 3075, replaceSensor.getStartDate(), replaceSensor.getEndDate());
            ipSum = qcmDao.findSensorNum(locationId, 3076, replaceSensor.getStartDate(), replaceSensor.getEndDate());
            spSum = qcmDao.findSensorNum(locationId, 3077, replaceSensor.getStartDate(), replaceSensor.getEndDate());
        } catch (Exception e) {

        }
        String startDate = DateTimeUtil.dateFormatReString("yyyy-MM-dd HH:mm", replaceSensor.getStartDate());
        String endDate = DateTimeUtil.dateFormatReString("yyyy-MM-dd HH:mm", replaceSensor.getEndDate());
        replaceSensor.setStringDate(startDate.trim() + "--" + endDate.trim());
        for (EvaluateCriterionPO evaluateCriterion : evaluateCriterionList) {
            if (Strings.isNullOrEmpty(replaceSensor.getStringOPLevel())) {
                if (spSum == 0) {
                    replaceSensor.setStringOPLevel("暂无数据");
                }
                if (spSum != 0 && opSum < evaluateCriterion.getOpRange()) {
                    replaceSensor.setStringOPLevel(evaluateCriterion.getAirQuality() + "(" + evaluateCriterion.getOpLevel() + ")");
                    replaceSensor.setStringOPLevelColor(evaluateCriterion.getColor());
                    replaceSensor.setOpLevel(evaluateCriterion.getOpLevel());
                    replaceSensor.setOPSum(opSum);
                }
            }
            if (Strings.isNullOrEmpty(replaceSensor.getStringIPLevel())) {
                if (ipSum == 0) {
                    replaceSensor.setStringIPLevel("暂无数据");
                }

                if (ipSum != 0 && ipSum < evaluateCriterion.getIpRange()) {
                    replaceSensor.setStringIPLevel(evaluateCriterion.getAirQuality() + "(" + evaluateCriterion.getIpLevel() + ")");
                    replaceSensor.setStringIPLevelColor(evaluateCriterion.getColor());
                    replaceSensor.setIpLevel(evaluateCriterion.getIpLevel());
                    replaceSensor.setIPSum(ipSum);
                }
            }
            if (Strings.isNullOrEmpty(replaceSensor.getStringSPLevel())) {
                if (spSum == 0) {
                    replaceSensor.setStringSPLevel("暂无数据");
                }
                if (spSum != 0 && spSum < evaluateCriterion.getSpRange()) {
                    replaceSensor.setStringSPLevel(evaluateCriterion.getAirQuality() + "(" + evaluateCriterion.getSpLevel() + ")");
                    replaceSensor.setStringSPLevelCoLor(evaluateCriterion.getColor());
                    replaceSensor.setSpLevel(evaluateCriterion.getSpLevel());
                    replaceSensor.setSPSum(spSum);
                }
            }
        }
        if (Strings.isNullOrEmpty(replaceSensor.getStringOPLevel())) {
            replaceSensor.setStringOPLevel("污染(P5)");
            replaceSensor.setStringOPLevelColor("#f8696b");
            replaceSensor.setOpLevel("P5");
            replaceSensor.setOPSum(opSum);
        }
        if (Strings.isNullOrEmpty(replaceSensor.getStringIPLevel())) {
            replaceSensor.setStringIPLevel("污染(C5)");
            replaceSensor.setStringIPLevelColor("#f8696b");
            replaceSensor.setIpLevel("C5");
            replaceSensor.setIPSum(ipSum);
        }
        if (Strings.isNullOrEmpty(replaceSensor.getStringSPLevel())) {
            replaceSensor.setStringSPLevel("污染(S5)");
            replaceSensor.setSpLevel("S5");
            replaceSensor.setStringSPLevelCoLor("#f8696b");
            replaceSensor.setSPSum(spSum);
        }
        return replaceSensor;
    }
}
