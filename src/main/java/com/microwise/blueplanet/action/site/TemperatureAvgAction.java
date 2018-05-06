package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Lists;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ZoneAvgDataVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 温度日均值统计
 *
 * @author: xuyuexi
 * @date: 14-10-28
 * @check wang.geng xu.yuexi #9787 2014-10-30
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class TemperatureAvgAction extends BlueplanetLoggerAction {


    public static final Logger log = LoggerFactory.getLogger(TemperatureAvgAction.class);

    @Autowired
    private ZoneService zoneService;


    //input
    /**
     * 监测指标 温度
     */
    private int sensorinfoId = 33;


    @Route(value = "summarize/getAverage.json")
    public String getJSONData() {
        List<ZoneAvgDataVO> zoneAvgDataList = Lists.newArrayList();

        try {
            String siteId = Sessions.createByAction().currentSiteId();
            Date begin = DateTime.now().minusMonths(1).toDate();
            Date end = new Date();
            zoneAvgDataList = zoneService.findZoneAvgData(siteId, sensorinfoId, begin, end);


            log("站点总览", "温度变化趋势图");
        } catch (Exception e) {
            log.error("站点总览温度变化趋势图", e);
            e.printStackTrace();
        }
        return Results.json().asRoot(zoneAvgDataList).done();
    }

    public int getSensorinfoId() {
        return sensorinfoId;
    }

    public void setSensorinfoId(int sensorinfoId) {
        this.sensorinfoId = sensorinfoId;
    }

}
