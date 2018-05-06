package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.bean.vo.OutEventStats;
import com.microwise.phoenix.bean.vo.OutEventStatsInfo;
import com.microwise.phoenix.bean.vo.RelicBasicStats;
import com.microwise.phoenix.bean.vo.RelicFrequency;
import com.microwise.phoenix.dao.OutEventStatsDao;
import com.microwise.phoenix.service.OutEventStatsService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.util.PieChartDataUtil;
import com.microwise.uma.util.DateTypeGenerator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 出库事件 统计service
 *
 * @author xu.baoji
 * @date 2013-7-9
 * @check @gaohui #4469 2013-07-12
 * @check @xu.baoji #4500 2013-07-12
 */
@Phoenix
@Service
@Transactional
public class OutEventStatsServiceImpl implements OutEventStatsService {

    /**
     * 出库 事件统计 dao
     */
    @Autowired
    private OutEventStatsDao outEventStatDao;

    @Override
    public List<OutEventStats> findOutEventStat(String siteId, int year) {
        List<OutEventStats> outEventStats = new ArrayList<OutEventStats>();
        outEventStats.add(outEventStatDao.findOutEventStat(siteId, DateTypeGenerator.start(year),
                DateTypeGenerator.end(year), true));
        outEventStats.add(outEventStatDao.findOutEventStat(siteId,
                DateTypeGenerator.start(year - 1), DateTypeGenerator.end(year - 1), true));
        return outEventStats;
    }

    @Override
    public List<OutEventStats> findOutEventStat(String siteId, Date date, int type) {
        List<OutEventStats> outEventStats = new ArrayList<OutEventStats>();
        if (type == Constants.FIND_TYPE_YEAR) {// 如果查询条件为年
            outEventStats = findOutEventStat(siteId, new DateTime(date).getYear());
        } else if (type == Constants.FIND_TYPE_MONTH) {// 如果查询条件为月
            outEventStats.add(outEventStatDao.findOutEventStat(siteId,
                    DateTypeGenerator.start(type, date), DateTypeGenerator.end(type, date), false));
        } else {
            throw new IllegalArgumentException("日期类型不正确: " + type);
        }
        return outEventStats;
    }

    @Override
    public List<OutEventStatsInfo> findOutEventStatInfo(String siteId, int year) {
        List<OutEventStatsInfo> outEventStatInfos = new ArrayList<OutEventStatsInfo>();
        // 查询外出借展类型的 出库单信息统计
        outEventStatInfos.add(findOutEventStatInfo(siteId, Constants.Phoenix.OUT_EVENT_TYPE_WCJZ, year));
        // 查询科研修复类型的 出库单信息统计
        outEventStatInfos.add(findOutEventStatInfo(siteId, Constants.Phoenix.OUT_EVENT_TYPE_KYXF, year));
        return outEventStatInfos;
    }

    @Override
    public RelicBasicStats findPieChartData(String siteId, int year) {
        Date startDate = DateTypeGenerator.start(year);
        Date endDate = DateTypeGenerator.end(year);
        return findPieChartData(siteId, startDate, endDate, true);
    }

    @Override
    public RelicBasicStats findPieChartData(String siteId, Date date, int type) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        boolean isYear = false;
        if (type == Constants.FIND_TYPE_YEAR) {
            isYear = true;
        }
        return findPieChartData(siteId, startDate, endDate, isYear);
    }

    /***
     * 查询饼图数据
     *
     * @param siteId    站点编号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     * @author xu.baoji
     * @date 2013-7-26
     */
    private RelicBasicStats findPieChartData(String siteId, Date startDate, Date endDate,
                                             boolean isYear) {
        RelicBasicStats relicBasicStat = new RelicBasicStats();
        // 查询本年出库文物统计实体
        OutEventStats outEventStat = outEventStatDao.findOutEventStat(siteId, startDate, endDate,
                isYear);
        // 判断本年是否有文物出库
        if (outEventStat != null) {
            // 本年出库文物总数量
            Integer eventRelicSum = outEventStat.getYearSum();
            if (eventRelicSum != null) {
                // 查询年代统计数据
                relicBasicStat.setEraStat(PieChartDataUtil
                        .disposePieChartDataToRelicData(outEventStatDao.findEraStat(siteId,
                                eventRelicSum, startDate, endDate)));
                // 查询级别统计数据
                relicBasicStat.setLevelStat(PieChartDataUtil.disposePieChartDataToRelicData(outEventStatDao.findLevelStat(
                        siteId, eventRelicSum, startDate, endDate)));
                // 查询质地统计数据
                relicBasicStat.setTextureStat(PieChartDataUtil.disposePieChartDataToRelicData(outEventStatDao.findTextureStat(
                        siteId, eventRelicSum, startDate, endDate)));
                // 查询区域统计数据
                relicBasicStat.setZoneStat(PieChartDataUtil.disposePieChartDataToRelicData(outEventStatDao.findZoneStats(
                        siteId, eventRelicSum, startDate, endDate)));
            }
        }
        return relicBasicStat;
    }

    /**
     * 查询出库单 信息 统计
     *
     * @param siteId 站点编号
     * @param type   查询出库类型
     * @param year   查询年
     * @return OutEventStatInfo 出库单 信息统计实体
     * @author xu.baoji
     * @date 2013-7-10
     */
    private OutEventStatsInfo findOutEventStatInfo(String siteId, int type, int year) {
        OutEventStatsInfo outEventStatInfo = outEventStatDao.findOutEventStatInfo(siteId, type,
                DateTypeGenerator.start(year), DateTypeGenerator.end(year));
        if (outEventStatInfo != null) {
            outEventStatInfo.setOutEventInfos(outEventStatDao.findOutEventInfo(siteId, type,
                    DateTypeGenerator.start(year), DateTypeGenerator.end(year)));
        }
        return outEventStatInfo;
    }

    @Override
    public Date findOldestOfOutedRelic(String siteId) {
        return outEventStatDao.findOldestOfOutedRelic(siteId);
    }

    @Override
    public List<RelicFrequency> findRelicOutRankingByYear(String siteId, Date year, int max) {
        Date startDate = DateTypeGenerator.start(Constants.FIND_TYPE_YEAR, year);
        Date endDate = DateTypeGenerator.end(Constants.FIND_TYPE_YEAR, year);
        return outEventStatDao.findRelicOutRanking(siteId, startDate, endDate, 0, max);
    }

    @Override
    public List<RelicFrequency> findRelicOutRanking(String siteId, Date date, int type, int max) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        return outEventStatDao.findRelicOutRanking(siteId, startDate, endDate, 0, max);
    }

    @Override
    public List<RelicFrequency> findRelicOutRanking(String siteId, int max) {
        Date startDate = DateTime.now().withDate(1970, 1, 1).toDate();
        Date endDate = DateTypeGenerator.end(Constants.FIND_TYPE_YEAR, new Date());
        return outEventStatDao.findRelicOutRanking(siteId, startDate, endDate, 0, max);
    }
}
