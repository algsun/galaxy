package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.action.ActionMessage;
import com.microwise.halley.bean.po.ConfigPO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 编辑报警范围配置
 *
 * @author wanggeng
 * @date 13-9-29 下午3:35
 */
public class EditAlarmRangeAction {
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../exhibition/car-config.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 车辆配置服务层对象
     */
    @Autowired
    private CarService carService;

    //input
    /**
     * 报警范围
     */
    private int alarmRange;

    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 报警范围配置
     *
     * @return string
     */
    @Route("/halley/exhibition/editAlarmRange/{exhibitionId}")
    public String execute() {
        try {
            ConfigPO configPO = new ConfigPO();
            configPO.setExhibitionId(exhibitionId);
            configPO.setAlarmRange(alarmRange);
            carService.saveConfigPO(configPO);
            ActionMessage.createByAction().success("配置成功保存");
            halleyLogger.log("外展配置", "其它配置");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("配置保存失败");
            halleyLogger.logFailed("外展配置", "其它配置");
            e.printStackTrace();
        }
        return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getAlarmRange() {
        return alarmRange;
    }

    public void setAlarmRange(int alarmRange) {
        this.alarmRange = alarmRange;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }
}
