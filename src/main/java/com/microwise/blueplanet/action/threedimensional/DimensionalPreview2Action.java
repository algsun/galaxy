package com.microwise.blueplanet.action.threedimensional;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;
import com.microwise.blueplanet.service.ThreeDimensionalService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 王耕
 * @date 15-7-13
 */
@Beans.Action
@Blueplanet
public class DimensionalPreview2Action extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(DimensionalPreview2Action.class);

    @Autowired
    private ThreeDimensionalService threeDimensionalService;

    // 内容页面
    private static final String _pagePath = "dimensional-field2.ftl";

    /**
     * 模型文件id
     */
    private int dimensionalId;


    /**
     * 模型文件实体类
     */
    private String path;

    @Route("/blueplanet/three-dimensional/dimensionalPreview2")
    public String dimensionalPreview2(){
        ThreeDimensionalPO threeDimensional = threeDimensionalService.findThreeDimenById(dimensionalId);
        String subPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/file/threedimensional";
        path = subPath+ "/" + threeDimensional.getPath();
        return Results.ftl("/blueplanet/pages/threedimensional/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getDimensionalId() {
        return dimensionalId;
    }

    public void setDimensionalId(int dimensionalId) {
        this.dimensionalId = dimensionalId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
