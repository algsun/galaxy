package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;

import java.util.Map;

/**
 * @author gaohui
 * @date 14-3-14 17:19
 */
@Beans.Action
@Blueplanet
public class SetDefaultParentAction {

    //input
    private String deviceId;
    private int parentId;

    @Route("/blueplanet/device/{deviceId}/default-parent")
    public String setDefaultParent(){
        try{
            BPHttpApiClient client = new BPHttpApiClient();
            Map<String, Object> result = client.setDefaultParent(deviceId, parentId);
            if((Boolean) result.get("success")){
               if((Boolean)result.get("sendSuccess")){
                   if((Boolean) result.get("doSuccess")){
                       ActionMessage.createByAction().success("命令执行成功");
                   }else{
                       ActionMessage.createByAction().success("命令发送成功");
                   }
               }else{
                   ActionMessage.createByAction().fail("命令发送失败");
               }
            }else{
                ActionMessage.createByAction().fail("错误");
            }
        }catch (Exception e){
            ActionMessage.createByAction().fail("发送命令失败");
        }

        return Results.redirect(String.format("/blueplanet/device/%s/detail/edit", deviceId));
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
