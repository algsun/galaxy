package com.microwise.halley.action.packingList;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.vo.PackingListVO;
import com.microwise.halley.service.PackingListService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.microwise.common.sys.annotation.Beans.Action;

/**
 * 装箱单查询 EventAction
 *
 * @author li.jianfei
 * @date 2013-10-08
 * @check @wang.geng #5765 2013-10-10
 */
@Action
@Halley
public class QueryPackingListAction {

    public static final Logger log = LoggerFactory.getLogger(QueryPackingListAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../packingList/query-packing-list.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 装箱单 Service
     */
    @Autowired
    private PackingListService packingListService;

    // Input
    /**
     * 外展ID
     */
    private int exhibitionId;

    // Output
    /**
     * 装箱单
     */
    private Map<CarPO, List<PackingListVO>> mapPackingLists;

    @Route("/halley/queryPackingList/exhibition/{exhibitionId}")
    public String execute() {

        try {
            // 根据车辆ID查询装箱清单
            mapPackingLists = packingListService.findPackingListByExhibitionId(exhibitionId);
            halleyLogger.log("外展管理", "装箱单");
        } catch (Exception e) {
            log.error("装箱单", e);
            halleyLogger.logFailed("外展管理", "装箱单");
        }
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

    public Map<CarPO, List<PackingListVO>> getMapPackingLists() {
        return mapPackingLists;
    }

    public void setMapPackingLists(Map<CarPO, List<PackingListVO>> mapPackingLists) {
        this.mapPackingLists = mapPackingLists;
    }
}

