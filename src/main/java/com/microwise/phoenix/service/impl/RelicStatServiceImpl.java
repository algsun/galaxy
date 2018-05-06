package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.bean.vo.RelicBasicStats;
import com.microwise.phoenix.dao.RelicStatDao;
import com.microwise.phoenix.service.RelicStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.util.PieChartDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 藏品管理：系统文物统计 service 实现
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @duan.qixin 2013-7-18 #4568
 */
@Service
@Phoenix
@Transactional
public class RelicStatServiceImpl implements RelicStatService {

    /**
     * 系统文物统计dao
     */
    @Autowired
    private RelicStatDao relicStatDao;

    @Override
    public int findRelicSum(String siteId) {
        return relicStatDao.findRelicSum(siteId);
    }

    @Override
    public RelicBasicStats findRelicStatData(String siteId) {
        RelicBasicStats relicBasicStats = new RelicBasicStats();
        Integer relicSum = relicStatDao.findRelicSum(siteId);
        // 封装处理数据
        if (relicSum != 0) {
            relicBasicStats.setEraStat(PieChartDataUtil.disposePieChartDataToRelicData(relicStatDao
                    .findEraStat(siteId, relicSum)));
            relicBasicStats.setLevelStat(PieChartDataUtil
                    .disposePieChartDataToRelicData(relicStatDao.findLevelStat(siteId, relicSum)));
            relicBasicStats
                    .setTextureStat(PieChartDataUtil.disposePieChartDataToRelicData(relicStatDao
                            .findTextureStat(siteId, relicSum)));
            relicBasicStats.setZoneStat(PieChartDataUtil
                    .disposePieChartDataToRelicData(relicStatDao.findZoneStat(siteId, relicSum)));
        }
        return relicBasicStats;
    }
}
