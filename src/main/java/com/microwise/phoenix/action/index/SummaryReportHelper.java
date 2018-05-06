package com.microwise.phoenix.action.index;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.CacheProxy;
import com.microwise.phoenix.bean.po.UserDistributionInfo;
import com.microwise.phoenix.bean.po.ZoneData;
import com.microwise.phoenix.bean.po.uma.ActionStat;
import com.microwise.phoenix.bean.po.uma.UserStat;
import com.microwise.phoenix.bean.vo.OutEventStats;
import com.microwise.phoenix.bean.vo.RelicBasicStats;
import com.microwise.phoenix.bean.vo.RelicFrequency;
import com.microwise.phoenix.bean.vo.UserStopOver;
import com.microwise.phoenix.service.*;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 综合评估报告数据统计
 *
 * @author li.jianfei
 * @date 13-7-26
 */
@Component("summaryReportHelper")
@Scope("prototype")
@Phoenix
public class SummaryReportHelper extends PhoenixLoggerAction {


    public static final Logger log = LoggerFactory.getLogger(SummaryReportHelper.class);
    /**
     * 环境监控缓存代理
     */
    @Autowired
    public CacheProxy cacheProxy;

    /**
     * 区域统计分析 Service
     */
    @Autowired
    public ZoneStatisticsService zoneService;


    /**
     * 出库时间统计 Service
     */
    @Autowired
    public OutEventStatsService outEventStatsService;

    /**
     * 统计文物各属性 Service
     */
    @Autowired
    public RelicStatService relicStatService;

    /**
     * 人员分布情况 Service
     */
    @Autowired
    public UserDistributionService userDistributionService;

    /**
     * 人员停留情况 Service
     */
    @Autowired
    public UserStopOverService userStopOverService;

    /**
     * 人员活动频率 Service
     */
    @Autowired
    public UserStatService userStatService;

    /**
     * 规则触发频率 Service
     */
    @Autowired
    public ActionStatService actionStatService;

    /**
     * 区域对比统计信息
     */
    private List<Map<SensorinfoVO, List<ZoneData>>> zoneDataList;

    private List<Map<SensorinfoVO, List<ZoneData>>> indoorZoneDataList;
    private List<Map<SensorinfoVO, List<ZoneData>>> outdoorZoneDataList;

    /**
     * 年度或月度出库最多的文物
     */
    private RelicFrequency relicFrequencyByCondition;

    /**
     * 出库总排名最多的文物
     */
    private RelicFrequency relicFrequencyOfAll;

    /**
     * 出库事件信息
     */
    private OutEventStats outEventStats;

    /**
     * 出库事件文物属性
     */
    private RelicBasicStats outEventRelicBasicStats;

    /**
     * 文物各属性统计信息
     */
    private RelicBasicStats relicBasicStats;

    /**
     * 文物总数
     */
    private int relicCount;

    /**
     * 区域人员活动分布
     */
    private UserDistributionInfo userDistribution;

    /**
     * 人员停留情况
     */
    private UserStopOver userStopover;

    /**
     * 活动最频繁的用户
     */
    private List<UserStat> highFrequencyUsers;

    /**
     * 触发最频繁的规则
     */
    private ActionStat highFrequencyAction;

    /**
     * 触发最不频繁的规则
     */
    private ActionStat lowFrequencyAction;


    /**
     * 获取区域对比统计信息
     *
     * @param siteId   当前站点信息
     * @param dateType 日期类型
     * @param date     日期
     */
    private void getZoneContrastInfo(String siteId, int dateType, Date date) {
        try {
            zoneDataList = zoneService.findRangStatsOfZones(siteId, date, dateType);
            indoorZoneDataList = Lists.newLinkedList();
            outdoorZoneDataList = Lists.newLinkedList();
            for (Map<SensorinfoVO, List<ZoneData>> data : zoneDataList) {
                Map<SensorinfoVO, List<ZoneData>> indoorZoneDataMap = Maps.newLinkedHashMap();
                Map<SensorinfoVO, List<ZoneData>> outdoorZoneDataMap = Maps.newLinkedHashMap();
                for (Map.Entry<SensorinfoVO, List<ZoneData>> entry : data.entrySet()) {
                    List<ZoneData> indoorZoneDatas = Lists.newLinkedList();
                    List<ZoneData> outdoorZoneDatas = Lists.newLinkedList();
                    for (ZoneData zoneData : entry.getValue()) {
                        if (zoneData.getPosition() == 1) {
                            indoorZoneDatas.add(zoneData);
                        } else if (zoneData.getPosition() == 2) {
                            outdoorZoneDatas.add(zoneData);
                        }
                    }
                    if (!indoorZoneDatas.isEmpty()) indoorZoneDataMap.put(entry.getKey(), indoorZoneDatas);
                    if (!outdoorZoneDatas.isEmpty()) outdoorZoneDataMap.put(entry.getKey(), outdoorZoneDatas);
                }
                indoorZoneDataList.add(indoorZoneDataMap);
                outdoorZoneDataList.add(outdoorZoneDataMap);
            }
        } catch (Exception e) {
            log.error("综合报告-区域对比统计", e);
        }
    }


