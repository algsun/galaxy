package com.microwise.blueplanet.action.location.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 位置点查询
 *
 * @author xuyuexi
 * @date 14-6-23
 */
@Beans.Action
@Blueplanet
public class QueryLocationAction extends BlueplanetLoggerAction {

    public static final int SIZE_PER_PAGE = 10;
    public static final String _pagePath = "/blueplanet/pages/location/manage/query-locations.ftl";
    public static final Logger logger = LoggerFactory.getLogger(QueryLocationAction.class);
    @Autowired
    private LocationService locationService;

    /**
     * 查询出来的位置点
     */
    List<LocationVO> locationList;
    /**
     * 当前页, 从 1 开始
     */
    private int page = 1;

    /**
     * 总页数
     */
    private int pageSum;

    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 位置点集合
     */
    private String locationIds;

    /**
     * 位置点名称
     */
    private String locationName;

    @Route(value = "/blueplanet/queryLocations")
    public String defaultView() {
        try {
            queryData();
        } catch (Exception e) {
            logger.error("查询位置点错误", e);
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout");
    }

    @Route(value = "/blueplanet/queryLocations", params = {"query", "page"})
    public String pageView() {
        try {
            queryData();
        } catch (Exception e) {
            logger.error("查询位置点错误", e);
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout");
    }

    /**
     * 根据zoneId或者locationName查询
     *
     * @return
     */
    @Route(value = "/blueplanet/queryLocationsBy")
    public String query() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            locationList = locationService.findLocationByNameAndZone(locationName, zoneId, siteId, page, SIZE_PER_PAGE);
            int locationCount = locationService.findLocationByNameAndZoneCount(locationName, zoneId, siteId);
            pageSum = PagingUtil.pagesCount(locationCount, SIZE_PER_PAGE);
            log("位置点管理", "查询位置点");
        } catch (Exception e) {
            logger.error("查询位置点错误", e);
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout");
    }


    private void queryData() {
        String siteId = Sessions.createByAction().currentSiteId();
        locationList = locationService.findLocationsBySiteId(siteId, page, SIZE_PER_PAGE);
        int locationCount = locationService.findLocationListCount(siteId);
        pageSum = PagingUtil.pagesCount(locationCount, SIZE_PER_PAGE);
        log("位置点管理", "查询位置点");
    }

    public List<LocationVO> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationVO> locationList) {
        this.locationList = locationList;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(String locationIds) {
        this.locationIds = locationIds;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
