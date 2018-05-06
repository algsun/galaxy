package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans.Action;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.service.ExhibitionStateService;
import com.microwise.halley.service.LocationService;
import com.microwise.halley.service.PackingListService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 查询外展状态记录 EventAction
 *
 * @author xu.yuexi
 * @date 2013-10-22
 * @check @wang.geng li.jianfei 2013-10-25  #6160
 */
@Action
@Halley
public class UpdateExhibitionStateAction {

    private final String TAG = "外展状态";

    public static final Logger log = LoggerFactory.getLogger(UpdateExhibitionStateAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../exhibition/query-exhibitionState-list.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 外展状态 Service
     */
    @Autowired
    private ExhibitionStateService exhibitionStateService;


    /**
     * 位置点Service
     */
    @Autowired
    private LocationService locationService;

    /**
     * 装箱单Service
     */
    @Autowired
    private PackingListService packingListService;

    // Input
    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 外展状态
     * 1-开始外展  2-开始运输  3-结束运输  4-结束外展
     */
    private Integer state;


    // Output
    /**
     * 外展状态
     */
    private ExhibitionStateVO exhibition;

    @Route("/halley/updateExhibitionState/exhibition/{exhibitionId}/exhibitionState/{state}")
    public String execute() {
        try {
            if (state == 1) {   // 开始外展
                int allPathSize = exhibitionStateService.getALLPathPO(exhibitionId).size();
                int locationSize = locationService.findLocationListByExhibitionId(exhibitionId).size();
//                int packingListSize = packingListService.findPackingList(exhibitionId).size();
//                packingListSize <= 0
                if (allPathSize <= 0 ||
                        locationSize <= 0) {
                    // 判段是否存在预设路线
                    if (allPathSize <= 0) {
                        ActionMessage.createByAction().fail("你还没有预设路径");
                    }
                    // 判段是否配置设备
                    if (locationSize <= 0) {
                        ActionMessage.createByAction().fail("你还没有配置设备");
                    }
                    // 判段是否存在装箱单
//                    if (packingListSize <= 0) {
//                        ActionMessage.createByAction().fail("你还没有装箱单");
//                    }
                    halleyLogger.logFailed(TAG, "开始外展");
                    return Results.redirect(String.format("/halley/queryExhibitionStateList/exhibition/%s", exhibitionId));
                }
            }

            // 添加状态
            exhibitionStateService.addExhibitionState(exhibitionId, state, Sessions.createByAction().currentUser().getId());

            // 根据当前状态提示信息
            String operatorName = "";
            switch (state) {
                case 1:
                    operatorName = "开始外展";
                    break;
                case 2:
                    operatorName = "开始运输";
                    break;
                case 3:
                    operatorName = "结束运输";
                    break;
                case 4:
                    operatorName = "结束外展";
                    break;
            }
            ActionMessage.createByAction().success(operatorName + "成功");
            halleyLogger.log(TAG, operatorName);
        } catch (Exception e) {
            log.error(TAG, e);
            ActionMessage.createByAction().fail("修改外展状态失败");
            halleyLogger.logFailed(TAG, TAG);
        }
        return Results.redirect(String.format("/halley/queryExhibitionStateList/exhibition/%s", exhibitionId));
    }

    public static String get_pagePath() {
        return _pagePath;
    }


    public ExhibitionStateVO getExhibition() {
        return exhibition;
    }

    public void setExhibition(ExhibitionStateVO exhibition) {
        this.exhibition = exhibition;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
