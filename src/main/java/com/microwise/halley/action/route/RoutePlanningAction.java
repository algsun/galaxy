package com.microwise.halley.action.route;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.bean.vo.PathPointsVO;
import com.microwise.halley.service.ExhibitionStateService;
import com.microwise.halley.service.PathService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线路预设 EventAction
 *
 * @author li.jianfei
 * @date 2013-10-16
 * @check @wang.geng xu.yuexi 2013-10-25  #6166
 */
@Beans.Action
@Halley
public class RoutePlanningAction {

    public static final Logger log = LoggerFactory.getLogger(RoutePlanningAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../route/route-planning2.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 外展状态 Service
     */
    @Autowired
    private ExhibitionStateService exhibitionStateService;

    /**
     * 线路 Service
     */
    @Autowired
    private PathService pathService;

    // Input
    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 预设线路
     */
    private String path;

    /**
     * 保存GPS点
     */
    private String point;

    // Output
    /**
     * 行驶线路
     */
    private List<PathPO> pathList;

    /**
     * 保存的GPS点集
     */
    private List<PathPointsVO> pathPointsList;

    /**
     * 是否可编辑
     */
    private boolean editable;

    private int pathType;

    @Route("/halley/routePlanning/exhibition/{exhibitionId}")
    public String routePlaning() {

        try {
            // 查询预设线路
            pathList = pathService.findPathByExhibitionId(exhibitionId);

            // 路径 Json 数据
            if (pathList.size() > 0) {
                // 移除第一个和最后一个目的地信息(当前站点)
                pathList.remove(0);
                pathList.remove(pathList.size() - 1);
                pathType = pathList.get(0).getPathType();
            }
            path = new Gson().toJson(pathList);

            // 获取可编辑状态
            ExhibitionStateVO currentState = exhibitionStateService.findCurrentState(exhibitionId);
            editable = (currentState == null || currentState.getState() == 0);
            halleyLogger.log("外展管理", "查询线路预设");
        } catch (Exception e) {
            log.error("查询线路预设", e);
            halleyLogger.logFailed("外展管理", "查询线路预设");
        }

        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    @Route("/halley/routePlanning/exhibition/{exhibitionId}/save")
    public String savePath() {

        Map<String, Object> data = new HashMap<String, Object>();

        try {
            // 删除当前预设线路
            pathService.deleteByExhibitionId(exhibitionId);

            // 获取预设线路信息
            Gson gson = new Gson();
            Type type = new TypeToken<List<PathPO>>() {
            }.getType();
            Type pointType = new TypeToken<List<PathPointsVO>>() {
            }.getType();
            pathList = gson.fromJson(path, type);
            pathPointsList = gson.fromJson(point, pointType);

            pathService.savePathList(pathList);
            if (pathPointsList != null) {
                pathService.savePathPointList(pathPointsList);
            }
            data.put("success", true);
            ActionMessage.createByAction().success("预设线路成功");
            halleyLogger.log("线路预设", "保存预设线路");
        } catch (JsonSyntaxException e) {
            data.put("success", false);
            log.error("保存预设线路", e);
            ActionMessage.createByAction().fail("预设线路失败");
            halleyLogger.logFailed("线路预设", "保存预设线路");
        }
        ActionContext.getContext().put("data", data);
        return Results.json().root("data").done();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<PathPO> getPathList() {
        return pathList;
    }

    public void setPathList(List<PathPO> pathList) {
        this.pathList = pathList;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public List<PathPointsVO> getPathPointsList() {
        return pathPointsList;
    }

    public void setPathPointsList(List<PathPointsVO> pathPointsList) {
        this.pathPointsList = pathPointsList;
    }

    public int getPathType() {
        return pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }
}

