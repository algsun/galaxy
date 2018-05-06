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
 * 编辑监测指标action
 *
 * @author liuzhu
 * @date 14-1-11
 * @check @wang.geng 2014-1-17 #7690
 */
public class UpdateCustomizeAction {

    private static final Logger log = LoggerFactory.getLogger(UpdateCustomizeAction.class);

    /**
     * 站点地图service
     */
    @Autowired
    private MapIndexService mapIndexService;

    /**
     * 比拉日志操作类
     */
    @Autowired
    private BielaLoggerAction logger;

    /**
     * 定制id
     */
    private Integer customizeId;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 监测指标id
     */
    private Integer sensorId;

    /**
     * 定制备注
     */
    private String customizeRemark;

    @Route("/biela/updateCustomize")
    public String updateCustomizeById() {
        try {
            mapIndexService.deleteCustomizeById(customizeId);
            mapIndexService.saveCustomize(siteId, deviceId, sensorId, customizeRemark);
            ActionMessage.createByAction().success("编辑成功");
        } catch (Exception e) {
            log.error("编辑定制失败", e);
            ActionMessage.createByAction().fail("编辑失败");
        }
        logger.log("监测指标定制", "更新定制");
        return Results.redirect("/biela/customize/" + siteId);
    }

    public Integer getCustomizeId() {
        return customizeId;
    }

    public void setCustomizeId(Integer customizeId) {
        this.customizeId = customizeId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCustomizeRemark() {
        return customizeRemark;
    }

    public void setCustomizeRemark(String customizeRemark) {
        this.customizeRemark = customizeRemark;
    }
}
