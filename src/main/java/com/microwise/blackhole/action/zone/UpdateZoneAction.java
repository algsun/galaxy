package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 获得区域信息
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-1-18 上午9:56
 * @check @gaohui 2013-02-25 #1694
 */
@Beans.Action
@Blackhole
public class UpdateZoneAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(UpdateZoneAction.class);

    /**
     * 准备确定修改的实景图标志
     */
    public static final String PLAN_IMAGE_PATH_PRE = "p";

    @Autowired
    private ZoneManagerProxy zoneManagerProxy;
    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    private ControlCenterCommunicationService controlCenterCommunicationService;

    //input
    /**
     * 修改区域的父区域id
     */
    private String parentZoneId;
    /**
     * 区域id
     */
    private String zoneId;
    /**
     * 区域名称
     */
    private String zoneName;
    /**
     * 图片名称
     */
    private String image;
    /**
     * 区域位置
     */
    private int position;

    //output
    /**
     * 修改区域后返回页面区域对象
     */
    private ZoneVO zone;

    /**
     * 环境监控资源路径
     */
    private String resourcesPath;

    @Route("/blackhole/zone/update.json")
    public String execute() {
        String siteId = ManagerIndexAction.getSiteId();
        Map<String, Object> data = new HashMap<String, Object>();
        resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";
        if (zoneManagerProxy.isNameAvailable(siteId, Strings.emptyToNull(parentZoneId), zoneId, zoneName)) {
            try {
                String realPath = ResourcePaths.galaxyResourcesDir("blueplanet/images/zonePlanImage");

                File oldImage = new File(new File(realPath), image);
                File newImage = new File(new File(realPath), PLAN_IMAGE_PATH_PRE + image);
                if (newImage.exists()) {
                    FileUtils.copyFile(newImage, oldImage);
                    newImage.delete();
                }
                zoneManagerProxy.updateZone(zoneId, zoneName, image, position);
                //清除缓存
                appCacheHolder.evictZoneDeviceTree(siteId);
                appCacheHolder.evictZone(zoneId);
                zone = zoneManagerProxy.findZoneById(zoneId);
                log("区域管理", "更新区域,id为:" + zoneId);
                data.put("success", true);
                data.put("resourcesPath", resourcesPath);
                data.put("zone", zone);

                //上传区域信息
                /*controlCenterCommunicationService.uploadZone(siteId, zoneId);*/
            } catch (Exception e) {
                data.put("success", false);
                data.put("msg", "未知错误");
                log.error("更新区域", e);
            }
        } else {
            data.put("success", false);
            data.put("msg", "该名称已存在");
        }

        ActionContext.getContext().put("data", data);
        return Results.json().root("data").ignoreHierarchy(false).done();
    }


    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ZoneVO getZone() {
        return zone;
    }

    public void setZone(ZoneVO zone) {
        this.zone = zone;
    }

    public String getParentZoneId() {
        return parentZoneId;
    }

    public void setParentZoneId(String parentZoneId) {
        this.parentZoneId = parentZoneId;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
