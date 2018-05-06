package com.microwise.biela.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.io.Resources;
import com.microwise.biela.bean.po.SitePO;
import com.microwise.biela.bean.vo.LocationInfoVO;
import com.microwise.biela.service.MapIndexService;
import com.microwise.biela.sys.Biela;
import com.microwise.biela.sys.BielaLoggerAction;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 站点地图概览页面.
 *
 * @author wang.geng
 * @date 13-12-31  下午1:33
 * @check @liu.zhu #7678 2014-1-17
 */
@Beans.Action
@Biela
public class MapIndexAction {

    /**
     * 日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(MapIndexAction.class);

    private static final String CONFIG_KEY = "qinshihuang";
    private static final String CONFIG_YMAL_URL = "config.yml";

    /**
     * 日志注入
     */
    @Autowired
    private BielaLoggerAction bielaLoggerAction;

    /**
     * 站点地图服务
     */
    @Autowired
    private MapIndexService mapIndexService;

    /**
     * 页面
     */
    public static final String _pagePath = "../mapIndex/mapview-index.ftl";

    /**
     * 传入的站点ID
     */
    private String siteId;

    /**
     * 站点组List对象
     */
    private List<SitePO> sitePOList;

    /**
     * 站点平局数据对象
     */
    private List<LocationInfoVO> locationInfoList;

    @Route("/biela/mapOverview")
    public String execute() {
        return Results.ftl("/biela/pages/index/layout.ftl");
    }

    @Route("/biela/mapOverview/getSiteDatas.json")
    public String getSiteDatas() {
        try {
            User user = Sessions.createByAction().currentUser();
            LogicGroup logicGroup = Sessions.createByAction().currentLogicGroup();
            sitePOList = mapIndexService.findSitePOList(logicGroup.getId(), user.getId());
            //增加友情站点链接
            List<String> siteParamList = loadYML(CONFIG_KEY);
            //友情链接只有4个参数
            if (siteParamList.size() == 4) {
                SitePO bmySitePO = new SitePO();
                //设置SitePO的ID为-1，表明该站点为友情链接站点
                bmySitePO.setId(-1);
                String friendlySiteId = siteParamList.get(0);
                bmySitePO.setSiteId(friendlySiteId);
                bmySitePO.setLogicGroupName(siteParamList.get(1));
                bmySitePO.setLngBaiDu(Double.parseDouble(siteParamList.get(2)));
                bmySitePO.setLatBaiDu(Double.parseDouble(siteParamList.get(3)));
                bmySitePO.setTemperatureRank("warning");
                bmySitePO.setAreaCodePO(mapIndexService.findAreaCodePOBySiteId(friendlySiteId));
                sitePOList.add(bmySitePO);
            }
            //计算地图中心点辅助站点实体
            if (sitePOList.size() > 2) {
                setMaxSitePOAndMinSitePO(sitePOList);
            }
            bielaLoggerAction.log("站点地图", "获取站点列表");
        } catch (Exception e) {
            log.error("站点地图", e);
        }
        return Results.json().root("sitePOList").done();
    }

    @Route("/biela/mapOverview/getSiteOverview.json")
    public String getSiteOverview() {
        try {
            locationInfoList = mapIndexService.findGeneralLocationInfoBySite(siteId);
            bielaLoggerAction.log("站点地图", "获取定制检测指标");
        } catch (Exception e) {
            log.error("站点地图", e);
        }
        return Results.json().root("locationInfoList").done();
    }

