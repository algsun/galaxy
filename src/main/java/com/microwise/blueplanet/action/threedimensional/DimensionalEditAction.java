package com.microwise.blueplanet.action.threedimensional;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DimensionalLocationPO;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ThreeDimensionalService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王耕
 * @date 15-6-12
 */
@Beans.Action
@Blueplanet
public class DimensionalEditAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(DimensionalEditAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "edit-dimensional.ftl";

    @Autowired
    private ThreeDimensionalService threeDimensionalService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ZoneService zoneService;

    /**
     * 被修改的模型文件的ID
     */
    private int dimensionalId;

    /**
     * 描述修改
     */
    private String remark;

    /**
     * 站点下所有的位置点
     */
    private Map<String, List<LocationVO>> locationMap;

    /**
     * 模型实体类
     */
    private ThreeDimensionalPO threeDimensionalPO;

    /**
     * 模型与位置点的关系列表
     */
    private List<DimensionalLocationPO> dimensionalLocations;
    /**
     * 模型绑定的位置点
     */
    private String locationIds;

    @Route("/blueplanet/three-dimensional/toEditDimensional")
    public String view() {
        try{
            String siteId = Sessions.createByAction().currentSiteId();
            threeDimensionalPO = threeDimensionalService.findThreeDimenById(dimensionalId);
            dimensionalLocations = threeDimensionalService.findDimensionalLocationRelations(siteId, dimensionalId);
            List<LocationVO> locations = locationService.findLocationsBySiteIdAndLocationName(siteId, null);
            locationMap = dealWithLocations(locations);
        }catch (Exception e){
                log.error("界面加载失败",e);
        }

        return Results.ftl("/blueplanet/pages/threedimensional/layout");
    }

    @Route("/blueplanet/three-dimensional/editDimensional")
    public String editDimensional() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            threeDimensionalService.updateDimensionalLocationRelation(siteId, dimensionalId, getLocationIdList());
            threeDimensionalService.updateDimensionalRemark(remark, dimensionalId);
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.threeDimensional.updateSuccess"));
        } catch (Exception e) {
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.threeDimensional.updateFail"));
            e.printStackTrace();
        }
        return "redirect:/blueplanet/three-dimensional";
    }

    /**
     * 按区域将位置点分为多个list
     *
     * @param locations 所有的位置点列表
     * @return 处理后的位置点Map
     */
    private Map<String, List<LocationVO>> dealWithLocations(List<LocationVO> locations) {
        Map<String, List<LocationVO>> map = new HashMap<String, List<LocationVO>>();
        for (LocationVO location : locations) {
            if (Strings.isNullOrEmpty(location.getZoneId())) {
                continue;
            }
            String zoneName = zoneService.findZoneById(location.getZoneId()).getZoneName();
            if (map.containsKey(zoneName)) {
                List<LocationVO> locationList = map.get(zoneName);
                locationList.add(location);
            } else {
                List<LocationVO> list = new ArrayList<LocationVO>();
                list.add(location);
                map.put(zoneName, list);
            }
        }
        return map;
    }


    /**
     * 获取位置点设备
     * @return  ids位置点设备
     */
    private String[] getLocationIdList() {
        String[] ids = locationIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            ids[i] = ids[i].trim();
        }
        return ids;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getDimensionalId() {
        return dimensionalId;
    }

    public void setDimensionalId(int dimensionalId) {
        this.dimensionalId = dimensionalId;
    }

    public ThreeDimensionalPO getThreeDimensionalPO() {
        return threeDimensionalPO;
    }

    public void setThreeDimensionalPO(ThreeDimensionalPO threeDimensionalPO) {
        this.threeDimensionalPO = threeDimensionalPO;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, List<LocationVO>> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(Map<String, List<LocationVO>> locationMap) {
        this.locationMap = locationMap;
    }

    public List<DimensionalLocationPO> getDimensionalLocations() {
        return dimensionalLocations;
    }

    public void setDimensionalLocations(List<DimensionalLocationPO> dimensionalLocations) {
        this.dimensionalLocations = dimensionalLocations;
    }

    public String getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(String locationIds) {
        this.locationIds = locationIds;
    }
}
