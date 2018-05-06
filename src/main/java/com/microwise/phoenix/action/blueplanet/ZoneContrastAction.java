package com.microwise.phoenix.action.blueplanet;

import com.google.common.collect.Lists;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.CacheProxy;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.po.ZoneData;
import com.microwise.phoenix.service.ZoneStatisticsService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 区域对比统计
 *
 * @author duan.qixin
 * @date 2013-07-04
 * @check li.jianfei 2013-07-08 #4415
 */
@Beans.Action
@Phoenix
public class ZoneContrastAction extends PhoenixLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(ZoneContrastAction.class);

    public static final String _pagePath = "../blueplanet/zoneContrast.ftl";

    @Autowired
    private CacheProxy cacheProxy;
    @Autowired
    private ZoneStatisticsService zoneService;

    //input

    /**
     * 技术指标 默认值：温度
     */
    private int target = 33;

    /**
     * 日期类型 默认值：日
     */
    private int dateType = Constants.FIND_TYPE_MONTH;

    /**
     * 日期 默认值：今天
     */
    private Date date;

    /**
     * 计量单位
     */
    private String uom;

    /**
     * 环境指标集合
     */
    private List<SensorinfoVO> sens;

    //output
    /**
     * 结论日期（日期）
     */
    private String formatTime;

    /**
     * 监测指标名称
     */
    private String sensCnName;

    /**
     * x轴数据（区域名称）
     */
    private String indoorZoneNames = "";

    /**
     * 区域幅度统计数据
     */
    private String indoorZoneMargin = "";

    /**
     * 区域对比统计数据
     */
    private String indoorZoneContrast = "";

    private String outdoorZoneNames = "";
    private String outdoorZoneMargin = "";
    private String outdoorZoneContrast = "";

    /**
     * 指标最高区域信息
     */
    private String highText;

    /**
     * 指标最低区域信息
     */
    private String lowText;

    /**
     * 区域幅度统计结论
     */
    private String indoorMarginConclusion;

    /**
     * 区域对比统计结论
     */
    private String indoorContrastConclusion;

    private String outdoorMarginConclusion;
    private String outdoorContrastConclusion;

    // 获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    public String execute() {

        getCurrentDate(); //获取当前时间
        try {
            sens = cacheProxy.loadAvailableSensorinfoOfSite(siteId);
            List<ZoneData> zoneDatas = zoneService.findRangStatsOfZones(siteId, target, date, dateType);
            //封装数据
            dataToJson(zoneDatas);

            log("数据分析", "区域对比统计");
        } catch (ExecutionException e) {
            log.error("区域对比统计", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 组装数据
     *
     * @param zoneDatas 区域数据
     * @author liuzhu
     * @date 2013-09-11
     */
    private void dataToJson(List<ZoneData> zoneDatas) {
        //获得计量单位
        if (sens != null && sens.size() > 0) {
            for (int i = 0; i < sens.size(); i++) {
                if (target == sens.get(i).getSensorPhysicalid()) {
                    uom = sens.get(i).getUnits();
                    sensCnName = sens.get(i).getCnName();
                    break;
                }
            }
        }

        for (ZoneData zd : zoneDatas) {
            if (zd.getPosition() == 1) {        // 室内
                //获得区域名称
                indoorZoneNames += zd.getZoneName() + "$";
                //获取区域指标最大值及最小值
                indoorZoneMargin += "[" + zd.getMinValue() + "," + zd.getMaxValue() + "],";
                //获取区域指标波动值
                indoorZoneContrast += zd.getWaveValue() + ",";
            } else if (zd.getPosition() == 2) {  // 室外
                //获得区域名称
                outdoorZoneNames += zd.getZoneName() + "$";
                //获取区域指标最大值及最小值
                outdoorZoneMargin += "[" + zd.getMinValue() + "," + zd.getMaxValue() + "],";
                //获取区域指标波动值
                outdoorZoneContrast += zd.getWaveValue() + ",";
            }
        }
        Concusion(zoneDatas); // 区域对比结论
        if (indoorZoneMargin != null && indoorZoneMargin.length() > 0) {
            indoorZoneMargin = "[" + indoorZoneMargin.substring(0, indoorZoneMargin.length() - 1) + "]";
        }
        if (indoorZoneContrast != null && indoorZoneContrast.length() > 0) {
            indoorZoneContrast = "[" + indoorZoneContrast.substring(0, indoorZoneContrast.length() - 1) + "]";
        }
        if (outdoorZoneMargin != null && outdoorZoneMargin.length() > 0) {
            outdoorZoneMargin = "[" + outdoorZoneMargin.substring(0, outdoorZoneMargin.length() - 1) + "]";
        }
        if (outdoorZoneContrast != null && outdoorZoneContrast.length() > 0) {
            outdoorZoneContrast = "[" + outdoorZoneContrast.substring(0, outdoorZoneContrast.length() - 1) + "]";
        }
    }

    /**
     * 区域对比结论
     *
     * @param zoneDatas 区域对比数据
     * @author liuzhu
     * @date 2013-09-11
     */
    private void Concusion(List<ZoneData> zoneDatas) {
        //时间处理
        if (dateType == 1) { //年
            formatTime = DateFormatUtils.format(date, "yyyy年");
        } else if (dateType == 2) { //月
            formatTime = DateFormatUtils.format(date, "yyyy年MM月");
        } else {
            formatTime = DateFormatUtils.format(date, "yyyy年MM月dd日");
        }

        List<ZoneData> indoorZoneData = null;
        List<ZoneData> outdoorZoneData = null;
        for (ZoneData zoneData : zoneDatas) {
            if (zoneData.getPosition() == 1) {
                if (indoorZoneData == null) {
                    indoorZoneData = Lists.newLinkedList();
                }
                indoorZoneData.add(zoneData);
            } else if (zoneData.getPosition() == 2) {
                if (outdoorZoneData == null) {
                    outdoorZoneData = Lists.newLinkedList();
                }
                outdoorZoneData.add(zoneData);
            }
        }

        if (indoorZoneData != null && indoorZoneData.size() > 0) {
            highText = "<strong>" + indoorZoneData.get(0).getZoneName() + "</strong>";
            lowText = "<strong>" + indoorZoneData.get(indoorZoneData.size() - 1).getZoneName() + "</strong>";
            indoorMarginConclusion = formatTime + "<br/>" + sensCnName + "幅度最大的室内区域是" + highText + "<br/>";
            indoorContrastConclusion = formatTime + "<br/>" + sensCnName + "变化最高的室内区域是" + highText + "<br/>";
            if (indoorZoneData.size() > 1) {
                indoorMarginConclusion += sensCnName + "幅度最小的室内区域是" + lowText + "<br/>";
                indoorContrastConclusion += sensCnName + "变化最低的室内区域是" + lowText + "<br/>";
            }
        }

        if (outdoorZoneData != null && outdoorZoneData.size() > 0) {
            highText = "<strong>" + outdoorZoneData.get(0).getZoneName() + "</strong>";
            lowText = "<strong>" + outdoorZoneData.get(outdoorZoneData.size() - 1).getZoneName() + "</strong>";
            outdoorMarginConclusion = formatTime + "<br/>" + sensCnName + "幅度最大的室外区域是" + highText + "<br/>";
            outdoorContrastConclusion = formatTime + "<br/>" + sensCnName + "变化最高的室外区域是" + highText + "<br/>";
            if (outdoorZoneData.size() > 1) {
                outdoorMarginConclusion += sensCnName + "幅度最小的是区域是" + lowText + "<br/>";
                outdoorContrastConclusion += sensCnName + "变化最低的区域是" + lowText + "<br/>";
            }
        }
    }

    /**
     * 获取当前时间
     *
     * @author 刘柱
     * @date 2013-09-11
     */
    private void getCurrentDate() {
        if (date == null) {
            Date dNow = new Date();   //当前时间
            date = new Date();


            Calendar calendar = Calendar.getInstance();  //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
            date = calendar.getTime();   //得到前一天的时间
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<SensorinfoVO> getSens() {
        return sens;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public void setSens(List<SensorinfoVO> sens) {
        this.sens = sens;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    public String getSensCnName() {
        return sensCnName;
    }

    public void setSensCnName(String sensCnName) {
        this.sensCnName = sensCnName;
    }

    public String getIndoorZoneNames() {
        return indoorZoneNames;
    }

    public void setIndoorZoneNames(String indoorZoneNames) {
        this.indoorZoneNames = indoorZoneNames;
    }

    public String getIndoorZoneMargin() {
        return indoorZoneMargin;
    }

    public void setIndoorZoneMargin(String indoorZoneMargin) {
        this.indoorZoneMargin = indoorZoneMargin;
    }

    public String getIndoorZoneContrast() {
        return indoorZoneContrast;
    }

    public void setIndoorZoneContrast(String indoorZoneContrast) {
        this.indoorZoneContrast = indoorZoneContrast;
    }

    public String getOutdoorZoneNames() {
        return outdoorZoneNames;
    }

    public void setOutdoorZoneNames(String outdoorZoneNames) {
        this.outdoorZoneNames = outdoorZoneNames;
    }

    public String getOutdoorZoneMargin() {
        return outdoorZoneMargin;
    }

    public void setOutdoorZoneMargin(String outdoorZoneMargin) {
        this.outdoorZoneMargin = outdoorZoneMargin;
    }

    public String getOutdoorZoneContrast() {
        return outdoorZoneContrast;
    }

    public void setOutdoorZoneContrast(String outdoorZoneContrast) {
        this.outdoorZoneContrast = outdoorZoneContrast;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getHighText() {
        return highText;
    }

    public void setHighText(String highText) {
        this.highText = highText;
    }

    public String getLowText() {
        return lowText;
    }

    public void setLowText(String lowText) {
        this.lowText = lowText;
    }

    public String getIndoorMarginConclusion() {
        return indoorMarginConclusion;
    }

    public void setIndoorMarginConclusion(String indoorMarginConclusion) {
        this.indoorMarginConclusion = indoorMarginConclusion;
    }

    public String getIndoorContrastConclusion() {
        return indoorContrastConclusion;
    }

    public void setIndoorContrastConclusion(String indoorContrastConclusion) {
        this.indoorContrastConclusion = indoorContrastConclusion;
    }

    public String getOutdoorMarginConclusion() {
        return outdoorMarginConclusion;
    }

    public void setOutdoorMarginConclusion(String outdoorMarginConclusion) {
        this.outdoorMarginConclusion = outdoorMarginConclusion;
    }

    public String getOutdoorContrastConclusion() {
        return outdoorContrastConclusion;
    }

    public void setOutdoorContrastConclusion(String outdoorContrastConclusion) {
        this.outdoorContrastConclusion = outdoorContrastConclusion;
    }
}

