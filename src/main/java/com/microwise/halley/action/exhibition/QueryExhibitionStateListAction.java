package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans.Action;
import com.microwise.halley.bean.po.OutEventPO;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.bean.vo.ExhibitionVO;
import com.microwise.halley.service.ExhibitionService;
import com.microwise.halley.service.ExhibitionStateService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 查询外展状态记录 EventAction
 *
 * @author xu.yuexi
 * @date 2013-10-22
 * @check @wang.geng li.jianfei  2013-10-25  #6160
 */
@Action
@Halley
public class QueryExhibitionStateListAction {

    public static final Logger log = LoggerFactory.getLogger(QueryExhibitionStateListAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../exhibition/query-exhibitionState-list.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 外展记录 Service
     */
    @Autowired
    private ExhibitionService exhibitionService;

    /**
     * 外展状态 Service
     */
    @Autowired
    private ExhibitionStateService exhibitionStateService;


    // Input
    /**
     * 外展ID
     */
    private int exhibitionId;

    // Output
    /**
     * 第一个目的地
     */
    private PathPO pathPO;

    /**
     * 外展事件
     */
    private OutEventPO outEventPO;

    /**
     * 外展
     */
    private ExhibitionVO exhibitionVO;

    /**
     * 外展列表
     */
    private List<ExhibitionStateVO> exhibitionList;

    @Route("/halley/queryExhibitionStateList/exhibition/{exhibitionId}")
    public String execute() {
        try {
            //获取所有历史操作
            exhibitionList = exhibitionStateService.getHistoryState(exhibitionId);

            // 获取起始目的地
            pathPO = exhibitionStateService.getStartDestination(exhibitionId);

            // 获取外展详细信息
            exhibitionVO = exhibitionService.findExhibition(exhibitionId);
            // 设置外展当前状态
            ExhibitionStateVO exhibitionStateVO = exhibitionStateService.findCurrentState(exhibitionId);
            if (exhibitionStateVO != null) {
                exhibitionVO.setState(exhibitionStateVO.getState());
            }

            halleyLogger.log("外展状态", "查询外展状态");
        } catch (Exception e) {
            log.error("外展状态", e);
            halleyLogger.logFailed("外展状态", "查询外展状态");
        }
        ActionMessage.createByAction().consume();
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public PathPO getPathPO() {
        return pathPO;
    }

    public void setPathPO(PathPO pathPO) {
        this.pathPO = pathPO;
    }

    public OutEventPO getOutEventPO() {
        return outEventPO;
    }

    public void setOutEventPO(OutEventPO outEventPO) {
        this.outEventPO = outEventPO;
    }

    public ExhibitionVO getExhibitionVO() {
        return exhibitionVO;
    }

    public void setExhibitionVO(ExhibitionVO exhibitionVO) {
        this.exhibitionVO = exhibitionVO;
    }

    public List<ExhibitionStateVO> getExhibitionList() {
        return exhibitionList;
    }

    public void setExhibitionList(List<ExhibitionStateVO> exhibitionList) {
        this.exhibitionList = exhibitionList;
    }
}
