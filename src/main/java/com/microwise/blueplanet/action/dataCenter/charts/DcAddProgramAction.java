package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCItemPO;
import com.microwise.blueplanet.bean.po.DCSlidePO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.bean.vo.ZoneLocationVO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import com.microwise.common.util.UpLoadFileUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * 节目管理Action.
 *
 * @author wang.geng
 * @date 14-2-7 上午9:39
 * @check @xie.deng li.jianfei  2014-3-5 #8053
 */
@Beans.Action
@Blueplanet
public class DcAddProgramAction extends BlueplanetLoggerAction {
    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(DcAddLayoutAction.class);

    /**
     * 数据中心服务
     */
    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private LocationService locationService;

    //output
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-add-program.ftl";

    /**
     * 位置点分组（按区域）
     */
    private List<ZoneLocationVO> zoneLocationList;

    /**
     * 幻灯片路径
     */
    private String slideUrl;

    //input
    /**
     * 布局ID
     */
    private String uuid;

    /**
     * 控件ID
     */
    private String itemId;

    /**
     * 幻灯片ID
     */
    private int slideId;

    /**
     * 幻灯片标题
     */
    private String slideTitle;

    /**
     * 幻灯片播放时长
     */
    private int slidePlayTime;

    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 幻灯片描述
     */
    private String slideRemark;

    /**
     * 图片路径
     */
    private String hideFileName;

    /**
     * 上传图片
     */
    private File srcUploadFile;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    /**
     * 幻灯片实例
     */
    private DCSlidePO dcSlidePO = new DCSlidePO();

    /**
     * 是否是更新操作，若有id的值，则为更新操作，若为字符串“null”则为新增操作
     */
    private int dcSlidePOId;

    /**
     * 布局参数json字符串
     */
    private String jsonStr;

