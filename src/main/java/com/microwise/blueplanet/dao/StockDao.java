package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.Stock;

import java.util.Date;
import java.util.List;

/**
 * KDJ随机指标dao.
 *
 * @author 王耕
 * @date 2015-2-27
 */
public interface StockDao {
    public List<Stock> findStocks(String locationId, Date date);

    /**
     * 查询位置点的某个监测指标的KDJ指标信息
     *
     * @param locationId 位置点ID
     * @param sensorId 监测指标ID
     * @return List<Stock> 指标集合
     */
    public List<Stock> findByLocationIdAndSensorId(String locationId, int sensorId,Date starttime,Date endtime);
}
