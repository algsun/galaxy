package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ContentBase;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 站点概览
 *
 * @author liuzhu
 * @date 2013-11-1
 * check xie.deng  2013-11-14  #6605
 */
@Beans.Action
@Blueplanet
@ContentBase("/blueplanet/pages/site")
public class SiteAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(SiteAction.class);

    /**
     * 站点service
     */
    @Autowired
    private SiteService siteService;

    /**
     * 内容页面
     */
    private static final String _pagePath = "index.ftl";

    /**
     * 站点概览左边的数据
     */
    private List<LocationDataVO> findLocationDataFiveDay;

    /**
     * 站点概览右边的数据 key为区域的名称，value为nodeSensorVO的集合
     */
    private Map<String,List<LocationDataVO>> findLocationDataRealTimeAvg;

    /**
     * 站点概览
     */
    @Route("/blueplanet/site")
    public String index() {
        try {
            //获取站点id
            String siteId = Sessions.createByAction().currentSiteId();
            //站点概览左边的数据
            findLocationDataFiveDay = siteService.findSiteChartData(siteId);
            //站点概览右边的数
            findLocationDataRealTimeAvg = siteService.findSiteRealTimeData(siteId);
        } catch (Exception e) {
            log.error("站点概览",e);
        }
        log("站点概览", "环境监控");
        return Results.ftl("/blueplanet/pages/site/layout.ftl");
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public List<LocationDataVO> getFindLocationDataFiveDay() {
        return findLocationDataFiveDay;
    }

    public void setFindLocationDataFiveDay(List<LocationDataVO> findLocationDataFiveDay) {
        this.findLocationDataFiveDay = findLocationDataFiveDay;
    }

    public Map<String, List<LocationDataVO>> getFindLocationDataRealTimeAvg() {
        return findLocationDataRealTimeAvg;
    }

    public void setFindLocationDataRealTimeAvg(Map<String, List<LocationDataVO>> findLocationDataRealTimeAvg) {
        this.findLocationDataRealTimeAvg = findLocationDataRealTimeAvg;
    }
}
