package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.EvaluateCriterionPO;
import com.microwise.blueplanet.bean.po.ReplaceSensorPO;
import com.microwise.blueplanet.bean.vo.QCMVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * QCM service
 *
 * @author liuzhu
 * @date 2015-5-7.
 */
public interface QCMService {


    /**
     * 查询QCM
     *
     * @param locationId
     * @param startDate
     * @param endDate
     */
    public List<QCMVO> findQCM(String locationId, Date startDate, Date endDate, int sensorId);

    /**
     * 组织qcm评估等级数据
     *
     * @param locationId
     * @return
     */
    public Map<String, List<ReplaceSensorPO>> assembleQCMLevel(String locationId,Date startDate, Date endDate);

    /**
     * 查询qcm评估等级
     *
     * @return
     */
    public List<EvaluateCriterionPO> findEvaluateCriterions();

    /**
     * 查询最大时间,最小时间
     *
     * @param locationId
     * @return
     */
    public List<Date> findQCMMinMaxDate(String locationId);

}
