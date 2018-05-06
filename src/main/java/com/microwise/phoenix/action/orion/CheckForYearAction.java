package com.microwise.phoenix.action.orion;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.po.uma.StockStat;
import com.microwise.phoenix.service.InventoryStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.List;

/**
 * 年度盘点
 *
 * @author duan.qixin
 * @date 2013-07-22
 * <p/>
 * * @check @wanggeng 2013年7月29日 #4666
 */
@Beans.Action
@Phoenix
public class CheckForYearAction extends PhoenixLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(CheckForYearAction.class);

    public static final String _pagePath = "../orion/check-year.ftl";


    //output
    /**
     * 结论
     */
    private String backup;

    /**
     * 文物盘点数据
     */
    private String relicData = "";
    /**
     * 文物单次盘点识别率
     */
    private String relicSingleData = "";
    /**
     * 文物年度盘点识别率
     */
    private String relicYearData = "";
    /**
     *
     */
    private String yearData = "";
    /**
     * 是否为空数据
     */
    private String nullFlag = "notNull";

    /**
     * 文物盘点统计service
     */
    @Autowired
    private InventoryStatService service;

    //获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/phoenix/orion/checkYear")
    public String view() {

        List<StockStat> stockStats = service.findStockStats(siteId);
        //盘点数据存在
        conclusion(stockStats);

        log("数据分析", "年度盘点");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 年度盘点结论
     *
     * @param stockStats 年度盘点的list
     * @author liuzhu
     * @date 2013-09-10
     */
    private void conclusion(List<StockStat> stockStats) {
        if (stockStats != null && stockStats.size() > 0) {
            StockStat thisYear = stockStats.get(stockStats.size() - 1);
            backup = thisYear.getYear() + "年";
            StockStat lastYear = null;
            if (stockStats.size() > 1) {
                lastYear = stockStats.get(stockStats.size() - 2);
            }
            NumberFormat nFormat = NumberFormat.getNumberInstance();
            nFormat.setMaximumFractionDigits(2);
            if (lastYear != null) {
                if (thisYear.getYearRecognition() == lastYear.getYearRecognition()) {
                    backup += "相比去年盘点识别率持平";
                } else if (thisYear.getYearRecognition() > lastYear.getYearRecognition()) {
                    backup += "相比去年盘点识别率增长了" + nFormat.format(thisYear.getYearRecognition() - lastYear.getYearRecognition()) + "%";
                } else {

                    backup += "相比去年盘点识别率降低了" + nFormat.format(lastYear.getYearRecognition() - thisYear.getYearRecognition()) + "%";
                }
            }
            yearData = "[";
            relicData = "{cData:[";
            relicYearData = "{cData:[";
            for (StockStat stockStat : stockStats) {
                yearData += stockStat.getYear() + ",";
                relicData += stockStat.getCount() + ",";
                relicYearData += stockStat.getYearRecognition() + ",";
            }
            yearData = yearData.substring(0, yearData.length() - 1);
            relicData = relicData.substring(0, relicData.length() - 1);
            relicYearData = relicYearData.substring(0, relicYearData.length() - 1);
            relicData += "]}";
            relicYearData += "]}";
            yearData += "]";
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getRelicData() {
        return relicData;
    }

    public void setRelicData(String relicData) {
        this.relicData = relicData;
    }

    public String getRelicSingleData() {
        return relicSingleData;
    }

    public void setRelicSingleData(String relicSingleData) {
        this.relicSingleData = relicSingleData;
    }

    public String getRelicYearData() {
        return relicYearData;
    }

    public void setRelicYearData(String relicYearData) {
        this.relicYearData = relicYearData;
    }

    public InventoryStatService getService() {
        return service;
    }

    public void setService(InventoryStatService service) {
        this.service = service;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getNullFlag() {
        return nullFlag;
    }

    public void setNullFlag(String nullFlag) {
        this.nullFlag = nullFlag;
    }

    public String getYearData() {
        return yearData;
    }

    public void setYearData(String yearData) {
        this.yearData = yearData;
    }

}
