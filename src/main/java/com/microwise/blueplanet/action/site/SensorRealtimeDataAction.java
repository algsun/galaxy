package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点总览监测指标实时数据
 *
 * @author 王耕
 * @date 2014-10-29
 * @check xu.yuexi wang.geng #9775 2014-10-30
 */
@Blueplanet
@Beans.Action
public class SensorRealtimeDataAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(SensorRealtimeDataAction.class);

    @Autowired
    private SiteService siteService;

    private Map<String, List<Object>> chartMap = new HashMap<String, List<Object>>();

    /**
     * 监测指标ID
     */
    private int sensorId;

    @Route(value = "/blueplanet/site/sensorRealtimeData.json")
    public String tempRealtimeData() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            Map<String, List<LocationDataVO>> dataMap = siteService.findSiteRealTimeData(siteId);
            List<Object> xAxis = new ArrayList<Object>();
            List<Object> series = new ArrayList<Object>();
            if (dataMap.size() > 0) {
                for (String zoneName : dataMap.keySet()) {
                    for (LocationDataVO locationDataVO : dataMap.get(zoneName)) {
                        if (locationDataVO != null) {
                            if (locationDataVO.getSensorPhysicalid() == sensorId) {
                                xAxis.add(zoneName);
                                series.add(Double.parseDouble(locationDataVO.getSensorPhysicalValue()));
                            }
                        }
                    }
                }
            }
            chartMap.put("xdata", xAxis);
            chartMap.put("ydata", series);
            log("站点总览", "监测指标实时数据");
        } catch (Exception e) {
            log.error("监测指标实时数据", e);
        }
        return Results.json().asRoot(chartMap).done();
    }

    public Map<String, List<Object>> getChartMap() {
        return chartMap;
    }

    public void setChartMap(Map<String, List<Object>> chartMap) {
        this.chartMap = chartMap;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
}
