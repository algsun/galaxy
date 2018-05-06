package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.EvaluateCriterionPO;
import com.microwise.blueplanet.bean.po.ReplaceSensorPO;
import com.microwise.blueplanet.bean.vo.QCMVO;

import java.util.Date;
import java.util.List;

/**
 * QCM dao
 *
 * @author liuzhu
 * @date 2015-5-7.
 */
public interface QCMDao {

    /**
     * 查询QCM
     *
     * @param locationId
     * @param startDate
     * @param endDate
     * @param sensorId
     * @return
     */
    public List<QCMVO> findQCM(String locationId, Date startDate, Date endDate,int sensorId);

    /**
     * 查询换探头日期集合
     *
     * @param locationId
     */
    public List<Date> findQCMReplaceSensorDate(String locationId);

    /**
     * 查询最大时间,最小时间
     *
     * @param locationId
     * @return
     */
    public List<Date> findQCMMinMaxDate(String locationId);

    /**
     *查询差值的和
     *
     * @param locationId
     * @param sensorId
     * @param startDate
     * @param endDate
     * @return
     */
    public float findSensorNum(String locationId,int sensorId,Date startDate,Date endDate);

    /**
     * 查询qcm评估等级
     *
     * @return
     */
    public List<EvaluateCriterionPO> findEvaluateCriterions();

}
