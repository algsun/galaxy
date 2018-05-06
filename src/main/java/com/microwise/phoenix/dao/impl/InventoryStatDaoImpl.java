package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.uma.InventoryStat;
import com.microwise.phoenix.bean.po.uma.StockStat;
import com.microwise.phoenix.dao.InventoryStatDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 藏品管理：盘点统计dao 实现
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #4621
 */
@Dao
@Phoenix
public class InventoryStatDaoImpl extends PhoenixBaseDao implements InventoryStatDao {

    @Override
    public InventoryStat findInventoryStat(String siteId, int year) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("year", year);
        return getSqlSession().selectOne("phoenix.mybatis.InventoryStatDao.findInventoryStat", paramMap);
    }

    @Override
    public List<StockStat> findStockStat(String siteId) {
        return getSqlSession().selectList("phoenix.mybatis.InventoryStatDao.findStockStat", siteId);
    }

    @Override
    public List<Map<Date, Object>> findSingleRecognition(String siteId, int year) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("year", year);
        return getSqlSession().selectList("phoenix.mybatis.InventoryStatDao.findSingleRecognition", paramMap);
    }
}