    /**
     * 获取最大经度点，最小经度点，最大纬度点，最小纬度点
     * 并计算最大最小纬度点的距离与最大最小经度点的距离
     * 前台页面根据这两个距离中较大的计算结果的两个点来绘制地图
     *
     * @param sitePOList 基层站点列表
     * @return 结果MAP
     */
    private void setMaxSitePOAndMinSitePO(List<SitePO> sitePOList) {

        SitePO maxSite = new SitePO();
        SitePO minSite = new SitePO();
        SitePO maxLntPoint = new SitePO();
        SitePO minLntPoint = new SitePO();
        SitePO maxLatPoint = new SitePO();
        SitePO minLatPoint = new SitePO();

        //最大经度点
        double maxLngPointLng = 0;
        double maxLngPointLat = 0;
        //最小经度点
        double minLngPointLng = 0;
        double minLngPointLat = 0;
        //最大纬度点
        double maxLatPointLng = 0;
        double maxLatPointLat = 0;
        //最小纬度点
        double minLatPointLng = 0;
        double minLatPointLat = 0;

        Map<Double, Double> lngLatMap = new HashMap<Double, Double>();
        maxLntPoint.setLogicGroupName("maxLntPoint");
        minLntPoint.setLogicGroupName("minLntPoint");
        maxLatPoint.setLogicGroupName("maxLatPoint");
        minLatPoint.setLogicGroupName("minLatPoint");
        maxSite.setLogicGroupName("maxLngLatSite");
        minSite.setLogicGroupName("minLngLatSite");

        //利用TreeSet自动排序功能分别对经纬度进行排序得出两个Set
        //并记录点的经纬度对应关系
        TreeSet<Double> lngSet = new TreeSet<Double>();
        TreeSet<Double> latSet = new TreeSet<Double>();
        for (SitePO site : sitePOList) {
            lngSet.add(site.getLngBaiDu());
            latSet.add(site.getLatBaiDu());
            lngLatMap.put(site.getLngBaiDu(), site.getLatBaiDu());
        }

        //获取这四个点的经纬度
        for (Double d : lngLatMap.keySet()) {
            if (lngSet.first().equals(d)) {
                minLngPointLng = d;
                minLngPointLat = lngLatMap.get(d);
            }

            if (lngSet.last().equals(d)) {
                maxLngPointLng = d;
                maxLngPointLat = lngLatMap.get(d);
            }

            if (latSet.first().equals(lngLatMap.get(d))) {
                minLatPointLng = d;
                minLatPointLat = lngLatMap.get(d);
            }

            if (latSet.last().equals(lngLatMap.get(d))) {
                maxLatPointLng = d;
                maxLatPointLat = lngLatMap.get(d);
            }
        }

        //计算最大最小经度两点间的距离
        double lngDis = distanceByLngLat(maxLngPointLng, maxLngPointLat, minLngPointLng, minLngPointLat);
        //计算最大最小纬度两点间的距离
        double latDis = distanceByLngLat(maxLatPointLng, maxLatPointLat, minLatPointLng, minLatPointLat);

        if (lngDis >= latDis) {
            maxSite.setLngBaiDu(maxLngPointLng);
            maxSite.setLatBaiDu(maxLngPointLat);
            minSite.setLngBaiDu(minLngPointLng);
            minSite.setLatBaiDu(minLngPointLat);
        } else {
            maxSite.setLngBaiDu(maxLatPointLng);
            maxSite.setLatBaiDu(maxLatPointLat);
            minSite.setLngBaiDu(minLatPointLng);
            minSite.setLatBaiDu(minLatPointLat);
        }

        maxLntPoint.setLngBaiDu(maxLngPointLng);
        maxLntPoint.setLatBaiDu(maxLngPointLat);
        minLntPoint.setLngBaiDu(minLngPointLng);
        minLntPoint.setLatBaiDu(minLngPointLat);
        maxLatPoint.setLngBaiDu(maxLatPointLng);
        maxLatPoint.setLatBaiDu(maxLatPointLat);
        minLatPoint.setLngBaiDu(minLatPointLng);
        minLatPoint.setLatBaiDu(minLatPointLat);


        sitePOList.add(maxSite);
        sitePOList.add(minSite);
        sitePOList.add(maxLntPoint);
        sitePOList.add(minLntPoint);
        sitePOList.add(maxLatPoint);
        sitePOList.add(minLatPoint);
    }

    /**
     * 计算两经纬度点间距离
     *
     * @param lng1 点1经度
     * @param lat1 点1纬度
     * @param lng2 点2经度
     * @param lat2 点2纬度
     * @return 距离
     */
    private double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    /**
     * 友情链接，加载YML配置文件
     */
    public List<String> loadYML(String key) {
        InputStream input = null;
        try {
            input = Resources.getResource(CONFIG_YMAL_URL).openStream();
            Map<String, Object> config = (Map<String, Object>) new Yaml().load(input);
            List<String> siteUrlMap = (List<String>) config.get(key);
            if (siteUrlMap != null) {
                return siteUrlMap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<SitePO> getSitePOList() {
        return sitePOList;
    }

    public void setSitePOList(List<SitePO> sitePOList) {
        this.sitePOList = sitePOList;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<LocationInfoVO> getLocationInfoList() {
        return locationInfoList;
    }

    public void setLocationInfoList(List<LocationInfoVO> locationInfoList) {
        this.locationInfoList = locationInfoList;
    }
}
