package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.OpticsDVPlacePO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.service.OpticsDVPlaceService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 摄像机设备与传感设备查询
 *
 * @author wanggeng
 * @date 13-9-29 下午2:27
 * @check @li.jianfei #5842 2013-10-10
 */
@Beans.Action
@Halley
public class QueryDeviceAction {

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 光学摄像机service服务层
     */
    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;

    /**
     * 传感设备service服务层
     */
    @Autowired
    private LocationService locationService;
    @Autowired
    private CarService carService;
    //input
    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 查询位置点的位置点名称
     */
    private String locationName;

    //output
    /**
     * 摄像机对象列表
     */
    private List<OpticsDVPlacePO> dvPlaces;

    /**
     * 位置点列表
     */
    private List<LocationVO> locations;

    /**
     * 查询摄像机设备
     *
     * @return string
     */
    @Route("/halley/queryDVPlaces.json")
    public String queryDvDevice() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();

            //zoneId为空，查询所有摄像机设备，不为空查询该区域下的摄像机设备
            if (Strings.isNullOrEmpty(zoneId)) {
                dvPlaces = opticsDVPlaceService.findAllOpticsDV(siteId);
            } else {
                dvPlaces = opticsDVPlaceService.findOpticsDVByZoneId(zoneId, siteId);
            }

            halleyLogger.log("外展配置", "摄像机查询");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("摄像机设备查询出错");
            halleyLogger.logFailed("外展配置", "摄像机查询");
            e.printStackTrace();
        }
        return Results.json().root("dvPlaces").done();
    }

    /**
     * 位置点查询
     *
     * @return string
     */
    @Route(value = "/halley/queryNodes.json")
    public String queryNodes() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();

            //查询位置点
            locations = locationService.findLocationsBySiteIdAndLocationName(siteId, locationName);

            List<LocationVO> locations = new ArrayList<LocationVO>();
            //车辆信息列表查询
            List<CarVO> cars = carService.findCarsWithDeviceByExhibitionId(exhibitionId);
            for (CarVO car : cars) {
                locations.addAll(car.getLocationVOs());
            }
            for (LocationVO location : locations) {
                Iterator<LocationVO> locationIterator = this.locations.iterator();
                while (locationIterator.hasNext()) {
                    LocationVO locationVO = locationIterator.next();
                    if (location.getId().equals(locationVO.getId())) {
                        locationIterator.remove();
                    }
                }
            }
            halleyLogger.log("外展配置", "传感设备查询");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("传感设备查询出错");
            halleyLogger.logFailed("外展配置", "传感设备查询");
            e.printStackTrace();
        }
        return Results.json().root("locations").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<OpticsDVPlacePO> getDvPlaces() {
        return dvPlaces;
    }

    public void setDvPlaces(List<OpticsDVPlacePO> dvPlaces) {
        this.dvPlaces = dvPlaces;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<LocationVO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationVO> locations) {
        this.locations = locations;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }
}
