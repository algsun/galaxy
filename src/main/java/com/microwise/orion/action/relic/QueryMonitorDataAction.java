package com.microwise.orion.action.relic;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.cong on 2017/8/3.
 */
@Beans.Action
@Orion
public class QueryMonitorDataAction {

    @Autowired
    private RelicService relicService;
    @Autowired
    private LocationService locationService;

    private Integer relicId;

    private Relic relic;

    /**
     * 获得siteId 当前站点编号
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    {
        //初始化图片路径
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "orion" + File.separator + "images";
    }

    /**
     * 项目请求路径
     */
    private String locationBasePath;

    {
        HttpServletRequest request = ServletActionContext.getRequest();
        locationBasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }

    @Route(value = "orion/initialize")
    public String initialize() {
        //获取项目的请求路径
        relic = relicService.findRelicByRelicId(relicId, siteId);
        return Results.ftl("orion/pages/relic/view-monitor-data.ftl");
    }

    @Route(value = "orion/getMonitorData")
    public String getMonitorData() {
        List<String> locationsId = relicService.findLocationsIdByRelicId(relicId);
        Map<String, RealtimeDataVO> monitorData = new HashMap<String, RealtimeDataVO>();
        RealtimeDataVO realtimeDataVO = null;
        for (String locationId : locationsId) {
            //获取位置点下所有传感器Id
            List<SensorinfoVO> sensorInfoVOS = locationService.findSensorInfoList(locationId);
            List<Integer> sensorPhysicalidList = new ArrayList<>();
            for (SensorinfoVO sensorinfoVO : sensorInfoVOS) {
                sensorPhysicalidList.add(sensorinfoVO.getSensorPhysicalid());
            }
            //获取位置点实时数据
            List<LocationDataVO> locationDataVOS = locationService.findLocationSensor(locationId, sensorPhysicalidList);
            Map<Integer, LocationDataVO> locationSensorInfoMap = new HashMap<>();
            for (LocationDataVO locationDataVO : locationDataVOS) {
                locationSensorInfoMap.put(locationDataVO.getSensorPhysicalid(), locationDataVO);
            }
            realtimeDataVO = locationService.findLocationData(locationId);
            realtimeDataVO.setLocationSensorInfoMap(locationSensorInfoMap);
            monitorData.put(realtimeDataVO.getLocationId(), realtimeDataVO);
        }
        return Results.json().asRoot(monitorData).done();
    }

    public Integer getRelicId() {
        return relicId;
    }

    public void setRelicId(Integer relicId) {
        this.relicId = relicId;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public RelicService getRelicService() {
        return relicService;
    }

    public void setRelicService(RelicService relicService) {
        this.relicService = relicService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getLocationBasePath() {
        return locationBasePath;
    }

    public void setLocationBasePath(String locationBasePath) {
        this.locationBasePath = locationBasePath;
    }
}
