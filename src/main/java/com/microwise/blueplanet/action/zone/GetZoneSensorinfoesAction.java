package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 获取区域监测指标
 *
 * @author Wang yunlong
 * @date 13-1-18 上午9:37
 * @check
 */
@Beans.Action
@Blueplanet
public class GetZoneSensorinfoesAction {
    @Autowired
    private ZoneService zoneService;
    //input
    /**
     * 区域id
     */
    private String zoneId;

    //output
    /**
     * 监测指标集合
     */
    private List<SensorinfoVO> sensorinfoes;

    @Route("/blueplanet/zone/{zoneId}/sensorinfoes.json")
    public String queryZoneSensorinfoes() {
        sensorinfoes = zoneService.findSensorinfo(zoneId);
        return Results.json().root("sensorinfoes").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<SensorinfoVO> getSensorinfoes() {
        return sensorinfoes;
    }

    public void setSensorinfoes(List<SensorinfoVO> sensorinfoes) {
        this.sensorinfoes = sensorinfoes;
    }
}
