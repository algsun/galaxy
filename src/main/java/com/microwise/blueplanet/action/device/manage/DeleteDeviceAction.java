package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.microwise.blackhole.action.app.VerifyCodeAction;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 删除设备
 *
 * @author liuzhu
 * @date 2013-10-16
 */
@Beans.Action
@Blueplanet
public class DeleteDeviceAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(DeleteDeviceAction.class);

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AppCacheHolder appCacheHolder;

    public static String URL = "";
    public static String onlineUrl = "";

    static {
        URL = ConfigFactory.getInstance().getConfig("config.properties").get("blueplanet.daemon.api.url");
//        onlineUrl = ConfigFactory.getInstance().getConfig("config.properties").get("galaxy_online.api.url");
    }

    //验证码名称
    public static final String VERIFY_CODE_NAME = "deleteDevice";

    //input
    /**
     * 设备id集合
     */
    private String deviceIds;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 删除设备
     *
     * @author lizhu
     * @date 2013-10-18
     */
    @Route(value = "/blueplanet/device/deleteDevice", params = {"deviceIds", "verifyCode"})
    public String deleteNodeById() {
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
                    String [] ids =  deviceIds.split(",");
                    for (String nodeId : ids) {
                        //删除节点
                        deleteFlag = deleteDevice(nodeId);
                    }
                } catch (Exception e) {
                    deleteFlag = false;
                    log.error("删除设备", e);
                }
            }
        }
        jsonData.put("codeSuccess", codeFlag);
        jsonData.put("deleteSuccess", deleteFlag);
        ActionContext.getContext().put("data", jsonData);
        return Results.json().root("data").done();
    }

    private boolean deleteDevice(String deviceId){
        try{
            HttpGet get = new HttpGet(URL + "struts/deleteDevice?deviceId=" + deviceId);
            JSONObject jsonObject = request(get);
            if (jsonObject.getBoolean("success")) {
//                DeviceVO device = deviceService.findDeviceById(deviceId);
//                String sn = device.getSn();
//                if (!Strings.isNullOrEmpty(sn) && !"0".equals(sn)) {
//                    RestTemplate template = new RestTemplate();
//                    try {
//                        template.put(onlineUrl + "/products/" + sn + "/state", null);
//                    } catch (RestClientException e) {
////                        log.error("删除云端设备", e);
//                        // TODO 删除GALAXY-ONLINE 设备失败不影响银河业务
//                    }
//                }

                deviceService.deleteDevice(deviceId);
                // 清除设备树缓存
                appCacheHolder.evictDevice(deviceId);
                appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
                log("设备管理", "删除设备");
            } else {
                return false;
            }
        }catch (Exception e){
            log.error("删除设备", e);
            return false;
        }
        return true;
    }
    private JSONObject request(HttpGet get) throws IOException,
            JSONException {
        InputStream input = null;
        try {
            HttpResponse response = new DefaultHttpClient().execute(get);
            input = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(response
                    .getEntity().getContent(), "utf-8");
            return new JSONObject(CharStreams.toString(reader));
        } finally {
            Closeables.close(input, true);
        }
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }
}
