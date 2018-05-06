package com.microwise.blueplanet.action.location.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.action.app.VerifyCodeAction;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiedeng
 * @date 14-6-24
 */

@Beans.Action
@Blueplanet
public class DeleteLocationAction extends BlueplanetLoggerAction {

    public static final Logger logger = LoggerFactory.getLogger(DeleteLocationAction.class);

    @Autowired
    private LocationService locationService;

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Autowired
    private ControlCenterCommunicationService controlCenterCommunicationService;

    //验证码名称
    public static final String VERIFY_CODE_NAME = "deleteLocation";

    private String locationId;

    /**
     * 验证码
     */
    private String verifyCode;


    /**
     * 删除位置点
     */
    // TODO params page 是否有用
    @Route(value = "/blueplanet/deleteLocation", params = {"locationId", "verifyCode"})
    public String delete() {
        boolean codeFlag = true;
        boolean deleteFlag = true;
        Map<String, Object> jsonData = new HashMap<String, Object>();
        ActionContext actionContext = ActionContext.getContext();
        if (VerifyCodeAction.hasVerifyCode(actionContext, VERIFY_CODE_NAME)) {
            String realVerifyCode = VerifyCodeAction.getVerifyCode(actionContext, VERIFY_CODE_NAME);
            if (!realVerifyCode.equalsIgnoreCase(verifyCode)) {
                codeFlag = false;
            } else {
                try {
                    LocationVO location = locationService.findLocationById(locationId);
                    locationService.deleteLocation(locationId);
                    locationService.dropTable(locationId);
                    appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
                    appCacheHolder.putLocation(location);
                    /*if (StringUtils.isNotBlank(location.getNodeId())) {
                        String siteId = Sessions.createByAction().currentSiteId();
                        controlCenterCommunicationService.uploadZone(siteId, null, location, false);
                    }*/
                    locationService.notifyLocationChanged(location.getNodeId());
                    log("位置点管理", "删除位置点");
                } catch (Exception e) {
                    deleteFlag = false;
                    logger.error("删除位置点错误", e);
                }
            }
        }
        jsonData.put("codeSuccess", codeFlag);
        jsonData.put("deleteSuccess", deleteFlag);
        ActionContext.getContext().put("data", jsonData);
        return Results.json().root("data").done();
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

}
