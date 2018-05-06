package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.FloatValue;
import com.microwise.blueplanet.service.FloatValueService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存或者修改上下限浮动值、浮动值恢复默认
 *
 * @author chenyaofei
 * @date 16-5-18
 */
@Beans.Action
@Blueplanet
public class SaveOrUpdateFloatValueAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(SaveOrUpdateFloatValueAction.class);

    @Autowired
    FloatValueService floatValueService;
    private FloatValue floatValue;

    @Route("/blueplanet/device/customFloatValue")
    public String saveOrUpdateFloatValue() {
        boolean isSave = false;
        try {
            floatValueService.saveOrUpdate(floatValue);
            BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
            bpHttpApiClient.evictFloatSensorCache(floatValue.getDeviceId());
            isSave = true;
            log("设备管理", "自定义浮动值");
        } catch (Exception e) {
            logger.error("自定义浮动值出错", e);
        }
        return Results.json().asRoot(isSave).done();
    }

    @Route("/blueplanet/device/resetFloatValue")
    public String resetFloatValue() {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            FloatValue resetDefault = floatValueService.resetDefault(floatValue.getDeviceId(), floatValue.getSensorId());
            if(resetDefault == null){
                resetDefault = new FloatValue();
                resetDefault.setMaxUpFloat(0);
                resetDefault.setMinDownFloat(0);
                resetDefault.setMinUpFloat(0);
            }
            BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
            bpHttpApiClient.evictFloatSensorCache(floatValue.getDeviceId());
            data.put("floatValue", resetDefault);
            data.put("isReset", true);
            log("设备管理", "浮动值恢复默认");
        } catch (Exception e) {
            data.put("isReset", false);
            logger.error("浮动值恢复默认出错", e);
        }
        return Results.json().asRoot(data).done();
    }

    public FloatValue getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(FloatValue floatValue) {
        this.floatValue = floatValue;
    }
}
