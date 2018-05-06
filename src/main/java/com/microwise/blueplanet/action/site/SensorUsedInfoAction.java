package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SensorUsedDataVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 站点总览-监测指标覆盖率统计
 *
 * @author chen.yaofei
 * @date 2016-08-25
 */

@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class SensorUsedInfoAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(SensorUsedInfoAction.class);

    /**
     * 监测指标覆盖率数据结果集
     */
    private List<SensorUsedDataVO> sensorUsedInfoList;


    /**
     * 站点service
     */
    @Autowired
    private SiteService siteService;

    @Route(value = "summarize/sensorUsedInfo.json")
    public String view() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            //语言国际化
            String language = LocaleBundleTools.appLocale();
            sensorUsedInfoList = siteService.findSensorUsedInfo(siteId,language);
            log("环境监控", "站点总览监测指标覆盖率");
        } catch (Exception e) {
            log.error("站点总览监测指标覆盖率", e);
        }
        return Results.json().asRoot(sensorUsedInfoList).done();
    }

    public List<SensorUsedDataVO> getSensorUsedInfoList() {
        return sensorUsedInfoList;
    }

    public void setSensorUsedInfoList(List<SensorUsedDataVO> sensorUsedInfoList) {
        this.sensorUsedInfoList = sensorUsedInfoList;
    }
}
