package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * // 区域湿度最大值、最小值、平均值(当前时间前一天)
 * @author : chenyaofei
 * @date : 2016-08-24
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ZoneHumidityAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(ZoneHumidityAction.class);
    /**
     *区域service
     */
    @Autowired
    private ZoneService zoneService;

    /**
     *站点service
     */
    @Autowired
    private SiteService siteService;

    @Route(value = "summarize/zoneHumidity.json")
    public String view() {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            //获取站点下所有区域
            List<ZoneVO> zoneVOList = zoneService.findZoneLineList(siteId);
            Map<String, AvgdataPO> zoneHumidityData = siteService.findZoneHumidity(zoneVOList);
            data.put("zoneVOList", zoneVOList);
            data.put("zoneHumidityData", zoneHumidityData);
            log("环境监控", "站点总览湿度波动范围");
        } catch (Exception e) {
            log.error("站点总览湿度波动范围", e);
        }
        return Results.json().asRoot(data).done();
    }
}
