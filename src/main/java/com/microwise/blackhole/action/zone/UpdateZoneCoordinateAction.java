package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blueplanet.bean.vo.PlanImageCoordinateVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-4-12 下午5:11
 */
@Beans.Action
@Blackhole
public class UpdateZoneCoordinateAction {
    public static final Logger log = LoggerFactory.getLogger(UpdateZoneCoordinateAction.class);
    @Autowired
    private ZoneService zoneService;
    /**
     * 当前区域id
     */
    private String parentZoneId;

    /**
     * 区域id
     */
    private String zoneId;

    /**
     * x轴
     */
    private float x;
    /**
     * y轴
     */
    private float y;

    @Route("/blueplanet/zone/{zoneId}/updateZoneCoordinate.json")
    public String execute() {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            PlanImageCoordinateVO planImageCoordinate = new PlanImageCoordinateVO();
            planImageCoordinate.setZoneId(parentZoneId);
            planImageCoordinate.setObjectId(zoneId);
            planImageCoordinate.setCoordinateX(x);
            planImageCoordinate.setCoordinateY(y);
            planImageCoordinate.setType(PlanImageCoordinateVO.ZONE_TYPE);
            zoneService.updatePlanImageCoordinate(planImageCoordinate);
            data.put("success", true);
        } catch (Exception e) {
            log.error("更新区域坐标信息", e);
            data.put("success", false);
        }
        ActionContext.getContext().put("data", data);
        return Results.json().root("data").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getParentZoneId() {
        return parentZoneId;
    }

    public void setParentZoneId(String parentZoneId) {
        this.parentZoneId = parentZoneId;
    }
}
