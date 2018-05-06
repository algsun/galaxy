package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 获取一个站点下可用的监测指标
 *
 * @author gaohui
 * @date 13-1-22 14:48
 */
@Beans.Action
@Blueplanet
public class SensorinfoOfSiteAction {
    public static final Logger log = LoggerFactory.getLogger(SensorinfoOfSiteAction.class);

    @Autowired
    private AppCacheHolder appCacheHolder;

    private List<SensorinfoVO> sensorinfoes = new ArrayList<SensorinfoVO>();


    @Route(value = "/blueplanet/site/sensorinfoes.json")
    public String sensorinfos() {
        Sessions sessions = Sessions.createByAction();
        LogicGroup logicGroup = sessions.currentLogicGroup();
        Site site = logicGroup.getSite();
        if (site != null) {
            try {
                sensorinfoes = appCacheHolder.loadAvailableSensorinfoOfSite(site.getSiteId());
            } catch (ExecutionException e) {
                log.error("站点下监测指标", e);
            }
        }

        return Results.json().root("sensorinfoes").done();
    }

    public List<SensorinfoVO> getSensorinfoes() {
        return sensorinfoes;
    }
}
