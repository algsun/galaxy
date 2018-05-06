package com.microwise.uma.action.realtime;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.ZoneBean;
import com.microwise.uma.service.ZoneService;
import com.microwise.uma.sys.Uma;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平面图
 *
 * @author li.jianfei
 * @date 2013-6-18
 * TODO 该功能暂未实现
 */
@Beans.Action
@Uma
public class PlanViewAction {

    @Autowired
    private ZoneService zoneService;

    /**
     * 区域ID
     */
    private String zoneId;

    private int queryType;

    private ZoneBean zone;

    private String resourcesPath;

    /**
     * 平面图
     *
     * @return
     */
    public String execute() {
        try {
            zoneId = "ca6b87fc-76c1-4db7-ace7-9bbe60a542b5";

            resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";
            zone = zoneService.findZoneById(zoneId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public ZoneBean getZone() {
        return zone;
    }

    public void setZone(ZoneBean zone) {
        this.zone = zone;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }
}

