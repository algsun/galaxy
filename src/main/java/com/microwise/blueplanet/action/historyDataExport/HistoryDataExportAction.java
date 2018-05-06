package com.microwise.blueplanet.action.historyDataExport;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 历史数据
 *
 * @author bai.weixing
 * @since 2018/01/12
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class HistoryDataExportAction extends BlueplanetLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(HistoryDataExportAction.class);

    // 内容页面
    private static final String _pagePath = "history-data-export.ftl";

    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 区域id
     */
    private String zoneId;
    /**
     * 位置点id
     */
    private String locationId;
    /**
     * 区域集合
     */
    private List<ZoneVO> zoneVOList;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;


    private String siteId = Sessions.createByAction().currentSiteId();

    /**
     * 历史数据导出首页
     */
    @Route(value = "/blueplanet/history-data-export")
    public String index() {

        try {
            // 默认当天时间
            startTime = DateTime.now().withTimeAtStartOfDay().toDate();
            endTime = DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
            zoneVOList = zoneService.findZonesBySiteId(siteId);
            logger.info("历史数据");
        } catch (Exception e) {
            logger.error("历史数据", e);
        }
        return Results.ftl("/blueplanet/pages/historyDataExport/history-data-layout");
    }


    @Route(value = "/blueplanet/locations", params = {"zoneId"})
    public String getLocations() {
        List<LocationVO> locationVOList = locationService.findLocationsByZoneId(zoneId, false);
        return Results.json().asRoot(locationVOList).done();
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public List<ZoneVO> getZoneVOList() {
        return zoneVOList;
    }

    public void setZoneVOList(List<ZoneVO> zoneVOList) {
        this.zoneVOList = zoneVOList;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
