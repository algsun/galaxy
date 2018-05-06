package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 获取站点实时数据, 带监测指标过滤
 *
 * @author Wang yunlong
 * @date 13-1-23 下午4:01
 * @check @gaohui 2013-01-29 #1297
 * @check @gaohui 2013-02-25 #1400
 */
@Beans.Action
@Blueplanet
public class GetFilterRealTimeDataAction extends BlueplanetLoggerAction {
    @Autowired
    private SiteService siteService;

    //input
    /**
     * 过滤监测指标标示
     */
    private Integer[] sensorPhysicalIds;

    //output
    /**
     * 站点实时数据
     */
    private List<RealtimeDataVO> data;

    @Route(value = "/blueplanet/site/realtime-data.json", params = {"sensorPhysicalIds"})
    public String executeFilter() {
        String siteId = SiteRealTimeDataAction.getSiteId();
        data = siteService.findRealtimeDataLocation(siteId,Arrays.asList(sensorPhysicalIds));
        log("环境监控", "实时数据");
        return Results.json().root("data").done();
    }

    public Integer[] getSensorPhysicalIds() {
        return sensorPhysicalIds;
    }

    public void setSensorPhysicalIds(Integer[] sensorPhysicalIds) {
        this.sensorPhysicalIds = sensorPhysicalIds;
    }

    public List<RealtimeDataVO> getData() {
        return data;
    }

    public void setData(List<RealtimeDataVO> data) {
        this.data = data;
    }
}
