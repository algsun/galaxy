package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.gson.Gson;
import com.microwise.blueplanet.bean.vo.DeviceStatusPieData;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 站点总览，设备运行状态饼图
 *
 * @author 王耕
 * @date 2014-10-23
 * @check xu.yuexi wang.geng #9744 2014-10-30
 */
@Beans.Action
@Blueplanet
public class DeviceStatusAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(DeviceStatusAction.class);

    @Autowired
    private SiteService siteService;

    //output
    private String deviceStatusInfo;

    @Route(value = "/blueplanet/site/deviceStatusPieData.json")
    public String pieChart() {
        try {
            String siteId = SiteRealTimeDataAction.getSiteId();
            DeviceStatusPieData deviceStatusPieData = siteService.findDeviceStatusPieDataBySiteId(siteId);
            Gson gson = new Gson();
            deviceStatusInfo = gson.toJson(deviceStatusPieData);
            log("环境监控", "站点总览设备运行状态饼图");
        } catch (Exception e) {
            log.error("站点总览设备运行状态饼图", e);
        }
        return Results.json().asRoot(deviceStatusInfo).done();
    }

    public String getDeviceStatusInfo() {
        return deviceStatusInfo;
    }

    public void setDeviceStatusInfo(String deviceStatusInfo) {
        this.deviceStatusInfo = deviceStatusInfo;
    }
}
