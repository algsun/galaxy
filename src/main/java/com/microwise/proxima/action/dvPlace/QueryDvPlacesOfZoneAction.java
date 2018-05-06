package com.microwise.proxima.action.dvPlace;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.PlanImageCoordinateVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.BeanMaps;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.service.DVPlaceService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 查询一个区域下的摄像机点位
 *
 * @author gaohui
 * @date 13-8-26 16:07
 */
@Beans.Action
@Proxima
public class QueryDvPlacesOfZoneAction {

    private static final Logger log = LoggerFactory.getLogger(QueryDvPlacesOfZoneAction.class);

    @Autowired
    private DVPlaceService dvPlaceService;

    @Autowired
    private ZoneService zoneService;
    //input

    // 区域ID
    private String zoneId;

    @Route("/proxima/zone/{zoneId}/dv-places.json")
    public String dvPlaces() {
        try {
            List<DVPlaceBean> dvPlaces = dvPlaceService.findDvPlacesByZoneId(zoneId);
            for (DVPlaceBean dvPlace : dvPlaces) {
                PlanImageCoordinateVO planImageCoordinate = zoneService.findPlanImageCoordinate(dvPlace.getZone().getId(), dvPlace.getId());
                if (planImageCoordinate != null) {
                    dvPlace.setCoordinateX(planImageCoordinate.getCoordinateX());
                    dvPlace.setCoordinateY(planImageCoordinate.getCoordinateY());
                }
            }
            List<Map<String, Object>> dvPlacesMap = BeanMaps.toMap(dvPlaces, new String[]{
                    "id",
                    "placeCode",
                    "placeName",
                    "enable",
                    "realmap",
                    "coordinateX",
                    "coordinateY"
            });

            ActionContext.getContext().put("data", dvPlacesMap);
        } catch (Exception e) {
            log.error("查詢區域攝像機列表", e);
        }
        return Results.json().root("data").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
