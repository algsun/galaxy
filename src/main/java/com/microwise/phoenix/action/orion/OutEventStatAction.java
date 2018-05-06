package com.microwise.phoenix.action.orion;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.OutEventStats;
import com.microwise.phoenix.bean.vo.OutEventStatsInfo;
import com.microwise.phoenix.bean.vo.RelicBasicStats;
import com.microwise.phoenix.bean.vo.healthCheck.RelicPieData;
import com.microwise.phoenix.service.OutEventStatsService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

/**
 * 出库事件统计-综合评估
 *
 * @author duanqixin
 * @date 13-7-10
 * @check @gaohui #4496 2013-07-12
 */
@Beans.Action
@Phoenix
public class OutEventStatAction extends PhoenixLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(OutEventStatAction.class);

    public static final String _pagePath = "../orion/outEventStat.ftl";

    @Autowired
    private OutEventStatsService service;

    //input
    /**
     * 年份
     */
    private Integer year;

    //output
    /**
     * 统计年份
     */
    private String yearData = "";
    /**
     * 出库次数
     */
    private String outCount = "";
    /**
     * 文物总数
     */
    private String relicSum = "";
    /**
     * 饼图-时代
     */
    private String timeData = "";
    /**
     * 饼图-级别
     */
    private String levelData = "";
    /**
     * 饼图-质地
     */
    private String grainData = "";
    /**
     * 饼图-库房位次
     */
    private String zoneData = "";
    /**
     * 外出展览
     */
    private OutEventStatsInfo outShow;
    /**
     * 科研修复
     */
    private OutEventStatsInfo scientific;
    /**
     * 合计
     */
    private String sumText;
    /**
     * 总结
     */
    private String sumUp;
    /**
     * 博物馆名称
     */
    private String groupName = Sessions.createByAction().currentLogicGroup().getLogicGroupName();

    // 获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    public String execute() {
        //默认当前年份
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        //柱状图数据
        List<OutEventStats> outEvents = service.findOutEventStat(siteId, year);
        if (outEvents != null && outEvents.size() > 1) {
            // 对比年
            OutEventStats contrastYear = outEvents.get(0);

            // 当前年
            OutEventStats currentYear = outEvents.get(1);

            double contrastYearOutCount = 0; //对比年的出库次数
            double currentYearOutCount = 0; //当前年的出库次数
            double contrastYearRelicSum = 0; //对比年的文物总数
            double currentYearRelicSum = 0; //当前年的文物总数
            //当前年
            if (currentYear.getYearCount() != 0) {
                yearData += currentYear.getYear() + ",";
                outCount += currentYear.getYearCount() + ",";
                relicSum += currentYear.getYearSum() + ",";
                currentYearOutCount = currentYear.getYearCount();
                currentYearRelicSum = currentYear.getYearSum();
            } else {
                yearData += (year - 1) + ",";
                outCount += "0,";
                relicSum += "0,";
            }
            //对比年
            if (contrastYear.getYearCount() != 0) {
                yearData += year + ",";
                outCount += contrastYear.getYearCount() + ",";
                relicSum += contrastYear.getYearSum() + ",";
                contrastYearOutCount = contrastYear.getYearCount();
                contrastYearRelicSum = contrastYear.getYearSum();
            } else {
                yearData += year + ",";
                outCount += "0,";
                relicSum += "0,";
            }
            //统计数据
            if (currentYearOutCount == 0) {
                sumUp = year + "年出库次数比去年增长了" + (contrastYearOutCount * 100) + "%，" + "文物数量比去年增长了" + (contrastYearRelicSum * 100) + "%。";
            } else {
                sumUp = year + "年出库次数比前一年年增长了" + String.format("%.2f", (contrastYearOutCount - contrastYearOutCount) / currentYearOutCount * 100) + "%，" + year + "年出库文物数量比前一年增长了" + String.format("%.2f", (contrastYearRelicSum - contrastYearRelicSum) / currentYearRelicSum * 100) + "%。";
            }
            //JSON组装
            if (yearData.length() > 0) {
                yearData = "[" + yearData.substring(0, yearData.length() - 1) + "]";
            }
            if (outCount.length() > 0) {
                outCount = "[" + outCount.substring(0, outCount.length() - 1) + "]";
            }
            if (relicSum.length() > 0) {
                relicSum = "[" + relicSum.substring(0, relicSum.length() - 1) + "]";
            }
        }

        //饼图数据
        RelicBasicStats rbs = service.findPieChartData(siteId, year);

        if (rbs != null) {
            if (rbs.getEraStat() != null) {//年代
                sumUp += "其中今年出库文物最多的是";

                timeData = assyData(rbs.getEraStat());
            }
            if (rbs.getLevelStat() != null) {//级别
                levelData = assyData(rbs.getLevelStat());
            }
            if (rbs.getTextureStat() != null) {//质地
                grainData = assyData(rbs.getTextureStat());
            }
            if (rbs.getZoneStat() != null) {//库房位次
                zoneData = assyData(rbs.getZoneStat());
            }
        }


        //列表数据
        List<OutEventStatsInfo> outInfos = service.findOutEventStatInfo(siteId, year);
        if (outInfos != null) {
            if (outInfos.size() > 1) {
                double oCount = 0;
                double sCount = 0;
                int sum = 0;
                outShow = outInfos.get(0);
                scientific = outInfos.get(1);

                if (outShow != null) {
                    oCount = outShow.getTypeCount();
                    sum += outShow.getTypeSum() == null ? 0 : outShow.getTypeSum();
                }
                if (scientific != null) {
                    sCount += scientific.getTypeCount();
                    sum += scientific.getTypeSum() == null ? 0 : scientific.getTypeSum();
                }

                sumText = "共出库" + (int) (oCount + sCount) + "次，共计" + sum + "套";

                if (oCount != 0 || sCount != 0) {
                    if (oCount > sCount) {
                        sumUp += "外出展览:" + String.format("%.2f", oCount / (oCount + sCount) * 100) + "%。";
                    } else {
                        sumUp += "科研修复:" + String.format("%.2f", sCount / (oCount + sCount) * 100) + "%。";
                    }
                }
            }
        }
        log("数据分析", "出库事件统计");
        return Action.SUCCESS;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    /**
     * 数据组装
     */
    private String assyData(RelicPieData rpd) {
        String data = "";

        if (rpd.isHasData()) {
            double ys = 0;//临时数据
            String yt = "";//统计文字说明

            String name = null;
            for (List<Object> elm : rpd.getPieData()) {
                name = elm.get(0) == null ? "无库房位次" : elm.get(0).toString();

                if ("其他".equals(name)) {
                    data += "{name:'其他',y:" + elm.get(1) + ",id:'" + assyOtherData(rpd) + "'},";
                } else {
                    data += "['" + name + "'," + elm.get(1) + "],";
                }
                double temp = Double.valueOf(elm.get(1).toString());
                if (temp > ys) {
                    ys = temp;
                    yt = name.toString();
                }
            }
            if (data.length() > 0) {
                data = "[" + data.substring(0, data.length() - 1) + "]";
                sumUp += yt + ":" + ys + "%，";
            }
        }

        return data;
    }

    /**
     * “其它”数据组装
     */
    private String assyOtherData(RelicPieData rpd) {
        String data = "";

        if (rpd.isHasOtherData()) {
            for (List<Object> elm : rpd.getOtherData()) {
                data += elm.get(0) + ": " + elm.get(1) + "%,数量:" + elm.get(2) + "个<br/>";
            }
        }

        return data;
    }

    //getter setter
    public String getYearData() {
        return yearData;
    }

    public void setYearData(String yearData) {
        this.yearData = yearData;
    }

    public String getOutCount() {
        return outCount;
    }

    public void setOutCount(String outCount) {
        this.outCount = outCount;
    }

    public String getRelicSum() {
        return relicSum;
    }

    public void setRelicSum(String relicSum) {
        this.relicSum = relicSum;
    }

    public String getTimeData() {
        return timeData;
    }

    public void setTimeData(String timeData) {
        this.timeData = timeData;
    }

    public String getLevelData() {
        return levelData;
    }

    public void setLevelData(String levelData) {
        this.levelData = levelData;
    }

    public String getGrainData() {
        return grainData;
    }

    public void setGrainData(String grainData) {
        this.grainData = grainData;
    }

    public String getZoneData() {
        return zoneData;
    }

    public void setZoneData(String zoneData) {
        this.zoneData = zoneData;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public OutEventStatsInfo getOutShow() {
        return outShow;
    }

    public void setOutShow(OutEventStatsInfo outShow) {
        this.outShow = outShow;
    }

    public OutEventStatsInfo getScientific() {
        return scientific;
    }

    public void setScientific(OutEventStatsInfo scientific) {
        this.scientific = scientific;
    }

    public String getSumUp() {
        return sumUp;
    }

    public void setSumUp(String sumUp) {
        this.sumUp = sumUp;
    }

    public String getSumText() {
        return sumText;
    }

    public void setSumText(String sumText) {
        this.sumText = sumText;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
