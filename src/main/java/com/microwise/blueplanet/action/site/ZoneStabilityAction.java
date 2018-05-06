package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZoneStability;
import com.microwise.phoenix.service.ZoneStabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 区域稳定性对比
 *
 * @author: xuyuexi
 * @date: 14-10-24
 *
 * @check wang.geng xu.yuexi #9740 2014-10-30
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ZoneStabilityAction extends BlueplanetLoggerAction {



    public static final Logger log = LoggerFactory.getLogger(ZoneStabilityAction.class);


    /**
     * 区域稳定性Service
     */
    @Autowired
    private ZoneStabilityService zoneStabilityService;

    /**
     * 监测指标 默认值：湿度
     */
    private int sensorId;

    /**
     * 日期类型 默认值：日
     */
    private int dateType;

    // 获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route(value = "summarize/zoneStability.json", params = {"dateType", "sensorId"})
    public String view() {
        List<ZoneStability> zoneStabilityList = null;
        try{
            zoneStabilityList = zoneStabilityService.findZoneStability(siteId, sensorId, new Date(), dateType);
            log("站点总览","区域稳定性");
        }catch (Exception e){
            log.error("站点总览，稳定性",e);
        }
        return Results.json().asRoot(zoneStabilityList).done();
    }


    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

}
