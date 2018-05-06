package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 获取站点监测指标
 *
 * @author Wang yunlong
 * @time 13-1-23 下午4:02
 * @check @gaohui 2013-01-29 #1297
 * @check @gaohui 2013-02-25 #1316
 * @deprecated
 */
@Beans.Action
@Blueplanet
public class GetSiteSensorinfoesAction {
    @Autowired
    private SiteService siteSevice;
    @Autowired
    private AppCacheHolder appCacheHolder;


    //output
    /**
     * 站点监测指标
     */
    private List<SensorinfoVO> sensorinfoes;

    //@Route("/blueplanet/site/sensorinfoes.json")
    public String querySensorinfoes() {
        String siteId = SiteRealTimeDataAction.getSiteId();
        try {
            sensorinfoes = appCacheHolder.loadAvailableSensorinfoOfSite(siteId);
        } catch (ExecutionException e) {
            sensorinfoes = siteSevice.findSensorinfo(siteId);
        }
        return Results.json().root("sensorinfoes").done();
    }

    public List<SensorinfoVO> getSensorinfoes() {
        return sensorinfoes;
    }

    public void setSensorinfoes(List<SensorinfoVO> sensorinfoes) {
        this.sensorinfoes = sensorinfoes;
    }
}
