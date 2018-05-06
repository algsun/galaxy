package com.microwise.blueplanet.service.impl;

import com.google.common.collect.Lists;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.dao.DeviceDao;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.dao.SiteDao;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.sys.annotation.Beans;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 15-1-23
 */
@Beans.Service
@Transactional
@Blueplanet
public class ControlCenterCommunicationServiceImpl implements ControlCenterCommunicationService {

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private ZoneDao zoneDao;

    @Autowired
    private LocationDao locationDao;

    @Override
    public void uploadZone(String siteId, String zoneId) {
        Map<LocationVO, String> nodeZoneMap = new HashMap<LocationVO, String>();
        findAllLocation(zoneId, nodeZoneMap);
        for (LocationVO location : nodeZoneMap.keySet()) {
            String zId = nodeZoneMap.get(location);
            //uploadZone(siteId, zId, location, true);
        }
    }

    /**
     * 查询区域所有设备
     *
     * @param zoneId      区域编号
     * @param nodeZoneMap 设备区域编号集合
     */
    private void findAllLocation(String zoneId, Map<LocationVO, String> nodeZoneMap) {
        List<String> zoneIds = zoneDao.findChildrenIdList(zoneId);
        for (String id : zoneIds) {
            List<LocationVO> locations = locationDao.findLocationsByZoneId(id);
            if (locations != null && !locations.isEmpty()) {
                for (LocationVO location : locations) {
                    nodeZoneMap.put(location, id);
                }
            }
        }
    }

    //todo 优化
    @Override
    public void uploadZone(String siteId, String zoneId, LocationVO location, boolean isLocation) {
        if (StringUtils.isBlank(location.getNodeId())) {
            return;
        }
        String siteName = getSiteName(siteId);
        DeviceVO device = deviceDao.findDeviceById(location.getNodeId());
        String sn = device.getSn();
        if (StringUtils.isBlank(siteName) || StringUtils.isBlank(sn) || sn.equals("0")) {
            return;
        }
        List<String> locations = new ArrayList<String>();
        locations.add(siteName);
        List<String> zones = new ArrayList<String>();
        if (StringUtils.isNotBlank(zoneId)) {
            getZoneNameList(zoneId, zones);
            zones = Lists.reverse(zones);
            locations.addAll(zones);
        }
        if (isLocation) {
            locations.add(getLocationName(location));
        }
        BPHttpApiClient client = new BPHttpApiClient();
        client.uploadNodeLocation(sn, locations);
    }

    /**
     * 获取位置点名称
     *
     * @param location
     * @return
     */
    private String getLocationName(LocationVO location) {
        if (StringUtils.isBlank(location.getLocationName())) {
            return location.getNodeId();
        } else {
            return location.getLocationName();
        }
    }

    /**
     * 查询所有位置点名称
     *
     * @param zoneId 区域名称
     * @param zones  区域列表
     */
    private void getZoneNameList(String zoneId, List<String> zones) {
        ZoneVO zoneVO = zoneDao.findZoneById(zoneId);
        zones.add(zoneVO.getZoneName());
        if (StringUtils.isNotBlank(zoneVO.getParentId())) {
            getZoneNameList(zoneVO.getParentId(), zones);
        }
    }

    /**
     * 根据站点编号查询站点名称
     *
     * @param siteId 站点编号
     * @return 站点名称
     */
    private String getSiteName(String siteId) {
        SiteVO site = siteDao.findSiteById(siteId);
        return site.getSiteName();
    }
}
