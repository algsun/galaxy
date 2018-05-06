package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.Stock;
import com.microwise.blueplanet.dao.StockDao;
import com.microwise.blueplanet.service.StockService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Stock Service 实现
 *
 * @author li.jianfei
 * @date 2015-02-26
 */
@Beans.Service
@Transactional
@Blueplanet
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public List<Stock> findStocks(String locationId, Date date) {
        return stockDao.findStocks(locationId,date);
    }

    @Override
    public List<Stock> findByLocationIdAndSensorId(String locationId,int sensorId,Date starttime,Date endtime) {
        return stockDao.findByLocationIdAndSensorId(locationId,sensorId,starttime,endtime);
    }
}
