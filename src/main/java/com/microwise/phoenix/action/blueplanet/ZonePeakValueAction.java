package com.microwise.phoenix.action.blueplanet;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.CacheProxy;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZonePeakValue;
import com.microwise.phoenix.service.ZonePeakValueService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 区域监测指标峰值
 *
 * @author: xuyuexi
 * @date: 14-9-25
 */
@Beans.Action
@Phoenix
public class ZonePeakValueAction extends PhoenixLoggerAction {
    /**
     * 日志
     */
    public static final Logger log = LoggerFactory.getLogger(ZonePeakValueAction.class);

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../blueplanet/zone-peak-value.ftl";

    /**
     * 环境监测下，缓存的代理
     */
    @Autowired
    private CacheProxy cacheProxy;

    /**
     * 区域均峰值Service
     */
    @Autowired
    private ZonePeakValueService zonePeakValueService;

    //input

    /**
     * 监测指标 默认值：温度
     */
    private int sensorId;

    /**
     * 类型 默认值：全部  0全部，1室内，2室外
     */
    private int type = 0;

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
     * 区域均峰值数据
     */
    private String peakValueData = "";

    /**
     * 区域集合列表Desc
     */
    private String zoneNamesDc = "";

    /**
     * 区域均峰值数据Desc
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
     * 组装区域峰值页面图表需要的数据
     *
     * @return 页面
     */
    @Route("/phoenix/blueplanet/peakValue")
    public String execute() {
        getCurrentDate(); //获取当前时间
        init();//初始化页面信息
        List<ZonePeakValue> zonePeakValues = zonePeakValueService.findZonePeakValue(siteId, sensorId, date, type);
        if (zonePeakValues.size() > 0) {
            dataToJson(zonePeakValues);
        }
        log("数据分析", "区域检测指标峰值");
        return Results.ftl("/phoenix/pages/index/layout.ftl");
    }

    /**
     * 组装数据
     *
     * @param zonePeakValues 区域峰值数据
     * @author xuyuexi
     * @date 2013-09-12
     */
    private void dataToJson(List<ZonePeakValue> zonePeakValues) {

        conclusion(zonePeakValues);//结论
        if (zoneNames.length() > 0) {
            zoneNames = "[" + zoneNames.substring(0, zoneNames.length() - 1) + "]";
        }
        if (peakValueData.length() > 0) {
            peakValueData = "[" + peakValueData.substring(0, peakValueData.length() - 1) + "]";
        }
    }

    /**
     * 区域峰值结论
     *
     * @param zonePeakValueList 区域稳定性list
     * @author xuyeuxi
     * @date 2014-09-26
     */
    private void conclusion(List<ZonePeakValue> zonePeakValueList) {
        if (zonePeakValueList != null && zonePeakValueList.size() > 0 && !zonePeakValueList.isEmpty()) {
            backup += "值最大的月份是<strong>" + compare(zonePeakValueList);

            for (ZonePeakValue ZonePeakValue : zonePeakValueList) {
                zoneNames += "'" + ZonePeakValue.getZoneName() + "-" + ZonePeakValue.getMonth() + "月',";
                peakValueData += ZonePeakValue.getPeakValue() + ",";
            }
        }
    }

    private String compare(List<ZonePeakValue> zonePeakValueList) {
        Double compare = 0.0;
        String data = "";
        for (ZonePeakValue zonePeakValue : zonePeakValueList) {
            if (zonePeakValue != null) {
                if (compare <= zonePeakValue.getPeakValue()) {
                    compare = zonePeakValue.getPeakValue();
                    data = zonePeakValue.getMonth() + "月 </strong>" + "区域是<strong>" + zonePeakValue.getZoneName() + "</strong>";
                }
            }
        }
        return data;
    }

    /**
     * 初始化界面展示信息
     * <p/>
     * 初始化默认时间信息，标题中文格式化，及环境指标
     * return void
     */
    private void init() {
        try {
            if (sensorId == 0) {
                sensorId = 33;
            }
            //初始化time
            formatTime = DateFormatUtils.format(date, "yyyy年");
            backup = formatTime;

            sens = new ArrayList<SensorinfoVO>();
            List<SensorinfoVO> sensorinfoVOs = cacheProxy.loadAvailableSensorinfoOfSite(siteId);

            for (SensorinfoVO sensorinfoVO : sensorinfoVOs) {
                sens.add(sensorinfoVO);
                if (sensorinfoVO.getSensorPhysicalid() == sensorId) {
                    sensCnName = sensorinfoVO.getCnName();
                    backup += "【" + sensorinfoVO.getCnName() + "】";
                }
                backupDc = backup;
            }

        } catch (Exception e) {
            log.error("区域检测指标峰值", e);
        }
    }

    /**
     * 当前时间
     *
     * @author 许月希
     * @date 2014-09-25
     */
    private void getCurrentDate() {
        if (date == null) {
            date = new Date();
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
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

    public String getPeakValueData() {
        return peakValueData;
    }

    public void setPeakValueData(String peakValueData) {
        this.peakValueData = peakValueData;
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
