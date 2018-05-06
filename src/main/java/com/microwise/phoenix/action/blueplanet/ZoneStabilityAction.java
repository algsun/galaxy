package com.microwise.phoenix.action.blueplanet;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.CacheProxy;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZoneStability;
import com.microwise.phoenix.service.ZoneStabilityService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.microwise.phoenix.util.ZoneStabilityComparator;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 区域稳定性对比
 *
 * @author: wanggeng
 * @date: 13-8-5 下午5:24
 * @check 2013-08-14 zhangpeng svn:5044
 */
@Beans.Action
@Phoenix
public class ZoneStabilityAction extends PhoenixLoggerAction {
    /**
     * 日志
     */
    public static final Logger log = LoggerFactory.getLogger(ZoneStabilityAction.class);

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../blueplanet/zone-stability.ftl";

    /**
     * 环境监测下，缓存的代理
     */
    @Autowired
    private CacheProxy cacheProxy;

    /**
     * 区域稳定性Service
     */
    @Autowired
    private ZoneStabilityService zoneStabilityService;

    //input

    /**
     * 监测指标 默认值：温度
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

    //output
    /**
     * 结论日期
     */
    private String formatTime;

    /**
     * 图形标题
     */
    private String sensCnName;

    /**
     * 环境指标集合
     */
    private List<SensorinfoVO> sens;

    /**
     * 区域集合列表json
     */
    private String zoneNames = "";

    /**
     * 区域稳定性数据
     */
    private String stabilityData = "";

    /**
     * 区域集合列表Desc
     */
    private String zoneNamesDc = "";

    /**
     * 区域稳定性数据Desc
     */
    private String stabilityDataDc = "";

    /**
     * 结论
     */
    private String backup;

    /**
     * 结论Desc
     */
    private String backupDc;

    // 获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    /**
     * 组装区域稳定性页面图表需要的数据
     *
     * @return 页面
     */
    @Route("/phoenix/blueplanet/zoneStability")
    public String execute() {
        getCurrentDate(); //获取当前时间
        init();//初始化页面信息
        List<ZoneStability> zoneStabilityList = zoneStabilityService.findZoneStability(siteId, target, date, dateType);

        dataToJson(zoneStabilityList);
        log("数据分析", "区域稳定性对比");
        return Results.ftl("/phoenix/pages/index/layout.ftl");
    }

    /**
     * 组装数据
     *
     * @param zoneStabilityList 区域稳定性数据
     * @author liuzhu
     * @date 2013-09-12
     */
    private void dataToJson(List<ZoneStability> zoneStabilityList) {
        ZoneStabilityComparator zoneStabilityComparatorDesc = new ZoneStabilityComparator(true);
        Collections.sort(zoneStabilityList, zoneStabilityComparatorDesc);

        conclusion(zoneStabilityList);//结论
        if (zoneNames.length() > 0) {
            zoneNames = "[" + zoneNames.substring(0, zoneNames.length() - 1) + "]";
        }
        if (stabilityData.length() > 0) {
            stabilityData = "[" + stabilityData.substring(0, stabilityData.length() - 1) + "]";
        }
    }

    /**
     * 区域稳定性结论
     *
     * @param zoneStabilityList 区域稳定性list
     * @author liuzhu
     * @date 2013-09-12
     */
    private void conclusion(List<ZoneStability> zoneStabilityList) {
        if (zoneStabilityList != null && zoneStabilityList.size() > 0) {
            backup += "<br/>最稳定的区域是：<strong>" + zoneStabilityList.get(0).getZoneName() + "</strong>";
            if (zoneStabilityList.size() > 1) {
                backup += "<br/>最不稳定的区域是：<strong>" + zoneStabilityList.get(zoneStabilityList.size() - 1).getZoneName() + "</strong>";
            }
            for (ZoneStability zoneStability : zoneStabilityList) {
                zoneNames += "'" + zoneStability.getZoneName() + "',";
                stabilityData += zoneStability.getStability() + ",";
            }
        }
    }

    /**
     * 初始化界面展示信息
     * <p/>
     * 初始化默认时间信息，标题中文格式化，及环境指标
     * <p/>
     * 区域稳定性对比，目前暂时只计算
     * 湿度（%）	温度（℃）	二氧化碳（ppm）	光照（lux）
     * 这四个环境指标
     * <p/>
     * return void
     */
    private void init() {
        try {
            //初始化time

            if (dateType == Constants.FIND_TYPE_YEAR) { //年
                formatTime = DateFormatUtils.format(date, "yyyy年");
            } else if (dateType == Constants.FIND_TYPE_MONTH) { //月
                formatTime = DateFormatUtils.format(date, "yyyy年MM月");
            } else {
                formatTime = DateFormatUtils.format(date, "yyyy年MM月dd日");
            }
            backup = formatTime;

            sens = new ArrayList<SensorinfoVO>();
            List<SensorinfoVO> sensorinfoVOs = cacheProxy.loadAvailableSensorinfoOfSite(siteId);


            for (SensorinfoVO sensorinfoVO : sensorinfoVOs) {
                if (sensorinfoVO.getSensorPhysicalid() == 32 ||
                        sensorinfoVO.getSensorPhysicalid() == 33 ||
                        sensorinfoVO.getSensorPhysicalid() == 36 ||
                        sensorinfoVO.getSensorPhysicalid() == 41) {
                    sens.add(sensorinfoVO);
                }

                if (sensorinfoVO.getSensorPhysicalid() == target) {
                    sensCnName = sensorinfoVO.getCnName();
                    backup += "【" + sensorinfoVO.getCnName() + "】";
                }
                backupDc = backup;
            }

        } catch (Exception e) {
            log.error("区域稳定性对比", e);
        }
    }

    /**
     * 当前时间
     *
     * @author 刘柱
     * @date 2013-09-11
     */
    private void getCurrentDate() {
        if (date == null) {
            date = new Date();
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SensorinfoVO> getSens() {
        return sens;
    }

    public void setSens(List<SensorinfoVO> sens) {
        this.sens = sens;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getZoneNames() {
        return zoneNames;
    }

    public void setZoneNames(String zoneNames) {
        this.zoneNames = zoneNames;
    }

    public String getZoneNamesDc() {
        return zoneNamesDc;
    }

    public void setZoneNamesDc(String zoneNamesDc) {
        this.zoneNamesDc = zoneNamesDc;
    }

    public String getBackupDc() {
        return backupDc;
    }

    public void setBackupDc(String backupDc) {
        this.backupDc = backupDc;
    }

    public String getStabilityData() {
        return stabilityData;
    }

    public void setStabilityData(String stabilityData) {
        this.stabilityData = stabilityData;
    }

    public String getStabilityDataDc() {
        return stabilityDataDc;
    }

    public void setStabilityDataDc(String stabilityDataDc) {
        this.stabilityDataDc = stabilityDataDc;
    }

    public String getSensCnName() {
        return sensCnName;
    }

    public void setSensCnName(String sensCnName) {
        this.sensCnName = sensCnName;
    }
}
