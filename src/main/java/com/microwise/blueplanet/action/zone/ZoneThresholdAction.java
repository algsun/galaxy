package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.bean.vo.ZoneThresholdVO;
import com.microwise.blueplanet.service.ThresholdService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域阈值设置
 *
 * @author liuzhu
 * @date 13-8-26
 * @check @wang.geng 2013年9月2日 #5345
 */
@Beans.Action
@Blueplanet
public class ZoneThresholdAction {

    public static final Logger log = LoggerFactory.getLogger(ZoneThresholdAction.class);

    private final String _pagePath = "threshold.ftl";

    public static String URL = "";

    static {
        URL = ConfigFactory.getInstance().getConfig("config.properties").get("blueplanet.daemon.api.url");
    } 
    @Autowired
    private ThresholdService thresholdService;
        /**
     * 区域id
     */
    private String zoneId;
    
    /**
     * 区域阈值
     */
    private List<ZoneThresholdVO> zoneThresholdVOs;
    /**
     * 监测阈值
     */
    private Map<String, ZoneThresholdVO> maps = new HashMap<String, ZoneThresholdVO>();

    /**
     * 添加阈值是否成功
     */
    private boolean flag;

    /**
     * 是否设置了阈值
     */
    private boolean hasThreshold = false;
    

    /**
     * 某一个区域下的监测指标
     */
    private List<SensorinfoVO> sensorinfoVOList;
    
    /**
     * 报警类型
     */
    private List<Integer> alarmTypes;

    /**
     * 短信
     */
    private String sms = "false";

    /**
     * 加入我的任务
     */
    private String task = "false";

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 区域阈值
     *
     * @author liuzhu
     * @date 2013-08-26
     * @author chenyaofei
     * @data 2016-07-13
     */
    @Route("/blueplanet/zone/{zoneId}/threshold/view")
    public String view() {
        zoneThresholdVOs = thresholdService.findZoneThresholds(zoneId);
        parentId = thresholdService.findParentIdByZoneId(zoneId);
        sensorinfoVOList = thresholdService.findThresholdSensorinfo(zoneId);
        return Results.ftl("/blueplanet/pages/zone/layout.ftl");
    }

    @Route("/blueplanet/zone/deleteAllThreshold/{zoneId}")
    public String deleteZoneThreshold() {
        thresholdService.deleteZoneThreshold(zoneId);
        return Results.redirect("../" + zoneId + "/threshold/view");
    }

    @Route("/blueplanet/zone/findZoneThreshold/{zoneId}")
    public String findZoneThreshold() {
        zoneThresholdVOs = thresholdService.findZoneThresholds(zoneId);
        if (zoneThresholdVOs.size() > 0) {
            hasThreshold = true;
        }
        return Results.json().root("hasThreshold").done();
    }


    /**
     * 保存区域阈值
     *
     * @author liuzhu
     * @date 2013-08-26
     * @author chenyaofei
     * @data 2016-07-13
     */
    @Route("/blueplanet/saveThreshold")
    public String saveThreshold() {
        try {
            List<ZoneThresholdVO> listValue = new ArrayList();
            for (String s : maps.keySet()) {
                String key = s;
                listValue.add(maps.get(key));
            }
            thresholdService.saveZoneThreshold(zoneId, listValue);
            flag = true;

            String url = URL + "thresholdCacheServlet?zoneId=" + zoneId;
            HttpGet get = new HttpGet(url);
            request(get);
        } catch (Exception e) {
            flag = false;
            log.error("区域阈值", e);
        }
        return Action.SUCCESS;
    }

    private JSONObject request(HttpGet get) throws IOException,
            JSONException {
        InputStream input = null;
        try {
            HttpResponse response = new DefaultHttpClient().execute(get);
            input = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(response
                    .getEntity().getContent(), "utf-8");
            String resultText = CharStreams.toString(reader);
            return new JSONObject(resultText);
        } finally {
            Closeables.close(input, true);
        }
    }


    public String get_pagePath() {
        return _pagePath;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<SensorinfoVO> getSensorinfoVOList() {
        return sensorinfoVOList;
    }

    public void setSensorinfoVOList(List<SensorinfoVO> sensorinfoVOList) {
        this.sensorinfoVOList = sensorinfoVOList;
    }

    public List<ZoneThresholdVO> getZoneThresholdVOs() {
        return zoneThresholdVOs;
    }

    public void setZoneThresholdVOs(List<ZoneThresholdVO> zoneThresholdVOs) {
        this.zoneThresholdVOs = zoneThresholdVOs;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Map<String, ZoneThresholdVO> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, ZoneThresholdVO> maps) {
        this.maps = maps;
    }

    public List<Integer> getAlarmTypes() {
        return alarmTypes;
    }

    public void setAlarmTypes(List<Integer> alarmTypes) {
        this.alarmTypes = alarmTypes;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isHasThreshold() {
        return hasThreshold;
    }

    public void setHasThreshold(boolean hasThreshold) {
        this.hasThreshold = hasThreshold;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
