package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCConfigPO;
import com.microwise.blueplanet.bean.po.DCLayoutPO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * 数据中心图表展示Action
 *
 * @author wang.geng
 * @date 13-11-26 下午2:43
 * @check @liu.zhu wang.geng 2013-12-18 #7228
 * @check @xie.deng li.jianfei 2014-3-5 # 8053
 */
@Beans.Action
@Blueplanet
public class DcChartsIndexAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(DcChartsIndexAction.class);

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-add-layout.ftl";

    /**
     * 数据中心服务
     */
    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private LocationService locationService;
    // output

    /**
     * 行政站点子站点列表
     */
    private List<SiteVO> siteVOList;

    /**
     * 设备监测指标
     */
    private List<SensorinfoVO> locationSensorInfo;

    //input
    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 行政站点的子站点ID
     */
    private String siteVOId = "";

    /**
     * 布局uuid
     */
    private String uuid;

    /**
     * 监测指标id
     */
    private Integer sensorPhysicalId;

    /**
     * 控件DIV id
     */
    private String divId;

    /**
     * 布局名称
     */
    private String layoutDescription;

    /**
     * 操作标记：preview为布局预览;edit为编辑操作
     */
    private String operMark = "add";

    /**
     * 请求url
     */
    private String url;

    /**
     * 序列化后的参数
     */
    private String serializationParams;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    //初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator+ "blueplanet"+File.separator+"images"+File.separator+"dataCenterConf";
    }

    /**
     * 是否配置了背景图片
     */
    private boolean hasBgUrl;

    /**
     * siteId站点id
     */
    private String siteId;

    @Route("/blueplanet/dataCenter/charts/index/{uuid}")
    public String execute() {
        try {
            siteId = Sessions.createByAction().currentSiteId();
        } catch (Exception e) {
            log.error("数据中心(图表组件化),添加图表页面", e);
        }
        log("数据中心(图表组件化)", "添加图表页面");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    @Route("/blueplanet/dataCenter/charts/addLayout/{uuid}")
    public String addLayout() {
        try {
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            dataCenterService.saveLayout(new DCLayoutPO(uuid, layoutDescription, logicGroupId));
            DCConfigPO dcConfigPO = dataCenterService.findDCConfig(uuid);
            hasBgUrl = dcConfigPO != null;
        } catch (Exception e) {
            log.error("数据中心(图表组件化),添加布局", e);
        }
        log("数据中心(图表组件化)", "添加布局");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDivId() {
        return divId;
    }

    public void setDivId(String divId) {
        this.divId = divId;
    }


    public String getLayoutDescription() {
        return layoutDescription;
    }

    public void setLayoutDescription(String layoutDescription) {
        this.layoutDescription = layoutDescription;
    }

    public String getOperMark() {
        return operMark;
    }

    public void setOperMark(String operMark) {
        this.operMark = operMark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSerializationParams() {
        return serializationParams;
    }

    public void setSerializationParams(String serializationParams) {
        this.serializationParams = serializationParams;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public boolean isHasBgUrl() {
        return hasBgUrl;
    }

    public void setHasBgUrl(boolean hasBgUrl) {
        this.hasBgUrl = hasBgUrl;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<SiteVO> getSiteVOList() {
        return siteVOList;
    }

    public void setSiteVOList(List<SiteVO> siteVOList) {
        this.siteVOList = siteVOList;
    }

    public String getSiteVOId() {
        return siteVOId;
    }

    public void setSiteVOId(String siteVOId) {
        this.siteVOId = siteVOId;
    }
}
