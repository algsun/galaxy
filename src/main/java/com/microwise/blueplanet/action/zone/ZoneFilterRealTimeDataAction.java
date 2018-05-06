package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 区域实施数据 带监测指标过滤
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-1-18 上午9:41
 * @check @gaohui 2013-02-25 #1400
 */
@Beans.Action
@Blueplanet
public class ZoneFilterRealTimeDataAction {
    @Autowired
    private ZoneService zoneService;

    //input
    /**
     * 区域id
     */
    private String zoneId;
    /**
     * 过滤之后的监测指标
     */
    private Integer[] sensorPhysicalIds;

    //output
    /**
     * 实时数据列表
     */
    private List<Map<String, Object>> data;

    @Route(value = "/blueplanet/zone/{zoneId}/realtime-data.json", params = {"sensorPhysicalIds"})
    public String filterRealtimeData() {
        List<RealtimeDataVO> data = zoneService.findRealtimeDataLocation(zoneId,Arrays.asList(sensorPhysicalIds));
        ActionContext.getContext().put("data", data);
        return Results.json().root("data").done();
    }


    /**
     * 测试用
     * @deprecated @Wang yunlong 2013-1-29
     */
    @Route(value = "/blueplanet/zone/{zoneId}/realtime-data.json", params = {"test", "sensorPhysicalIds"})
    public String execute() {
        data = new ArrayList<Map<String, Object>>();
        System.out.println(sensorPhysicalIds);
        return Results.json().root("data").done();
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Integer[] getSensorPhysicalIds() {
        return sensorPhysicalIds;
    }

    public void setSensorPhysicalIds(Integer[] sensorPhysicalIds) {
        this.sensorPhysicalIds = sensorPhysicalIds;
    }
}