    /**
     * 获取文物出库排名统计信息
     *
     * @param siteId 当前站点ID
     */
    private void getRelicOutRankingInfo(String siteId, int dateType, Date date) {

        List<RelicFrequency> relicFrequencyList;

        try {
            // 年度或月度文物出库排名
            relicFrequencyList = outEventStatsService.findRelicOutRanking(siteId, date, dateType, 1);
            if (relicFrequencyList.size() > 0) {
                relicFrequencyByCondition = relicFrequencyList.get(0);
            }

            // 文物出库总排名
            relicFrequencyList = outEventStatsService.findRelicOutRanking(siteId, 1);
            if (relicFrequencyList.size() > 0) {
                relicFrequencyOfAll = relicFrequencyList.get(0);
            }
        } catch (Exception e) {
            log.error("综合报告-文物出库排名", e);
        }
    }

    /**
     * 获取出库事件统计信息
     *
     * @param siteId   当前站点ID
     * @param dateType 日期类型
     * @param date     日期
     */
    private void getOutEventStatInfo(String siteId, int dateType, Date date) {
        try {
            // 柱状图数据
            List<OutEventStats> outEventStatsList = outEventStatsService.findOutEventStat(siteId, date, dateType);
            if (outEventStatsList.size() > 0) {
                outEventStats = outEventStatsList.get(0);
            }
        } catch (Exception e) {
            log.error("综合报告-出库时间统计", e);
        }
    }


    /**
     * 获取文物各属性统计信息
     *
     * @param siteId 当前站点ID
     */
    private void getRelicBasicStatsInfo(String siteId) {

        try {
            // 获取当前站点下文物统计信息
            relicBasicStats = relicStatService.findRelicStatData(siteId);

            // 获取文物总数
            relicCount = relicStatService.findRelicSum(siteId);
        } catch (Exception e) {
            log.error("综合报告-各属性饼状图", e);
        }
    }

    /**
     * 获取区域人员活动分布信息
     */
    private void getUserDistributionInfo(String siteId, int dateType, Date date) {
        try {
            userDistribution = userDistributionService.getUserDistributionInfo(siteId, date, dateType);
        } catch (Exception e) {
            log.error("综合报告-区域活动分布", e);
        }
    }

    /**
     * 获取人员停留状况信息
     *
     * @param siteId 当前站点ID
     */
    private void getUserStopoverInfo(String siteId, int dateType, Date date) {

        try {
            userStopover = userStopOverService.findUserStopOver(siteId, date, dateType);
        } catch (Exception e) {
            log.error("综合报告-人员停留情况", e);
        }
    }

    /**
     * 获取人员活动频率信息
     *
     * @param logicGroupId 当前站点组ID
     */
    private void getUserStatInfo(int logicGroupId, int dateType, Date date) {

        List<UserStat> users;
        try {
            // 活动最频繁
            users = userStatService.findUserFrequencyOfActivitiesStat(logicGroupId, date, dateType, 3, true);
            highFrequencyUsers = users;
        } catch (Exception e) {
            log.error("综合报告-人员活动频率", e);
        }
    }

    /**
     * 获取规则触发频率信息
     *
     * @param siteId 当前站点ID
     */
    private void getActionStatInfo(String siteId, int dateType, Date date) {
        try {
            List<ActionStat> actionStatList = actionStatService.findActionStat(siteId, date, dateType);
            if (actionStatList.size() > 0) {
                highFrequencyAction = actionStatList.get(0);
            }
        } catch (Exception e) {
            log.error("综合报告-规则触发频率", e);
        }
    }


