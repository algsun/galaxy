package com.microwise.proxima.action.infraredImage;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.InfraredDVPlaceService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 浏览光学图片
 *
 * @author gaohui
 * @date 2012-7-11
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 #8344
 * <p/>
 * <p/>
 * TODO 处理异常 打印日志
 */
@Beans.Action
@Proxima
public class ViewInfraredImageAction extends ProximaLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(ViewInfraredImageAction.class);

    @Autowired
    private InfraredDVPlaceService dvPlaceServcie;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private InfraredDVPlaceService infraredDVPlaceService;

    /**
     * 区域service
     */
    @Autowired
    private ZoneService zoneService;

    private int monitorPointId; // 监测点ID
    private String dvPlaceId; // 摄像机点位ID
    private Date startDate; // 开始时间
    private Date endDate; // 结束时间
    private List<PictureBean> pictures; // 查询图片集合
    /**
     * 有摄像机点位的区域
     */
    private List<Zone> zones;

    /**
     * 默认展示点位
     */
    private List<InfraredDVPlaceBean> infraredDVPlaces;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    // 初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + "/proxima/images";
    }

    //Output
    /**
     * 页面是否展开
     */
    private boolean inputView;


    /**
     * 默认查询
     *
     * @return
     */
    public String view() {
        try {
            initDefaultDate();
            String siteId = Sessions.createByAction().currentSiteId();
            zones = zoneService.findHasInfrareds(siteId);
            infraredDVPlaces = infraredDVPlaceService.findAll(siteId);
            if (!infraredDVPlaces.isEmpty()) {
                for (InfraredDVPlaceBean o : infraredDVPlaces) {
                    if (o != null) {
                        dvPlaceId = o.getId();
                    }
                    break;
                }
                // 加载第一个 dvPlace 的 图片
                DVPlaceBean firstDVPlace = infraredDVPlaces.get(0);
                pictures = pictureService.findByTime(firstDVPlace.getId(), startDate, endDate);
            }
            log("图片浏览", "红外摄像机图片");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("红外摄像机图片", e);
            return Action.ERROR;
        }

    }

    /**
     * 条件查询
     *
     * @return
     */
    public String execute() {
        try {
            // 如果时间为空, 则时间初始化为默认时间
            if (startDate == null || endDate == null) {
                initDefaultDate();
            }
            String siteId = Sessions.createByAction().currentSiteId();
            zones = zoneService.findHasInfrareds(siteId);
            infraredDVPlaces = infraredDVPlaceService.findAll(siteId);
            pictures = pictureService.findByTime(dvPlaceId,
                    startDate, endDate);
            log("图片浏览", "红外摄像机图片");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("红外摄像机图片", e);
            return Action.ERROR;
        }
    }


    // 初始化默认时间段
    private void initDefaultDate() {
        int defaultDays = Integer.parseInt(ConfigFactory
                .getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL)
                .get(Constants.Proxima.KEY_VIEW_IMAGE_QUERY_DEFAULT_DAYS));
        // n天前 0点0分
        startDate = DateTimeUtil.startOfDayDT(new Date()).minusDays(defaultDays - 1).toDate();
        // 今天 的 23点59分
        endDate = DateTimeUtil.endOfDay(new Date());
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

    public int getMonitorPointId() {
        return monitorPointId;
    }

    public void setMonitorPointId(int monitorPointId) {
        this.monitorPointId = monitorPointId;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }


    public List<PictureBean> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureBean> pictures) {
        this.pictures = pictures;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<InfraredDVPlaceBean> getInfraredDVPlaces() {
        return infraredDVPlaces;
    }

    public void setInfraredDVPlaces(List<InfraredDVPlaceBean> infraredDVPlaces) {
        this.infraredDVPlaces = infraredDVPlaces;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public boolean isInputView() {
        return inputView;
    }

    public void setInputView(boolean inputView) {
        this.inputView = inputView;
    }
}
