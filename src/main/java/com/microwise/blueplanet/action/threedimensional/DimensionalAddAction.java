package com.microwise.blueplanet.action.threedimensional;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ThreeDimensionalService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import com.microwise.common.util.UpLoadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


/**
 * @author 王耕
 * @date 15-6-12
 */
@Beans.Action
@Blueplanet
public class DimensionalAddAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(DimensionalAddAction.class);

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日HH时mm分ss秒";

    @Autowired
    private ThreeDimensionalService threeDimensionalService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ZoneService zoneService;

    /**
     * 内容页面
     */
    private static final String _pagePath = "add-dimensional.ftl";

    /**
     * 模型描述
     */
    private String remark;

    /**
     * 隐藏域传递文件名
     */
    private String hideFileName;

    /**
     * 文档上传
     */
    private File srcUploadFile;

    /**
     * 模型绑定的位置点
     */
    private String locationIds = "";

    /**
     * 站点下所有的位置点
     */
    private Map<String, List<LocationVO>> locationMap;


    @Route("/blueplanet/three-dimensional/toAddDimensional")
    public String view() {
        try {
            ActionMessage.createByAction().consume();
            String siteId = Sessions.createByAction().currentSiteId();
            List<LocationVO> locations = locationService.findLocationsBySiteIdAndLocationName(siteId, null);
            locationMap = dealWithLocations(locations);
        } catch (Exception e) {
            log.error("添加模型界面失败！",e);
        }
        return Results.ftl("/blueplanet/pages/threedimensional/layout");
    }

    @Route("/blueplanet/three-dimensional/validateThreeDimensionalPath")
    public String validateThreeDimensionalPath() {
        String fileName = new File(hideFileName).getName();
        ThreeDimensionalPO threeDimensionalPO = threeDimensionalService.findThreeDimenByPath(fileName);
        if (threeDimensionalPO != null) {
            return Results.json().asRoot(false).done();
        }
        return Results.json().asRoot(true).done();
    }

    @Route("/blueplanet/three-dimensional/uploadFile")
    public String uploadFile() {
        String siteId = Sessions.createByAction().currentSiteId();
        String fileName = new File(hideFileName).getName();
        String path = UpLoadFileUtil.getUploadPath( File.separator +"blueplanet") + File.separator + "file"+ File.separator +"threedimensional";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String newPath = path + File.separator + fileName;
        File destUploadFile = new File(newPath);
        String isUpload = fileUpload(srcUploadFile, destUploadFile);
        if ("outOfLength".equals(isUpload)) {
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.threeDimensional.modeSize"));
            return "redirect:/blueplanet/three-dimensional";
        }
        if ("true".equals(isUpload)) {
            try {
                int dimensionalId = addFileToDB(fileName, siteId, remark);
                    threeDimensionalService.saveDimensionalLocationRelation(siteId, dimensionalId, getLocationIdList());
                ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.threeDimensional.addSuccess"));
                log("3d模型管理", "添加模型，模型名称： " + fileName);
            } catch (Exception e) {
                ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.threeDimensional.addFail"));
                log.error("模型添加失败,模型添加失败", e);
            }
        }
        return "redirect:/blueplanet/three-dimensional";
    }

    /**
     * 对数据元素做去空格运算
     * @return ids 位置点设备
     */
    private String[] getLocationIdList() {
        String[] ids = locationIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            ids[i] = ids[i].trim();
        }
        return ids;
    }

    /**
     * 文件上传
     *
     * @param srcFile
     * @param destFile
     * @return outOfLength 模型大小超过200M
     * true 上传成功
     * false 上传失败
     */
    private String fileUpload(File srcFile, File destFile) {
        if (srcFile.length() > 200000000) {
            return "outOfLength";
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            ByteStreams.copy(fis, fos);
            return "true";
        }catch (Exception e) {
            log.error("模型上传失败",e);
            return "false";
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("字节流关闭异常",e);
            }
        }
    }

    /**
     * 添加文档信息到数据库
     * @param threeDimensionalPath 模型文件名称
     * @return id 新插入的模型的id
     */
    private int addFileToDB(String threeDimensionalPath, String siteId, String remark) {
        ThreeDimensionalPO threeDimensionalPO = new ThreeDimensionalPO();
        threeDimensionalPO.setPath(threeDimensionalPath);
        threeDimensionalPO.setSiteId(siteId);
        threeDimensionalPO.setUploadtime(new Date());
        threeDimensionalPO.setRemark(remark);
        try {
            threeDimensionalService.saveDimensional(threeDimensionalPO);
        } catch (Exception e) {
            log.error("模型保存失败", e);
        }
        return threeDimensionalPO.getId();
    }

    /**
     * 按区域将位置点分为多个list
     *
     * @param locations 所有的位置点列表
     * @return 处理后的位置点Map
     */
    private Map<String, List<LocationVO>> dealWithLocations(List<LocationVO> locations) {
        Map<String, List<LocationVO>> map = new HashMap<String, List<LocationVO>>();
        for (LocationVO location : locations) {
            if (Strings.isNullOrEmpty(location.getZoneId())) {
                continue;
            }
            String zoneName = zoneService.findZoneById(location.getZoneId()).getZoneName();
            if (map.containsKey(zoneName)) {
                List<LocationVO> locationList = map.get(zoneName);
                locationList.add(location);
            } else {
                List<LocationVO> list = new ArrayList<LocationVO>();
                list.add(location);
                map.put(zoneName, list);
            }
        }
        return map;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHideFileName() {
        return hideFileName;
    }

    public void setHideFileName(String hideFileName) {
        this.hideFileName = hideFileName;
    }

    public File getSrcUploadFile() {
        return srcUploadFile;
    }

    public void setSrcUploadFile(File srcUploadFile) {
        this.srcUploadFile = srcUploadFile;
    }

    public Map<String, List<LocationVO>> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(Map<String, List<LocationVO>> locationMap) {
        this.locationMap = locationMap;
    }

    public String getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(String locationIds) {
        this.locationIds = locationIds;
    }
}
