package com.microwise.proxima.action.dvPlace;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.PlanImageCoordinateVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.sys.Proxima;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaohui
 * @date 13-8-27 10:33
 */
@Beans.Action
@Proxima
public class UpdateDvPlaceCoordinateAction {
    public static final Logger log = LoggerFactory.getLogger(UpdateDvPlaceCoordinateAction.class);

    @Autowired
    private ZoneService zoneService;

    // input
    // 摄像机点位ID
    private String dvPlaceId;
    // 坐标
    private int x;
    private int y;
    //当前区域id
    private String zoneId;
    //output
    // 是否成功
    private boolean success;

    @Route("/proxima/dv-place/{dvPlaceId}/updateCoordinate.json")
    public String update() {
        try {
            PlanImageCoordinateVO planImageCoordinate = new PlanImageCoordinateVO();
            planImageCoordinate.setZoneId(zoneId);
            planImageCoordinate.setObjectId(dvPlaceId);
            planImageCoordinate.setCoordinateX(x);
            planImageCoordinate.setCoordinateY(y);
            planImageCoordinate.setType(PlanImageCoordinateVO.DV_PLACE_TYPE);
            zoneService.updatePlanImageCoordinate(planImageCoordinate);
            success = true;
        } catch (Exception e) {
            log.error("更新坐标", e);
            success = false;
        }

        return Results.json().includeProperties("success").done();
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
