package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ContentBase;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.action.app.VerifyCodeAction;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备列表
 *
 * @author li.jianfei
 * @date 2014-07-29
 */
@Beans.Action
@Blueplanet
@ContentBase("/blueplanet/pages/device/manage")
public class QueryDeviceAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(QueryDeviceAction.class);
    public static final String _pagePath = "list-device.ftl";

    @Autowired
    private DeviceService deviceService;
    private static String VERIFY_CODE_NAME="patrol";

    //input
    /**
     * 设备ID, 后 5 位
     */
    private String deviceId;

    /**
     * 设备类型
     */
    private Integer deviceType = -1;

    //output
    /**
     * 设备列表
     */
    List<DeviceVO> devices;
    /**
     * 当前页
     */
    private int page;
    /**
     * 总页数
     */
    private int pageSum;

    /**
     *
     */
    private String pollingFlag;
    /**
     * 巡检验证码
     * */
    private String verifyCode;
    /**
     * 当前站点 id
     */
    private String siteId;

    /**
     * 站点下设备查询
     */
    @Route(value = "/blueplanet/devices")
    public String defaultView() {
        page = 1;
        queryData(page);
        log("设备管理", "查询设备列表");
        return Results.ftl("device-manage-layout");
    }

    /**
     * 带分页的查询
     */
    @Route(value = "/blueplanet/devices", params = "page")
    public String pagingView() {
        queryData(page);
        log("设备管理", "查询设备列表");
        return Results.ftl("device-manage-layout");
    }

    /**
     * 开启、关闭全网巡检
     */
    @Route(value = "/blueplanet/{pollingFlag}.json",params = "verifyCode")
    public String pollingOpenOrClose() {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        String siteId = Sessions.createByAction().currentSiteId();
        boolean flagVerifyCode=verifyCode();
        if(!flagVerifyCode){
            jsonData.put("flagVerifyCode",false);
            ActionContext.getContext().put("data", jsonData);
            return Results.json().root("data").done();
        }
        try {
            Map<String, Object> result;
            BPHttpApiClient client = new BPHttpApiClient();
            if ("pollingOpen".endsWith(pollingFlag)) {
                result = client.openPolling(siteId, 60);
            } else {
                result = client.closePolling(siteId);
            }
            if ((Boolean) result.get("success") && (Boolean) result.get("sendSuccess")) {
                    jsonData.put("success", true);
            }else{
                    jsonData.put("success", false);
            }
        } catch (Exception e) {
            jsonData.put("success", false);
            log.error("巡检失败", e);
        }
        jsonData.put("flagVerifyCode",true);
        ActionContext.getContext().put("data", jsonData);
        return Results.json().root("data").done();
    }


    /**
     * 查询设备
     *
     * @param page
     */
    private void queryData(int page) {
        siteId = Sessions.createByAction().currentSiteId();
        devices = deviceService.findDeviceList(siteId, deviceId, deviceType, page, Constants.SIZE_PER_PAGE);
        int devicesCount = deviceService.findDeviceListCount(siteId, deviceId, deviceType);
        pageSum = PagingUtil.pagesCount(devicesCount, Constants.SIZE_PER_PAGE);
    }

    /**
     * 验证码
     * */
    private boolean verifyCode(){
        ActionContext actionContext = ActionContext.getContext();
        if (VerifyCodeAction.hasVerifyCode(actionContext, VERIFY_CODE_NAME)) {
            String realVerifyCode = VerifyCodeAction.getVerifyCode(actionContext, VERIFY_CODE_NAME);
            System.out.println(verifyCode);
            System.out.println(realVerifyCode);
            if (realVerifyCode.equalsIgnoreCase(verifyCode)) {
                return true;
            }
        }
        return false;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<DeviceVO> getDevices() {
        return devices;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getPollingFlag() {
        return pollingFlag;
    }

    public void setPollingFlag(String pollingFlag) {
        this.pollingFlag = pollingFlag;
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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
