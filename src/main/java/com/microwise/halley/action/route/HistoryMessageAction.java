package com.microwise.halley.action.route;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.halley.bean.po.AlarmPO;
import com.microwise.halley.service.AlarmService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 历史传感器数据和路线数据
 *
 * @author xu.yuexi
 * @date 13-11-4
 * @check @li.jianfei 2014-5-22 #8588
 */
@Beans.Action
@Halley
public class HistoryMessageAction {

    public static final Logger log = LoggerFactory.getLogger(HistoryMessageAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../route/history-message.ftl";


    @Autowired
    private HalleyLoggerAction halleyLogger;


    /**
     * 报警Service
     */
    @Autowired
    private AlarmService alarmService;

    // Input
    /**
     * 外展ID
     */
    private int exhibitionId;


    /**
     * 分页大小
     */
    private int pageCount;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 当前页, 默认第一页
     */
    private int page = 1;


    /**
     * 报警短信记录集合
     */
    private List<AlarmPO> alarmList;


    /**
     * 短信总页数
     */
    private int pageSum;


    @Route(value = "/halley/messageHistory", params = {"exhibitionId", "page"})
    public String messageHistory() {
        try {
            alarmList = alarmService.findAlarmListByPage(exhibitionId, startDate, endDate, page, Constants.SIZE_PER_PAGE);
            pageSum = PagingUtil.pagesCount(alarmService.findAlarmListCount(exhibitionId, startDate, endDate), Constants.SIZE_PER_PAGE);
            halleyLogger.log("查询短信记录", "查询短信记录");
        } catch (Exception e) {
            log.error("查询历史数据", e);
            halleyLogger.logFailed("查询短信记录", "查询短信记录");
        }
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    public void setHalleyLogger(HalleyLoggerAction halleyLogger) {
        this.halleyLogger = halleyLogger;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public Date getStartDate() {
        return startDate;
    }


    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public List<AlarmPO> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AlarmPO> alarmList) {
        this.alarmList = alarmList;
    }
}
