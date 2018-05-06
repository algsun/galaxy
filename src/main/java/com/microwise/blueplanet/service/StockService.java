package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.Stock;

import java.util.Date;
import java.util.List;

/**
 * Stock Service
 *
 * @author li.jianfei
 * @date 2015-02-26
 */
public interface StockService {
    public List<Stock> findStocks(String locationId, Date date);

    /**
     * 查询位置点的某个监测指标的KDJ指标信息
     *
     * @param locationId 位置点ID
     * @param sensorId 监测指标ID
     * @return List<Stock> 指标集合
     */
    public List<Stock> findByLocationIdAndSensorId(String locationId,int sensorId,Date starttime,Date endtime);
}