    public void getStatisticInfo(String siteId, int logicGroupId, int dateType, Date date) {
        // 获取【区域对比统计】信息
        getZoneContrastInfo(siteId, dateType, date);

        // 获取【文物出库排名】信息
        getRelicOutRankingInfo(siteId, dateType, date);

        // 获取【出库事件统计】信息
        getOutEventStatInfo(siteId, dateType, date);

        // 获取【各属性饼状图】统计信息
        getRelicBasicStatsInfo(siteId);

        // 获取【区域活动分布】信息
        getUserDistributionInfo(siteId, dateType, date);

        // 获取【人员停留情况】信息
        getUserStopoverInfo(siteId, dateType, date);

        // 获取【人员活动频率】信息
        getUserStatInfo(logicGroupId, dateType, date);

        // 获取【规则触发频率】信息
        getActionStatInfo(siteId, dateType, date);

    }

    public List<Map<SensorinfoVO, List<ZoneData>>> getZoneDataList() {
        return zoneDataList;
    }

    public void setZoneDataList(List<Map<SensorinfoVO, List<ZoneData>>> zoneDataList) {
        this.zoneDataList = zoneDataList;
    }

    public RelicFrequency getRelicFrequencyByCondition() {
        return relicFrequencyByCondition;
    }

    public void setRelicFrequencyByCondition(RelicFrequency relicFrequencyByCondition) {
        this.relicFrequencyByCondition = relicFrequencyByCondition;
    }

    public RelicFrequency getRelicFrequencyOfAll() {
        return relicFrequencyOfAll;
    }

    public void setRelicFrequencyOfAll(RelicFrequency relicFrequencyOfAll) {
        this.relicFrequencyOfAll = relicFrequencyOfAll;
    }

    public OutEventStats getOutEventStats() {
        return outEventStats;
    }

    public void setOutEventStats(OutEventStats outEventStats) {
        this.outEventStats = outEventStats;
    }

    public RelicBasicStats getOutEventRelicBasicStats() {
        return outEventRelicBasicStats;
    }

    public void setOutEventRelicBasicStats(RelicBasicStats outEventRelicBasicStats) {
        this.outEventRelicBasicStats = outEventRelicBasicStats;
    }

    public RelicBasicStats getRelicBasicStats() {
        return relicBasicStats;
    }

    public void setRelicBasicStats(RelicBasicStats relicBasicStats) {
        this.relicBasicStats = relicBasicStats;
    }

    public int getRelicCount() {
        return relicCount;
    }

    public void setRelicCount(int relicCount) {
        this.relicCount = relicCount;
    }

    public UserDistributionInfo getUserDistribution() {
        return userDistribution;
    }

    public void setUserDistribution(UserDistributionInfo userDistribution) {
        this.userDistribution = userDistribution;
    }

    public UserStopOver getUserStopover() {
        return userStopover;
    }

    public void setUserStopover(UserStopOver userStopover) {
        this.userStopover = userStopover;
    }

    public List<UserStat> getHighFrequencyUsers() {
        return highFrequencyUsers;
    }

    public void setHighFrequencyUsers(List<UserStat> highFrequencyUsers) {
        this.highFrequencyUsers = highFrequencyUsers;
    }

    public ActionStat getHighFrequencyAction() {
        return highFrequencyAction;
    }

    public void setHighFrequencyAction(ActionStat highFrequencyAction) {
        this.highFrequencyAction = highFrequencyAction;
    }

    public List<Map<SensorinfoVO, List<ZoneData>>> getIndoorZoneDataList() {
        return indoorZoneDataList;
    }

    public void setIndoorZoneDataList(List<Map<SensorinfoVO, List<ZoneData>>> indoorZoneDataList) {
        this.indoorZoneDataList = indoorZoneDataList;
    }

    public List<Map<SensorinfoVO, List<ZoneData>>> getOutdoorZoneDataList() {
        return outdoorZoneDataList;
    }

    public void setOutdoorZoneDataList(List<Map<SensorinfoVO, List<ZoneData>>> outdoorZoneDataList) {
        this.outdoorZoneDataList = outdoorZoneDataList;
    }

    public ActionStat getLowFrequencyAction() {
        return lowFrequencyAction;
    }

    public void setLowFrequencyAction(ActionStat lowFrequencyAction) {
        this.lowFrequencyAction = lowFrequencyAction;
    }
}
