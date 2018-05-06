package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.bean.po.uma.InventoryStat;
import com.microwise.phoenix.bean.po.uma.StockStat;
import com.microwise.phoenix.dao.InventoryStatDao;
import com.microwise.phoenix.service.InventoryStatService;
import com.microwise.phoenix.sys.Phoenix;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.*;

/**
 * 藏品管理：文物盘点统计service 实现类
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #4621
 */
@Service
@Phoenix
@Transactional
public class InventoryStatServiceImpl implements InventoryStatService {

    /**
     * 文物盘点统计 dao
     */
    @Autowired
    private InventoryStatDao inventoryStatDao;

    @Override
    public List<InventoryStat> findInventoryStats(String siteId, Date date) {
        int year = new DateTime(date).getYear();
        List<InventoryStat> inventoryStats = new ArrayList<InventoryStat>();
        // 获得当前年的盘点数据
        inventoryStats.add(findInventoryStat(siteId, year));
        //获得前一年的盘点数据
        inventoryStats.add(findInventoryStat(siteId, year - 1));
        return inventoryStats;
    }

    /**
     * 查询 盘点统计：并处理没有数据的对象为null
     *
     * @param siteId
     * @param year
     * @return
     * @author xu.baoji
     * @date 2013-7-22
     */
    private InventoryStat findInventoryStat(String siteId, int year) {
        InventoryStat inventoryStat = inventoryStatDao.findInventoryStat(siteId, year);
        if (inventoryStat.getCount() == 0) {
            return null;
        }
        return inventoryStat;
    }

    /**
     * 查询 盘点统计：并处理没有数据的对象为null
     *
     * @param siteId
     * @return
     * @author xu.yuexi
     * @date 2014-9-18
     */
    @Override
    public List<StockStat> findStockStats(String siteId) {
        List<StockStat> stockStat = inventoryStatDao.findStockStat(siteId);
        for (StockStat stat : stockStat) {
            List<Map<Date, Object>> map = inventoryStatDao.findSingleRecognition(siteId, stat.getYear());
            if (stat.getCount() == 0 || map == null) {
                return null;
            }
            double yearRecognition = 0.0;
            if (map != null) {
                for (Map<Date, Object> map1 : map) {
                    double scanNumber = Double.parseDouble(map1.get("scanNumber").toString()) * 100;
                    double tagNumber = Double.parseDouble(map1.get("tagNumber").toString());
                    Map<Date, Double> dateDoubleMap = new HashMap<Date, Double>();
                    dateDoubleMap.put((Date) map1.get("date"), scanNumber / tagNumber);
                    stat.getSingleRecognition().add(dateDoubleMap);
                    if (tagNumber > 0) {
                        yearRecognition += scanNumber / tagNumber;
                    }
                }
                NumberFormat nFormat = NumberFormat.getNumberInstance();
                nFormat.setMaximumFractionDigits(2);
                yearRecognition = Double.parseDouble(nFormat.format(yearRecognition / stat.getCount()));
                stat.setYearRecognition(yearRecognition);
            }
        }
        return stockStat;
    }

}
