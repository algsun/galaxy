package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.OpticsDVPlacePO;
import com.microwise.halley.bean.vo.TimeIntervalVO;
import com.microwise.halley.service.DeviceService;
import com.microwise.halley.service.HistoryDataService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.proxy.PictureProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 浏览光学图片
 *
 * @author xu.yuexi
 * @date 2013-10-30
 */
@Beans.Action
@Halley
public class QueryOpticsImageAction {

    public static final Logger log = LoggerFactory.getLogger(QueryOpticsImageAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../exhibition/image-view.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 获得图片的代理
     */
    @Autowired
    private PictureProxy pictureProxy;

    @Autowired
    private DeviceService deviceService;
    /**
     * 历史数据查询服务
     */
    @Autowired
    private HistoryDataService historyDataService;

    //input
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

    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 当前时间段
     */
    private TimeIntervalVO timeInterval;

    /**
     * 页面选择的Index
     */
    private int timeIntervalIndex;


    // output
    /**
     * 图片集合
     */
    private List<PictureBean> pictures;
    /**
     * 摄像机点位
     */
    private List<OpticsDVPlacePO> opticsDVPlaces;

    /**
     * 时间段划分
     */
    private List<TimeIntervalVO> timeIntervalVOs;
    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    // 初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "proxima" + File.separator + "images";
    }

    /**
     * 默认查询
     */
    @Route("/halley/defaultImageView/exhibition/{exhibitionId}/timeIntervalIndex/{timeIntervalIndex}")
    public String view() {
        try {
            initDateIfAbsent();
            opticsDVPlaces = deviceService.findOpticsDVByExhibitionId(exhibitionId);
            if (opticsDVPlaces.size() > 0) {
                dvPlaceId = opticsDVPlaces.get(0).getId();
                pictures = pictureProxy.findByTime(dvPlaceId, startDate, endDate);
                halleyLogger.log("外展管理", "图片浏览");
            }
        } catch (Exception e) {
            halleyLogger.logFailed("外展管理", "图片浏览");
            log.error("图片浏览", e);
        }
        ActionMessage.createByAction().consume();
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 条件查询
     */
    @Route("/halley/conditionImageView/exhibition/{exhibitionId}")
    public String execute() {
        try {
            initDateIfAbsent();
            opticsDVPlaces = deviceService.findOpticsDVByExhibitionId(exhibitionId);
            pictures = pictureProxy.findByTime(dvPlaceId, startDate, endDate);
            halleyLogger.log("外展管理", "图片浏览");

        } catch (Exception e) {
            halleyLogger.logFailed("外展管理", "图片浏览");
            log.error("图片浏览", e);
        }
        ActionMessage.createByAction().consume();
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 初始化开始结束时间，如果不存在
     */
    private void initDateIfAbsent() {
        //获取时间段列表
        timeIntervalVOs = historyDataService.getTimeInterval(exhibitionId);
        //开始时间为所选时间段的开始时间  ，timeIntervalIndex是时间段序号
        startDate = timeIntervalVOs.get(timeIntervalIndex).getBeginTime();
        //时间段的结束时间可能为空，如果不给空将时间段的结束时间赋给结束时间
        endDate = timeIntervalVOs.get(timeIntervalIndex).getEndTime();
        if (endDate == null) {
            endDate = new Date();
        }
    }

    public static String get_pagePath() {
        return _pagePath;
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

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public List<PictureBean> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureBean> pictures) {
        this.pictures = pictures;
    }

    public List<OpticsDVPlacePO> getOpticsDVPlaces() {
        return opticsDVPlaces;
    }

    public void setOpticsDVPlaces(List<OpticsDVPlacePO> opticsDVPlaces) {
        this.opticsDVPlaces = opticsDVPlaces;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public TimeIntervalVO getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeIntervalVO timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getTimeIntervalIndex() {
        return timeIntervalIndex;
    }

    public void setTimeIntervalIndex(int timeIntervalIndex) {
        this.timeIntervalIndex = timeIntervalIndex;
    }

    public List<TimeIntervalVO> getTimeIntervalVOs() {
        return timeIntervalVOs;
    }

    public void setTimeIntervalVOs(List<TimeIntervalVO> timeIntervalVOs) {
        this.timeIntervalVOs = timeIntervalVOs;
    }
}
