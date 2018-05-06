package com.microwise.blueplanet.action.location.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.proxy.RelicProxy;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 14-6-24
 */

@Beans.Action
@Blueplanet
public class EditLocationAction extends BlueplanetLoggerAction {

    public static final Logger logger = LoggerFactory.getLogger(EditLocationAction.class);

    public static String _pagePath = "/blueplanet/pages/location/manage/edit-location.ftl";

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Autowired
    private ControlCenterCommunicationService controlCenterCommunicationService;

    @Autowired
    private RelicProxy relicProxy;


    /**
     * 未绑定设备列表
     */
    private List<DeviceVO> unbindDeviceList;


    /**
     * 设备的类型集合
     */
    private List<Integer> deviceTypes;

    /**
     * 设备编号
     */
    private String nodeId;

    /**
     * 位置点编号
     */
    private String locationId;

    /**
     * 区域编号
     */
    private String zoneId;

    /**
     * 位置点名称
     */
    private String locationName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 经度
     */
    private Double lng;
    /**
     * 维度
     */
    private Double lat;

    /**
     * 页码
     */
    private int page;

    /**
     * 文件
     */
    private File uploadFile;

    /**
     * 解绑的nodeId
     */
    private String unbindNodeId;

    /**
     * 上传的文件名
     */
    private String uploadFilename;

    /**
     * 位置点文物列表
     */
    private LocationVO location;

    /**
     * 位置点绑定文物集合
     */
    private List<Relic> relics;

    /**
     * m_location_relic id
     */
    private int id;

    /**
     * 绑定文物ID
     */
    private String relic;

    /**
     * 检索文物名称
     */
    private String relicNameTotalCode;

    /**
     * select2 检索分页
     */
    private int pageIndex;

    /**
     * select2 检索分页
     */
    private int pageSize;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 是否开通了资产管理
     */
    private boolean orion;

    /**
     *
     */
    private String excludeId;

    @Route(value = "/blueplanet/editLocation")
    public String editLocation() {
        LocationVO location = new LocationVO();
        location.setId(locationId);
        if (StringUtils.isNotBlank(locationName)) {
            location.setLocationName(locationName);
        }
        if (StringUtils.isNotBlank(nodeId)) {
            location.setNodeId(nodeId);
        }
        boolean isLocation = true;
        String zoneId = this.zoneId;
        if (nodeId.trim().equals("unbind")) {
            location.setNodeId(null);
            isLocation = false;
            zoneId = null;
        }
        location.setRemark(remark);
        location.setLng(lng);
        location.setLat(lat);
        String siteId = Sessions.createByAction().currentSiteId();
        if (!locationService.isExistLocationName(locationId, locationName, siteId)) {
            try {
                if (!Strings.isNullOrEmpty(relic)) {
                    relic = relic.replace(" ","");
                    String[] relicIds = relic.split(",");
                    for (String relicId : relicIds) {
                        locationService.addLocationRelic(Integer.parseInt(relicId.trim()), location.getId());
                    }
                    //执行检测指标融合
                    relics = locationService.findRelics(locationId);
                    if(relics.size()>0){
                        String[] thresholdRelicIds = new String[relics.size()];
                        int i = 0;
                        for (Relic relic : relics) {
                            thresholdRelicIds[i] = relic.getId().toString();
                            i++;
                        }
                        locationService.editTextureThreshold(locationId, thresholdRelicIds);
                    } else {
                        //执行监测指标融合，参数为null
                        locationService.editTextureThreshold(locationId, null);
                    }
                }
                List<String> deviceIds = locationService.updateLocation(location);
                appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
                appCacheHolder.putLocation(location);
                if (nodeId.trim().equals("unbind")) {
                    location.setNodeId(unbindNodeId);
                }
                /*controlCenterCommunicationService.uploadZone(siteId, zoneId, location, isLocation);*/
                for (String deviceId : deviceIds) {
                    locationService.notifyLocationChanged(deviceId);
                }
            } catch (Exception e) {
                logger.error("编辑位置点信息失败", e);
            }
        }
        return Results.redirect("/blueplanet/queryLocations?query&page=" + page);
    }


    @Route(value = "/blueplanet/isExistLocationNameExpectSelf")
    public String isExistLocationNameExpectSelf() {
        String siteId = Sessions.createByAction().currentSiteId();
        boolean success = locationService.isExistLocationName(locationId, locationName, siteId);
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("success", success);
        return Results.json().asRoot(msg).done();
    }

