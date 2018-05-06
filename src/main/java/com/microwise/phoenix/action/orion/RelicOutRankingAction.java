package com.microwise.phoenix.action.orion;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.RelicFrequency;
import com.microwise.phoenix.service.OutEventStatsService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 文物出库排名
 *
 * @author gaohui
 * @date 13-7-9 15:32
 * @check @duan.qixin 2013-7-12 #4472
 */
@Beans.Action
@Phoenix
public class RelicOutRankingAction extends PhoenixLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(RelicOutRankingAction.class);

    public static final String _pagePath = "../orion/relic-out-ranking.ftl";

    // 默认文物数量
    public static final int DEFAULT_COUNT = 20;

    @Autowired
    private OutEventStatsService outEventStatsService;


    //input
    /**
     * 前多少名
     */
    private int topN = DEFAULT_COUNT;
    /**
     * 哪一年度数据
     */
    private int year = 0;
    /**
     * 有数据的最小年份
     */
    private int minYear = 0;
    /**
     * 最大年份(当前时候的年份)
     */
    private int maxYear = 0;

    //output
    /**
     * 某年的文物出库次数排名
     */
    private List<RelicFrequency> relicFrequenciesOfYear;
    /**
     * 文物出库次数总排名
     */
    private List<RelicFrequency> relicFrequenciesAll;

    @Route("/phoenix/orion/relic-out-ranking")
    public String relicOutRanking() {
        String siteId = Sessions.createByAction().currentSiteId();
        Date minDate = outEventStatsService.findOldestOfOutedRelic(siteId);
        if (minDate == null) {
            minDate = new Date(); // 如果没有数据，默认当前年
        }
        minYear = DateTime.now().withMillis(minDate.getTime()).getYear();
        maxYear = DateTime.now().getYear();
        if (year == 0) {
            year = maxYear;
        }

        try {
            relicFrequenciesOfYear = outEventStatsService.findRelicOutRankingByYear(siteId, DateTime.now().withYear(year).toDate(), topN);
            relicFrequenciesAll = outEventStatsService.findRelicOutRanking(siteId, topN);
            log("数据分析", "文物出库排名");
        } catch (Exception e) {
            log.error("文物出库排名", e);
        }

        return Results.ftl("/phoenix/pages/index/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<RelicFrequency> getRelicFrequenciesOfYear() {
        return relicFrequenciesOfYear;
    }

    public void setRelicFrequenciesOfYear(List<RelicFrequency> relicFrequenciesOfYear) {
        this.relicFrequenciesOfYear = relicFrequenciesOfYear;
    }

    public List<RelicFrequency> getRelicFrequenciesAll() {
        return relicFrequenciesAll;
    }

    public void setRelicFrequenciesAll(List<RelicFrequency> relicFrequenciesAll) {
        this.relicFrequenciesAll = relicFrequenciesAll;
    }

    public int getTopN() {
        return topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
