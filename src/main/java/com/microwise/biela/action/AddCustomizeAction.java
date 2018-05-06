package com.microwise.biela.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.biela.service.MapIndexService;
import com.microwise.biela.sys.BielaLoggerAction;
import com.microwise.common.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 添加监测指标定制action
 *
 * @author liuzhu
 * @date 14-1-11
 * @check @wang.geng #7690 2014-1-17
 */
public class AddCustomizeAction {

    private static final Logger log = LoggerFactory.getLogger(AddCustomizeAction.class);

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
     * 站点组id
     */
    private String siteId;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 监测指标id
     */
    private Integer sensorId;

    /**
     * 定制备注
     */
    private String customizeRemark;

    @Route("/biela/saveCustomize")
    public String saveCustomize() {
        try {
            mapIndexService.saveCustomize(siteId, locationId, sensorId, customizeRemark);
            ActionMessage.createByAction().success("添加成功");
        } catch (Exception e) {
            log.error("添加定制失败", e);
            ActionMessage.createByAction().fail("添加失败");
        }
        logger.log("监测指标定制", "添加定制");
        return Results.redirect("/biela/customize/" + siteId);
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getCustomizeRemark() {
        return customizeRemark;
    }

    public void setCustomizeRemark(String customizeRemark) {
        this.customizeRemark = customizeRemark;
    }
}