    @Route(value = "/blueplanet/toEditLocation")
    public String toEditLocation() {
        try {
            location = locationService.findLocationById(locationId);
            filePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "blueplanet" + File.separator + "images" + File.separator + "location" + File.separator;
            if (Strings.isNullOrEmpty(location.getPhoto())) {
                filePath = "../" + File.separator + "blueplanet" + File.separator + "images" + File.separator;
                location.setPhoto("no-relic.jpg");
            }
            unbindDeviceList = locationService.findUnbindDevices(Sessions.createByAction().currentSiteId());
            if (StringUtils.isNotBlank(nodeId)) {
                DeviceVO device = deviceService.findDeviceById(nodeId);
                unbindDeviceList.add(device);
            } else {
                nodeId = "";
            }

            Map<String, Object> session = ActionContext.getContext().getSession();
            List<Subsystem> subsystems = (List<Subsystem>) session.get("subsystemList");

            for (Subsystem subsystem : subsystems) {
                if (subsystem.getSubsystemCode().equals("orion")) {
                    orion = subsystem.isEnable();
                    break;
                }
            }
            // 查询位置点绑定文物
            relics = locationService.findRelics(locationId);
            deviceTypes = locationService.getDeviceTypes(unbindDeviceList);
        } catch (Exception e) {
            logger.error("编辑位置点失败", e);
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout");
    }

    @Route("/blueplanet/getRelicsEditLocation.json")
    public String getRelicList() {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Integer> excludeIds = new ArrayList<Integer>();
        try     {
            relics = locationService.findRelics(locationId);
            for (Relic r : relics) {
                excludeIds.add(r.getId());
            }
            relicNameTotalCode = new String(relicNameTotalCode.getBytes("ISO-8859-1"), "UTF-8");
            json.put("total_count", relicProxy.findRelicsCount(relicNameTotalCode, excludeIds));
            json.put("relics", relicProxy.findRelics(relicNameTotalCode, pageIndex, pageSize, excludeIds));
        } catch (UnsupportedEncodingException e) {
            logger.error("查询文物失败");
        }
        return Results.json().includeProperties("total_count,relics\\[\\d+\\]\\.id,relics\\[\\d+\\]\\.name,relics\\[\\d+\\]\\.totalCode,relics\\[\\d+\\]\\.photos.*,relics\\[\\d+\\]\\.siteId,relics\\[\\d+\\]\\.era.*").asRoot(json).done();
    }

    @Route("/blueplanet/deleteLocationRelic")
    public String deleteLocationRelic() {
        try {
            locationService.deleteLocationRelic(id);
            //执行检测指标融合
            relics = locationService.findRelics(locationId);
            if(relics.size()>0){
                String[] thresholdRelicIds = new String[relics.size()];
                int i = 0;
                for (Relic relic : relics) {
                    thresholdRelicIds[i] = relic.getId().toString();
                    i++;
                }
                locationService.editTextureThreshold(locationId, thresholdRelicIds);
            } else {
                //执行监测指标融合，参数为null
                locationService.editTextureThreshold(locationId, null);
            }
            return Results.json().asRoot(true).done();
        } catch (Exception e) {
            return Results.json().asRoot(false).done();
        }
    }

    @Route(value = "/blueplanet/uploadLocationDataFile")
    public String uploadLocationDataFile() {
        String saveFilePath = ResourcePaths.galaxyResourcesDir("blueplanet" + File.separator + "locationDataFiles" + File.separator);
        FileOutputStream fos = null;
        FileInputStream fis = null;
        boolean isSuccess = true;
        try {
            File file = new File(saveFilePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            fos = new FileOutputStream(saveFilePath + File.separator + uploadFilename);
            fis = new FileInputStream(uploadFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            locationService.addUploadRecord(uploadFilename);
            locationService.notifyAnalysisDataFile(uploadFilename);
        } catch (Exception e) {
            logger.error("文件上传失败", e);
            isSuccess = false;
        } finally {
            close(fos, fis);
        }
        return Results.json().asRoot(isSuccess).done();
    }

    private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                logger.error("FileInputStream关闭失败", e);
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                logger.error("FileOutputStream关闭失败", e);
            }
        }
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<DeviceVO> getUnbindDeviceList() {
        return unbindDeviceList;
    }

    public void setUnbindDeviceList(List<DeviceVO> unbindDeviceList) {
        this.unbindDeviceList = unbindDeviceList;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Integer> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(List<Integer> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getUploadFilename() {
        return uploadFilename;
    }

    public void setUploadFilename(String uploadFilename) {
        this.uploadFilename = uploadFilename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public String getUnbindNodeId() {
        return unbindNodeId;
    }

    public void setUnbindNodeId(String unbindNodeId) {
        this.unbindNodeId = unbindNodeId;
    }

    public List<Relic> getRelics() {
        return relics;
    }

    public void setRelics(List<Relic> relics) {
        this.relics = relics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelic() {
        return relic;
    }

    public void setRelic(String relic) {
        this.relic = relic;
    }

    public String getRelicNameTotalCode() {
        return relicNameTotalCode;
    }

    public void setRelicNameTotalCode(String relicNameTotalCode) {
        this.relicNameTotalCode = relicNameTotalCode;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isOrion() {
        return orion;
    }

    public void setOrion(boolean orion) {
        this.orion = orion;
    }

    public String getExcludeId() {
        return excludeId;
    }

    public void setExcludeId(String excludeId) {
        this.excludeId = excludeId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
