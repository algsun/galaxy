package com.microwise.blueplanet.proxy.impl;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liuzhu
 * @date 13-11-25
 */

@Component
@Scope("prototype")
@Blueplanet
public class ZoneManagerProxyImpl implements ZoneManagerProxy {

    //区域service
    @Autowired
    private ZoneService zoneService;

    @Override
    public void changeParent(String zoneId, String parentZoneId) {
        zoneService.changeParent(zoneId,parentZoneId);
    }

    @Override
    public boolean containsName(String siteId, String parentZoneId, String zoneName) {
        return zoneService.containsName(siteId, parentZoneId, zoneName);
    }

    @Override
    public void deleteZone(String zoneId) {
        zoneService.deleteZone(zoneId);
    }

    @Override
    public List<String> findChildrenIdList(String zoneId) {
        return zoneService.findChildrenIdList(zoneId);
    }

    @Override
    public long findDataVersion(String zoneId) {
        return zoneService.findDataVersion(zoneId);
    }

    @Override
    public List<SensorinfoVO> findSensorinfo(String zoneId) {
        return zoneService.findSensorinfo(zoneId);
    }

    @Override
    public ZoneVO findZoneById(String zoneId) {
        return zoneService.findZoneById(zoneId);
    }

    @Override
    public List<ZoneVO> findZones(String zoneId) {
        return zoneService.findZones(zoneId);
    }

    @Override
    public List<ZoneVO> findZoneList(String siteId, String parentZoneId) {
        return zoneService.findZoneList(siteId,parentZoneId);
    }

    @Override
    public boolean isEmpty(String zoneId) {
        return zoneService.isEmpty(zoneId);
    }

    @Override
    public boolean isNameAvailable(String siteId, String parentZoneId, String zoneId, String zoneName) {
        return zoneService.isNameAvailable(siteId, parentZoneId, zoneId, zoneName);
    }

    @Override
    public String saveZone(ZoneVO zone) {
        return zoneService.saveZone(zone);
    }

    @Override
    public void updateZone(String zoneId, String zoneName, String planImage,int position) {
        zoneService.updateZone(zoneId, zoneName, planImage,position);
    }
}
