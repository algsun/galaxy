package com.microwise.phoenix.action.blueplanet;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.po.LocationDate;
import com.microwise.phoenix.service.ZoneDataDistributionService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.microwise.uma.util.DateTypeGenerator;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 数据分析 - 区域数据分布
 * author : chen.yaofei
 * date : 2016-10-08
 */

@Beans.Action
@Phoenix
public class ZoneDataDistributionAction extends PhoenixLoggerAction {
    /**
     * 日志
     */
    public static final Logger log = LoggerFactory.getLogger(ZoneDataDistributionAction.class);

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../blueplanet/zone-data-distribution.ftl";

    /**
     *区域数据分布service
     */
    @Autowired
    private ZoneDataDistributionService zoneDataDistribution;

    /**
     * 区域管理代理
     */
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;

    /**
     * 区域service
     */
    @Autowired
    private ZoneService zoneService;

    /**
     * 区域Id
     */
    private String zoneId;

    /**
     * 区域名
     */
    private String zoneName;

    /**
     * 技术指标 默认值：温度
     */
    private int sensorId = Constants.ChartConstants.SENSORINFO_TMT;

    /**
     * 日期类型
     * 1.年 2.月 3.日
     * 默认值：日
     */
    private int dateType = Constants.FIND_TYPE_DAY;

    /**
     * 日期 默认值：昨天
     */
    private Date date;

    /**
     * 环境指标集合
     */
    private List<SensorinfoVO> sens;

    /**
     * 选中的监测指标名称
     */
    private String sensCnName;

    /**
     * 选中的监测指标计量单位
     */
    private String sensUnits;

    /**
     * 区域位置点数据
     */
    private List<LocationDate> locationDateList;

    /**
     * 箱式图数据
     */
    Map<String, List<Double>> boxplotDataMap = new HashMap<String, List<Double>>();


    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/phoenix/blueplanet/zoneDataDistribution")
    public String execute() {

        initDate(); //获时间
        init(); //初始化选择条件
        try {

            //查找数据
            Date startDate = DateTypeGenerator.start(dateType, date);
            Date endDate = DateTypeGenerator.end(dateType, date);
            locationDateList = zoneDataDistribution.findDataDistribution(zoneId, sensorId, startDate, endDate);

            for (LocationDate locationDate : locationDateList) {
//                //组织箱子图数据
                List<Double> fourQuantileList = zoneDataDistribution.findFourQuantile(locationDate.getLocationId(), sensorId, startDate, endDate, locationDate.getSensorPrecision());
                boxplotDataMap.put(locationDate.getLocationId(), fourQuantileList);
            }
            log("数据分析", "区域数据分布");
        } catch (Exception e) {
            log.error("区域数据分布", e);
        }
        return Results.ftl("/phoenix/pages/index/layout.ftl");
    }


    /**
     * 获取前一天时间
     */
    private void initDate() {
        if (date == null) {
            date = DateTime.now().minusDays(1).toDate();
        }
    }

    /**
     * 初始化界面展示信息
     * <p/>
     * 初始化默认时间信息，标题中文格式化，及环境指标
     * return void
     */
    private void init() {
        try {
            //初始化time
            if (Strings.isNullOrEmpty(zoneId)) {
                List<ZoneVO> zoneList = zoneManagerProxy.findZoneList(siteId, Strings.emptyToNull(zoneId));
                if (zoneList.size() > 0) {
                    ZoneVO zone = zoneList.get(0);
                    zoneId = zone.getZoneId();
                    zoneName = zone.getZoneName();
                }
            }

            sens = new ArrayList<SensorinfoVO>();
            List<SensorinfoVO> sensorinfoVOs = zoneService.findSensorinfo(zoneId);

            for (SensorinfoVO sensorinfoVO : sensorinfoVOs) {
                sens.add(sensorinfoVO);
                if (sensorinfoVO.getSensorPhysicalid() == sensorId) {
                    sensCnName = sensorinfoVO.getCnName();
                    sensUnits = sensorinfoVO.getUnits();
                }
            }

        } catch (Exception e) {
            log.error("区域数据分布", e);
        }
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getSensUnits() {
        return sensUnits;
    }

    public void setSensUnits(String sensUnits) {
        this.sensUnits = sensUnits;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
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

    public String getSensCnName() {
        return sensCnName;
    }

    public void setSensCnName(String sensCnName) {
        this.sensCnName = sensCnName;
    }

    public List<LocationDate> getLocationDateList() {
        return locationDateList;
    }

    public void setLocationDateList(List<LocationDate> locationDateList) {
        this.locationDateList = locationDateList;
    }

    public Map<String, List<Double>> getBoxplotDataMap() {
        return boxplotDataMap;
    }

    public void setBoxplotDataMap(Map<String, List<Double>> boxplotDataMap) {
        this.boxplotDataMap = boxplotDataMap;
    }
}
