package com.microwise.blueplanet.util;

import com.microwise.blueplanet.bean.po.DeviceFormulaPO;
import com.microwise.blueplanet.bean.vo.ProductStateVO;
import com.microwise.blueplanet.bean.vo.ProductVO;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.HttpApiClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 环境监控 http api
 *
 * @author xuyuexi
 * @date 14-2-25 14:02
 */
public class BPHttpApiClient extends HttpApiClient {
    public static final Logger log = LoggerFactory.getLogger(BPHttpApiClient.class);

    private HttpClient httpClient = new DefaultHttpClient();
    public static String URL = "";

    public static String onlineUrl = "";

    static {
        ConfigFactory.Configs config = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        URL = config.get("blueplanet.daemon.api.url");
        onlineUrl = config.get("galaxy_online.api.url");

    }

    public Map<String, Boolean> turnSwitch(String deviceId, int route, boolean onOrOff) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/turnSwitch?route=" + route + "&onOrOff=" + onOrOff);
            JSONObject result = requestAndParse(get);
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Double> getOriginValue(String deviceId, int sensorId, double target) {
        try {
            HttpGet get = new HttpGet(URL + "struts/condition-refl/origin?deviceId=" + deviceId + "&sensorId=" + sensorId + "&target=" + target);
            JSONObject result = requestAndParse(get);
            Map<String, Double> map = new HashMap<String, Double>();
            map.put("origin", result.getDouble("origin"));
            map.put("originLeft", result.getDouble("originLeft"));
            map.put("originRight", result.getDouble("originRight"));
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Double> getOriginValue(int sensorId, double target) {
        try {
            HttpGet get = new HttpGet(URL + "struts/condition-refl/origin?sensorId=" + sensorId + "&target=" + target);
            JSONObject result = requestAndParse(get);
            Map<String, Double> map = new HashMap<String, Double>();
            map.put("origin", result.getDouble("origin"));
            map.put("originLeft", result.getDouble("originLeft"));
            map.put("originRight", result.getDouble("originRight"));
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Boolean> configConditionRefl(String deviceId, int route, String subNodeId, int sensorId, int low, int high, int switchAction) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/condition-refl?route=" + route + "&subNodeId=" + subNodeId
                    + "&sensorId=" + sensorId + "&low=" + low + "&high=" + high + "&switchAction=" + switchAction);
            JSONObject result = requestAndParse(get);
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加动作或者删除
     *
     * @param deviceId
     * @param switchActionId
     * @return
     */
    public Boolean actionChanged(String deviceId, int type, String switchActionId) {
        try {
            HttpPut put = new HttpPut(URL + "struts/devices/" + deviceId + "/switch-action-time?type=" + type + "&switchActionId=" + switchActionId);
            JSONObject result = requestAndParse(put);
            return result.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询可选父节点
     *
     * @param deviceId
     * @return
     */
    public Map<String, Object> availableParents(String deviceId) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/available-parents");
            JSONObject result = requestAndParse(get);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设备默认父节点
     *
     * @param deviceId
     * @param parentId
     * @return
     */
    public Map<String, Object> setDefaultParent(String deviceId, int parentId) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/default-parent/" + parentId);
            JSONObject result = requestAndParse(get);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 设置节点RF不休眠
     *
     * @param deviceId
     * @param enable
     * @return
     */
    public Map<String, Object> setRFAlive(String deviceId, boolean enable) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/rf-alive?enable=" + enable);
            JSONObject result = requestAndParse(get);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 清除设备自定义公式系数
     *
     * @param deviceId
     * @return
     */
    public boolean evictCustomFormulaCache(String deviceId) {
        try {
            HttpGet get = new HttpGet(URL + "struts/caches/device-custom-formula?_method=delete&deviceId=" + deviceId);
            JSONObject result = requestAndParse(get);
            return result.getBoolean("success");
        } catch (Exception e) {
            log.error("", e);
        }

        return false;
    }

    /**
     * 修改设备工作周期
     *
     * @param deviceId
     * @param interval
     * @return
     */
    public Map<String, Object> modifyInterval(String deviceId, int interval) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/modify_interval?deviceId=" + deviceId + "&interval=" + interval);
            JSONObject result = requestAndParse(get);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean setHumidityController(String deviceId, int targetHumidity, int highHumidity, int lowHumidity) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/setHumidityController?deviceId="
                    + deviceId + "&targetHumidity=" + targetHumidity + "&highHumidity=" + highHumidity +
                    "&lowHumidity=" + lowHumidity);
            JSONObject result = requestAndParse(get);
            boolean success = result.getBoolean("success");
            boolean sendSuccess = result.getBoolean("sendSuccess");
            boolean doSuccess = result.getBoolean("doSuccess");
            if (success && sendSuccess) {
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean setAirConditionerTemperature(String deviceId, int targetTemperature) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/setAirConditionerTemperature?&targetTemperature=" + targetTemperature);
            JSONObject result = requestAndParse(get);
            boolean success = result.getBoolean("success");
            boolean sendSuccess = result.getBoolean("sendSuccess");
            if (success && sendSuccess) {
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean setAirConditionerHumidity(String deviceId, int targetHumidity) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/setAirConditionerHumidity?deviceId="
                    + deviceId + "&targetHumidity=" + targetHumidity);
            JSONObject result = requestAndParse(get);
            boolean success = result.getBoolean("success");
            boolean sendSuccess = result.getBoolean("sendSuccess");
            if (success && sendSuccess) {
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean setHumCompensate(String deviceId, int humCompensate) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/setHumCompensate/" + humCompensate);
            JSONObject result = requestAndParse(get);
            isSuccess = result.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 设置标定模式
     *
     * @param deviceId
     * @param inOrOut
     * @return
     */
    public boolean setDemarcate(String deviceId, boolean inOrOut) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/demarcate?inOrOut=" + inOrOut);
            JSONObject result = requestAndParse(get);
            isSuccess = result.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 切换屏幕开关状态
     *
     * @param deviceId
     * @param onOrOff
     * @return
     */
    public boolean switchScreen(String deviceId, boolean onOrOff) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/switchScreen?onOrOff=" + onOrOff);
            JSONObject result = requestAndParse(get);
            isSuccess = result.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 设置空调开关状态
     *
     * @param deviceId
     * @param switchState
     * @return
     */
    public boolean setAirConditionerSwitchState(String deviceId, int switchState) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/setAirConditionerSwitchState?switchState=" + switchState);
            JSONObject result = requestAndParse(get);
            boolean success = result.getBoolean("success");
            boolean sendSuccess = result.getBoolean("sendSuccess");
            if (success && sendSuccess) {
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 木卫一关闭蜂鸣器
     */
    public boolean buzzerSwitch(String deviceId) {
        boolean isSuccess = false;
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/buzzerSwitch?deviceId=" + deviceId);
            JSONObject result = requestAndParse(get);
            isSuccess = result.getBoolean("sendSuccess");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 修改设备工作周期
     *
     * @param deviceId
     * @param sensorId
     * @param minValue
     * @param maxValue
     * @return
     */
    public Map<String, Object> setSensorThreshold(String deviceId, int sensorId, int maxValue, int minValue) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpGet get = new HttpGet(URL + "struts/device/" + deviceId + "/sensor-threshold?sensorId=" + sensorId + "&high=" + maxValue + "&low=" + minValue);
            try {
                JSONObject result = requestAndParse(get);
                map.put("success", result.getBoolean("success"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 通知中间件位置点操作
     *
     * @param deviceId 设备编号
     * @return
     */
    public Map<String, Object> notifyLocationChanged(String deviceId) throws Exception {
        HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/reload-device-cache");
        JSONObject result = requestAndParse(get);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", result.getBoolean("success"));
        return map;
    }

    /**
     * 通知中间件木卫一上传文件
     *
     * @param locationId 位置点编号
     * @return
     */
    public Map<String, Object> notifyAnalysisDataFile(String locationId) throws Exception {
        HttpGet get = new HttpGet(URL + "struts/analysisDataFile?locationId=" + locationId);
        JSONObject result = requestAndParse(get);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", result.getBoolean("success"));
        return map;
    }

    /**
     * 开启全网巡检
     *
     * @param siteId
     * @param interval
     * @return
     */
    public Map<String, Object> openPolling(String siteId, int interval) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/openPolling?siteId=" + siteId + "&interval=" + interval);
            JSONObject result = requestAndParse(get);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 关闭全网巡检
     *
     * @param siteId
     * @return
     */
    public Map<String, Object> closePolling(String siteId) {
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/closePolling?siteId=" + siteId);
            JSONObject result = requestAndParse(get);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 上传设备位置信息
     *
     * @param serialNumber 序列号
     * @param location     区域名称编号
     * @return
     */
    public Map<String, Object> uploadNodeLocation(String serialNumber, List<String> location) {
        //todo 删除无用代码
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpEntity<List<String>> entity = new HttpEntity<List<String>>(location);
            new RestTemplate().put(onlineUrl + "products/" + serialNumber + "/location", entity);
            return map;
        } catch (Exception e) {
            log.error("上传位置信息发生异常", e);
        }
        return map;
    }

    public Map<String, Object> locate(String deviceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpGet get = new HttpGet(URL + "struts/device/" + deviceId + "/locate");
            JSONObject result = requestAndParse(get);
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 设备重启
     *
     * @param deviceId
     * @return
     */
    public Map<String, Object> reboot(String deviceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpGet get = new HttpGet(URL + "struts/devices/" + deviceId + "/restart");
            JSONObject result = requestAndParse(get);
            map.put("success", result.getBoolean("success"));
            map.put("sendSuccess", result.getBoolean("sendSuccess"));
            map.put("doSuccess", result.getBoolean("doSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 上传状态
     *
     * @param productStateVOs 设备状态
     * @return
     */
    public boolean uploadState(List<ProductStateVO> productStateVOs) {
        try {
            HttpEntity<List<ProductStateVO>> entity = new HttpEntity<List<ProductStateVO>>(productStateVOs);
            new RestTemplate().put(onlineUrl + "products/state", entity);
        } catch (Exception e) {
            log.error("上传状态失败", e);
            return false;
        }
        return true;
    }

    public boolean uploadStateNOSn(List<ProductVO> productVOs) {
        try {
            HttpEntity<List<ProductVO>> entity = new HttpEntity<List<ProductVO>>(productVOs);
            new RestTemplate().put(onlineUrl + "products", entity);
        } catch (Exception e) {
            log.error("上传状态失败", e);
            return false;
        }
        return true;
    }

    /**
     * 获取云端已发生改变的公式
     *
     * @param siteId 站点
     * @return DeviceFormualaPO 数组
     */
    public DeviceFormulaPO[] getOnlineFormulasUpdateToLocalTable(String siteId) {
        try {
            String url = onlineUrl + "products/formulas?siteId=" + siteId;
            ResponseEntity<DeviceFormulaPO[]> responseEntity = new RestTemplate().getForEntity(url, DeviceFormulaPO[].class);
            return responseEntity.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设备修改公式之后上传
     *
     * @param sn              产品序列号
     * @param deviceFormulaPO 设备公式po
     */
    public void uploadDeviceFormula(String sn, DeviceFormulaPO deviceFormulaPO) {
        try {
            HttpEntity<DeviceFormulaPO> entity = new HttpEntity<DeviceFormulaPO>(deviceFormulaPO);
            new RestTemplate().put(onlineUrl + "products/" + sn + "/formula", entity);
        } catch (Exception e) {
            log.error("上传公式至云端失败", e);
        }
    }

    /**
     * 清除设备监测指标浮动值
     *
     * @param deviceId
     * @return
     */
    public boolean evictFloatSensorCache(String deviceId) {
        try {
            HttpGet get = new HttpGet(URL + "struts/caches/evictFloatSensor?_method=delete&deviceId=" + deviceId);
            JSONObject result = requestAndParse(get);
            return result.getBoolean("success");
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

    /**
     * 清除指定设备
     *
     * @param deviceId
     * @return
     */
    public boolean evictDiviceCacheById(String deviceId) {
        try {
            HttpGet get = new HttpGet(URL + "struts/caches/evictDeviceById?_method=delete&deviceId=" + deviceId);
            JSONObject result = requestAndParse(get);
            return result.getBoolean("success");
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

    @Override
    public HttpClient getHttpClient() {
        return httpClient;
    }
}
