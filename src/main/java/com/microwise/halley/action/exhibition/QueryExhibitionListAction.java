package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans.Action;
import com.microwise.common.util.PagingUtil;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.bean.vo.ExhibitionVO;
import com.microwise.halley.service.ExhibitionService;
import com.microwise.halley.service.ExhibitionStateService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 查询外展记录 EventAction
 *
 * @author li.jianfei
 * @date 2013-09-27
 * @check @wang.geng #5847 2013-10-10
 */
@Action
@Halley
public class QueryExhibitionListAction {

    public static final Logger log = LoggerFactory.getLogger(QueryExhibitionListAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../exhibition/query-exhibition-list.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 外展 Service
     */
    @Autowired
    private ExhibitionService exhibitionService;
    /**
     * 外展状态 Service
     */
    @Autowired
    private ExhibitionStateService exhibitionStateService;
    //Input
    /**
     * 外展状态
     * 5-全部；默认查询全部
     * TODO 使用枚举 定义状态
     */
    private int state = 5;

    /**
     * 分页的当前页面
     */
    private int page = 1;

    /**
     * 分页的总数
     */
    private int pageSum;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    // Output
    /**
     * 外展列表
     */
    private List<ExhibitionVO> exhibitionList;


    @Route("/halley/queryExhibitions")
    public String execute() {
        try {
            this.initDateIfAbsent();
            // 获取 siteId
            String siteId = Sessions.createByAction().currentSiteId();

            // 获取当前站点下的外展列表
            exhibitionList = exhibitionService.findExhibitionList(siteId, state, page, Constants.SIZE_PER_PAGE, startDate, endDate);
            for (ExhibitionVO exhibition : exhibitionList) {
                exhibition.setOnDisplay(isOnDisplay(exhibition.getExhibitionId()));
            }
            pageSum = PagingUtil.pagesCount(exhibitionService.findExhibitionListCount(siteId, state, startDate, endDate), Constants.SIZE_PER_PAGE);
            halleyLogger.log("外展管理", "外展查询");
        } catch (Exception e) {
            log.error("外展查询", e);
            halleyLogger.logFailed("外展管理", "外展查询");
        }
        ActionMessage.createByAction().consume();
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 初始化开始结束时间，如果不存在
     */
    private void initDateIfAbsent() {
        if (startDate == null || endDate == null) {
            startDate = DateTime.now().minusMonths(1).withTimeAtStartOfDay().toDate();
            endDate = DateTime.now().minusMillis(1).toDate();
        }
    }

    /**
     * 判断当前的外展是否陈展中
     */
    private boolean isOnDisplay(int exhibitionId) {
        ExhibitionStateVO exhibitionState = exhibitionStateService.findCurrentState(exhibitionId);
        if (exhibitionState != null) {
            if (exhibitionState.getState() == 3 && exhibitionState.getPathPO() != null) {
                return true;
            }
        }
        return false;
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public List<ExhibitionVO> getExhibitionList() {
        return exhibitionList;
    }

    public void setExhibitionList(List<ExhibitionVO> exhibitionList) {
        this.exhibitionList = exhibitionList;
    }

    public int getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setState(int state) {
        this.state = state;
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
}
