package com.microwise.biela.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.biela.bean.po.LogicGroupPO;
import com.microwise.biela.bean.po.ZoneLocationPO;
import com.microwise.biela.bean.vo.CustomizeVO;
import com.microwise.biela.service.MapIndexService;
import com.microwise.biela.sys.BielaLoggerAction;
import com.microwise.common.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 定制检测指标action
 *
 * @author liuzhu
 * @date 13-12-31  下午1:33
 * @check @wang.geng #7674 2014-1-17
 */
public class CustomizeAction {

    /**
     * 日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(CustomizeAction.class);

    /**
     * 站点地图service
     */
    @Autowired
    private MapIndexService mapIndexService;

    /**
     * 比拉日志操作
     */
    @Autowired
    private BielaLoggerAction logger;

    /**
     * 站点组po
     */
    private LogicGroupPO logicGroupPO;

    /**
     * 监测指标id
     */
    private Integer sensorId;

    /**
     * 设备list（按照区域分组）
     */
    private List<ZoneLocationPO> zoneLocationPOList;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 定制voList
     */
    private List<CustomizeVO> customizeVOList;

    /**
     * 定制id
     */
    private Integer id;

    /**
     * ajax请求返回的标识
     */
    private Boolean flag = false;

    /**
     * 页面
     */
    public static final String _pagePath = "../mapIndex/customize.ftl";

    @Route("/biela/customize/{siteId}")
    public String execute() {
        try {
            logicGroupPO = mapIndexService.findLogicGroupBySiteId(siteId);
            logicGroupPO.setSensorInfoVOList(mapIndexService.findSensorInfo(siteId));
            customizeVOList = mapIndexService.findCustomizeVOList(siteId);
            ActionMessage.createByAction().consume();
        } catch (Exception e) {
            log.error("定制页面异常");
        }
        logger.log("站点地图", "定制页面");
        return Results.ftl("/biela/pages/index/layout.ftl");
    }

    @Route("/biela/findZoneLocationBySiteSensorId.json")
    public String findZoneLocationBySiteSensorId() {
        try {
            zoneLocationPOList = mapIndexService.findZoneLocationBySiteIdSensorId(siteId, sensorId);
        } catch (Exception e) {
            log.error("获取设备信息失败", e);
        }
        return Results.json().root("zoneLocationPOList").done();
    }

    @Route("/biela/verifyCustomize/{siteId}/{locationId}/{sensorId}")
    public String verifyCustomize() {
        Integer num = mapIndexService.verifyCustomize(siteId, locationId, sensorId);
        if (num > 0) {
            flag = true;
        }
        return Results.json().root("flag").done();
    }

    @Route("/biela/customizeCount/{siteId}")
    public String customizeCount() {
        Integer num = mapIndexService.customizeCount(siteId);
        if (num >= 3) {
            flag = true;
        }
        return Results.json().root("flag").done();
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public LogicGroupPO getLogicGroupPO() {
        return logicGroupPO;
    }

    public void setLogicGroupPO(LogicGroupPO logicGroupPO) {
        this.logicGroupPO = logicGroupPO;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public List<ZoneLocationPO> getZoneLocationPOList() {
        return zoneLocationPOList;
    }

    public void setZoneLocationPOList(List<ZoneLocationPO> zoneLocationPOList) {
        this.zoneLocationPOList = zoneLocationPOList;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<CustomizeVO> getCustomizeVOList() {
        return customizeVOList;
    }

    public void setCustomizeVOList(List<CustomizeVO> customizeVOList) {
        this.customizeVOList = customizeVOList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
