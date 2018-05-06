package com.microwise.blueplanet.dao.impl;

import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.po.Stock;
import com.microwise.blueplanet.dao.StockDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KDJ随机指标dao.
 *
 * @author 王耕
 * @date 2015-2-27
 */
@Beans.Dao
@Blueplanet
public class StockDaoImpl extends BlueplanetBaseDao implements StockDao {

    @Override
    public List<Stock> findStocks(String locationId, Date date) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("locationId", locationId);
        params.put("date", date);
        return getSqlSession().selectList("blueplanet.mybatis.StockDao.findStocks", params);
    }

    @Override
    public List<Stock> findByLocationIdAndSensorId(String locationId, int sensorId,Date starttime,Date endtime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        paramMap.put("starttime",starttime);
        paramMap.put("endtime",endtime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.StockDao.findByLocationIdAndSensorId", paramMap);
    }
}
