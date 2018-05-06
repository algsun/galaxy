package com.microwise.blueplanet.action.offline;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.action.app.VerifyCodeAction;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.OfflineService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 离线数据
 *
 * @author chenyaofei
 * @date 16-4-20
 */
@Beans.Action
@Blueplanet
public class OfflineAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(OfflineAction.class);

    private static final String _pagePath = "offline.ftl";

    private static final String VERIFY_CODE_NAME = "deleteOfflineLocation";

    /**
     * 分页页码
     */
    private int page = 1;

    /**
     * 分页总页数
     */
    private int pageSum;

    /**
     * 位置点名称查询
     */
    private String locationName;

    /**
     * 区域名称查询
     */
    private String zoneName;
    /**
     * 区域id查询
     */
    private String zoneId;
    /**
     * 位置点id
     */
    private String locationId;

    private List<LocationVO> locationList;
    /**
     * 验证码
     */
    private String verifyCode;
    /**
     * 读取的文件
     */
    private File readFile;
    /**
     * 文件名
     */
    private String readFileFileName;
    @Autowired
    private OfflineService offlineService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ReadExcel readExcel;

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Route("/blueplanet/offline/offline.action")
    public String view() {
        try {
            //获取站点id
            String siteId = Sessions.createByAction().currentSiteId();
            //分页查询批次列表
            locationList = offlineService.findLocationByNameAndZoneId(siteId, locationName, zoneId, page, Constants.SIZE_PER_PAGE);
            pageSum = PagingUtil.pagesCount(offlineService.findAllLocationByNameAndZoneId(siteId, locationName, zoneId), Constants.SIZE_PER_PAGE);
            log("离线数据", "离线位置点数据");
        } catch (Exception e) {
            logger.error("离线数据--离线位置点查看出错", e);
        }
        ActionMessage.createByAction().consume();
        return Results.ftl("/blueplanet/pages/offline/layout.ftl");
    }

    @Route("/blueplanet/offline/delete")
    public String delete() {
        boolean codeFlag = false;
        boolean deleteFlag = true;
        Map<String, Object> data = new HashMap<String, Object>();
        boolean falgverifyCode = verifyCode();
        if (!falgverifyCode) {
            data.put("codeFlag", codeFlag);
        } else {
            try {
                String siteId = Sessions.createByAction().currentSiteId();
                locationService.deleteLocation(locationId);
                locationService.dropTable(locationId);
                appCacheHolder.evictZoneDeviceTree(siteId);
                log("离线数据", "删除位置点");
            } catch (Exception e) {
                deleteFlag = false;
                logger.error("离线数据--位置点删除", e);
            }
            codeFlag = true;
            data.put("codeFlag", codeFlag);
            data.put("deleteFlag", deleteFlag);
        }
        return Results.json().asRoot(data).done();
    }

    @Route("/blueplanet/offline/readExcel")
    public String readExcel() {
        try {
            if (readFile != null) {
                String fileType = readFileFileName.substring(readFileFileName.lastIndexOf(".") + 1, readFileFileName.length());
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(fileType) || Common.OFFICE_EXCEL_2007_POSTFIX.equals(fileType)) {
                    //获取资源路径
                    String path = ResourcePaths.galaxyResourcesDir("blueplanet/file/offline/");
                    File file = new File(path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    FileUtils.copyFile(readFile,new File(file,readFileFileName));
                    readExcel.readExcel(path + File.separator + readFileFileName);
                    ActionMessage.createByAction().success("上传成功并解析完成");
                    log("离线数据", "读取Excel");
                } else {
                    ActionMessage.createByAction().fail("文件格式不正确");
                }
            } else {
                ActionMessage.createByAction().fail("找不到文件");
            }
        } catch (Exception e) {
            ActionMessage.createByAction().fail("读取失败");
            e.printStackTrace();
        }
        return Results.redirect("/blueplanet/offline/offline.action");
    }

    /**
     * 验证码
     */

    private boolean verifyCode() {
        ActionContext actionContext = ActionContext.getContext();
        if (VerifyCodeAction.hasVerifyCode(actionContext, VERIFY_CODE_NAME)) {
            String realVerifyCode = VerifyCodeAction.getVerifyCode(actionContext, VERIFY_CODE_NAME);
            if (realVerifyCode.equalsIgnoreCase(verifyCode)) {
                return true;
            }
        }
        return false;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public List<LocationVO> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationVO> locationList) {
        this.locationList = locationList;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public File getReadFile() {
        return readFile;
    }

    public void setReadFile(File readFile) {
        this.readFile = readFile;
    }

    public String getReadFileFileName() {
        return readFileFileName;
    }

    public void setReadFileFileName(String readFileFileName) {
        this.readFileFileName = readFileFileName;
    }
}
