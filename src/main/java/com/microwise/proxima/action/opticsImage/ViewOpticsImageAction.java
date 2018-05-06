package com.microwise.proxima.action.opticsImage;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.action.dvPlace.UploadDVrealmapAction;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.DVPlaceService;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 浏览光学图片
 *
 * @author wang yunlong
 * @time 2013-3-25
 * @check li.jianfei 2013-3-29 #2411
 */
@Beans.Action
@Proxima
public class ViewOpticsImageAction extends ProximaLoggerAction {

    private static final int DEFAULT_PICTURE_COUNT = 5;

    public static final Logger log = LoggerFactory.getLogger(ViewOpticsImageAction.class);
    /**
     * 获得图片service
     */
    @Autowired
    private PictureService pictureService;

    /**
     * 摄像机 service
     */
    @Autowired
    private DVPlaceService dvPlaceService;

    /**
     * 光学摄像机service
     */
    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;
    /**
     * 区域service
     */
    @Autowired
    private ZoneService zoneService;

    // input
    /**
     * 区域
     */
    private String zoneId;

    /**
     * 摄像机点位ID
     */
    private String dvPlaceId;
    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    // output
    /**
     * 图片集合
     */
    private List<PictureBean> pictures;
    /**
     * 默认展示点位
     */
    private List<OpticsDVPlaceBean> opticsDVPlaces;
    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    /**
     * 有摄像机点位的区域
     */
    private List<Zone> zones;

    // 初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + "/proxima/images";
    }

    /**
     * 摄像机实景图
     */
    private String realmapUrl;

    /**
     * 摄像机名称
     */
    private String dvName;

    //Output
    /**
     * 页面是否展开
     */
    private boolean inputView;

    /**
     * 默认查询
     */
    public String view() {
        try {
            initDateIfAbsent();
            String siteId = Sessions.createByAction().currentSiteId();
            zones = zoneService.findHasOptics(siteId);
            opticsDVPlaces = opticsDVPlaceService.findAll(siteId);
            List<OpticsDVPlaceBean> opticsDVPlaces = opticsDVPlaceService.findAll(siteId, 1, 1);
            if (opticsDVPlaces != null && opticsDVPlaces.size() > 0) {
                for (OpticsDVPlaceBean o : opticsDVPlaces) {
                    if (o != null) {
                        dvPlaceId = o.getId();
                    }
                    break;
                }
                // 活动 摄像机 实景图
                handleRealmap(dvPlaceId);
                // 默认查询 第一个摄像机的最近5条数据
                pictures = pictureService.findRecentPictures(dvPlaceId, DEFAULT_PICTURE_COUNT);
                if (pictures.size() > 0) {
                    startDate = pictures.get(pictures.size() - 1).getSaveTime();
                    endDate = pictures.get(0).getSaveTime();
                }
                log("图片浏览", "图片浏览");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 条件查询
     */
    public String execute() {
        try {
            initDateIfAbsent();
            String siteId = Sessions.createByAction().currentSiteId();
            zones = zoneService.findHasOptics(siteId);
            if (Strings.isNullOrEmpty(zoneId)) {
                opticsDVPlaces = opticsDVPlaceService.findAll(siteId);
            } else {
                opticsDVPlaces = opticsDVPlaceService.findByZoneId(zoneId);
            }
            handleRealmap(dvPlaceId);
            pictures = pictureService.findByTime(dvPlaceId, startDate, endDate);
            log("图片浏览", "查询图片");

        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 初始化开始结束时间，如果不存在
     */
    private void initDateIfAbsent() {
        if (startDate == null || endDate == null) {
            startDate = DateTime.now().withTimeAtStartOfDay().toDate();
            endDate = DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
        }
    }

    /**
     * 处理摄像机 实景图
     */
    private void handleRealmap(String dvPlaceId) {
        DVPlaceBean dvPlaceBean = dvPlaceService.findById(dvPlaceId);
        String realmap = dvPlaceBean.getRealmap();
        dvName = dvPlaceBean.getPlaceName();
        if (realmap != null && !realmap.equals("")) {
            realmapUrl = picturesBasePath + "/realmap/" + realmap;
        } else {
            realmapUrl = UploadDVrealmapAction.DEFAULT_REALMAP_URL;
        }
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<PictureBean> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureBean> pictures) {
        this.pictures = pictures;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public List<OpticsDVPlaceBean> getOpticsDVPlaces() {
        return opticsDVPlaces;
    }

    public void setOpticsDVPlaces(List<OpticsDVPlaceBean> opticsDVPlaces) {
        this.opticsDVPlaces = opticsDVPlaces;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public String getRealmapUrl() {
        return realmapUrl;
    }

    public void setRealmapUrl(String realmapUrl) {
        this.realmapUrl = realmapUrl;
    }

    public String getDvName() {
        return dvName;
    }

    public void setDvName(String dvName) {
        this.dvName = dvName;
    }

    public boolean isInputView() {
        return inputView;
    }

    public void setInputView(boolean inputView) {
        this.inputView = inputView;
    }
}