    //初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "blueplanet" + File.separator + "images" + File.separator + "slide";
    }

    /**
     * 行政站点下所有的基层站点
     */
    private List<SiteVO> siteVOList;

    /**
     * 位置点站点ID
     */
    private String locationSiteId;

    /**
     * siteId站点id
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/blueplanet/dataCenter/charts/index/slide/toAddSlide/{uuid}/{itemId}")
    public String toAddSlide() {
        try {
            if (siteId != null) {
                zoneLocationList = dataCenterService.findZoneLocationBySiteId(siteId);
            } else {
                int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
                siteVOList = dataCenterService.findSiteVOByLogicGroupId(logicGroupId);
            }

        } catch (Exception e) {
            log.error("信息发布，区域设备查询", e);
        }
        log("区域设备查询", "信息发布");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    @Route("/blueplanet/dataCenter/charts/index/slide/toUpdateSlide/{uuid}/{itemId}/{slideId}")
    public String toUpdateSlide() {
        try {
            siteId = Sessions.createByAction().currentSiteId();
            List<DCSlidePO> dcSlidePOs = dataCenterService.findSlideList(uuid, itemId);
            for (DCSlidePO dcSlide : dcSlidePOs) {
                if (dcSlide.getId() == slideId) {
                    dcSlidePO = dcSlide;
                }
            }
            if (siteId != null) {
                zoneLocationList = dataCenterService.findZoneLocationBySiteId(siteId);
            } else {
                int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
                siteVOList = dataCenterService.findSiteVOByLogicGroupId(logicGroupId);
                locationSiteId = locationService.findLocationById(dcSlidePO.getLocationId()).getSiteId();

            }
        } catch (Exception e) {
            log.error("信息发布，更新页面跳转", e);
        }
        log("更新页面跳转", "信息发布");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    @Route("/blueplanet/dataCenter/charts/index/slide/addSlide/{uuid}/{itemId}")
    public String addSlide() {

        try {
            DCSlidePO dcSlidePO = new DCSlidePO();
            dcSlidePO.setRelatedLayoutId(uuid);
            dcSlidePO.setDetail(slideRemark);
            dcSlidePO.setLocationId(locationId);
            dcSlidePO.setTitle(slideTitle);
            dcSlidePO.setRefresh(slidePlayTime);
            dcSlidePO.setRelatedItemId(itemId);
            if (dcSlidePOId == 0) {
                String fileName = uploadBgImage(uuid, itemId);
                dcSlidePO.setUrl(fileName);
                dataCenterService.saveSlide(dcSlidePO);
            } else {
                if (srcUploadFile != null) {
                    DCSlidePO slidePO = dataCenterService.findSlideShowById(dcSlidePOId);
                    File imgFile = new File(UpLoadFileUtil.getUploadPath("blueplanet" + File.separator + "images" + File.separator + "slide") + File.separator + slidePO.getUrl());
                    if (imgFile.exists()) {
                        imgFile.delete();
                    }
                    UpLoadFileUtil.fileUpload(srcUploadFile, imgFile);
                }
                dcSlidePO.setId(dcSlidePOId);
                dataCenterService.updateSlide(dcSlidePO);
            }
        } catch (Exception e) {
            log.error("信息发布，新增更新幻灯片", e);
        }
        log("新增更新幻灯片", "信息发布");
        return Results.redirect(String.format("/blueplanet/dataCenter/charts/index/slide/toSlideList/%s/%s", uuid, itemId));
    }

    @Route("/blueplanet/dataCenter/charts/index/slide/{uuid}/saveSlide.json")
    public String saveSlide() {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("jsonString");
            JSONObject jsonObj = jsonArray.getJSONObject(0);
            DCItemPO dcItemPO = new DCItemPO();
            dcItemPO.setRelated_layoutId(uuid);
            dcItemPO.setItem_id(jsonObj.get("id").toString());
            dcItemPO.setData_col(Integer.parseInt(jsonObj.get("data-col").toString()));
            dcItemPO.setData_row(Integer.parseInt(jsonObj.get("data-row").toString()));
            dcItemPO.setData_sizex(Integer.parseInt(jsonObj.get("data-sizex").toString()));
            dcItemPO.setData_sizey(Integer.parseInt(jsonObj.get("data-sizey").toString()));
            dcItemPO.setItemType(Integer.parseInt(jsonObj.get("itemType").toString()));
            dataCenterService.saveLayoutItem(dcItemPO);
        } catch (JSONException e) {
            log.error("信息发布，保存幻灯片布局", e);
        }
        log("保存幻灯片布局", "信息发布");
        return Results.json().done();
    }

    /**
     * 幻灯片图片上传
     */
    private String uploadBgImage(String layoutId, String divId) {
        String path = UpLoadFileUtil.getUploadPath(File.separator + "blueplanet") + File.separator + "images" + File.separator + "slide";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String fileName = layoutId + "_" + divId + "_" + System.currentTimeMillis() + ".jpg";
        String newPath = path + "/" + fileName;
        File destImageFile = new File(newPath);
        if (destImageFile.isFile() && destImageFile.exists()) {
            destImageFile.delete();
        }
        String isUpload = UpLoadFileUtil.fileUpload(srcUploadFile, destImageFile);
        if ("true".equals(isUpload)) {
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.action.dataCenter.photoSuccess"));
            log("藏品信息", "图片名称：" + fileName);
        }
        return fileName;
    }

    @Route("/blueplanet/dataCenter/charts/index/slide/findZoneLocationById/{siteId}")
    public String findZoneLocationBySiteId() {
        zoneLocationList = dataCenterService.findZoneLocationBySiteId(siteId);
        return Results.json().root("zoneLocationList").done();
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<ZoneLocationVO> getZoneLocationList() {
        return zoneLocationList;
    }

    public void setZoneLocationList(List<ZoneLocationVO> zoneLocationList) {
        this.zoneLocationList = zoneLocationList;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public File getSrcUploadFile() {
        return srcUploadFile;
    }

    public void setSrcUploadFile(File srcUploadFile) {
        this.srcUploadFile = srcUploadFile;
    }

    public String getSlideUrl() {
        return slideUrl;
    }

    public void setSlideUrl(String slideUrl) {
        this.slideUrl = slideUrl;
    }

    public String getSlideTitle() {
        return slideTitle;
    }

    public void setSlideTitle(String slideTitle) {
        this.slideTitle = slideTitle;
    }

    public int getSlidePlayTime() {
        return slidePlayTime;
    }

    public void setSlidePlayTime(int slidePlayTime) {
        this.slidePlayTime = slidePlayTime;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getSlideRemark() {
        return slideRemark;
    }

    public void setSlideRemark(String slideRemark) {
        this.slideRemark = slideRemark;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getSlideId() {
        return slideId;
    }

    public void setSlideId(int slideId) {
        this.slideId = slideId;
    }

    public DCSlidePO getDcSlidePO() {
        return dcSlidePO;
    }

    public void setDcSlidePO(DCSlidePO dcSlidePO) {
        this.dcSlidePO = dcSlidePO;
    }

    public int getDcSlidePOId() {
        return dcSlidePOId;
    }

    public void setDcSlidePOId(int dcSlidePOId) {
        this.dcSlidePOId = dcSlidePOId;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getHideFileName() {
        return hideFileName;
    }

    public void setHideFileName(String hideFileName) {
        this.hideFileName = hideFileName;
    }

    public List<SiteVO> getSiteVOList() {
        return siteVOList;
    }

    public void setSiteVOList(List<SiteVO> siteVOList) {
        this.siteVOList = siteVOList;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getLocationSiteId() {
        return locationSiteId;
    }

    public void setLocationSiteId(String locationSiteId) {
        this.locationSiteId = locationSiteId;
    }
}
