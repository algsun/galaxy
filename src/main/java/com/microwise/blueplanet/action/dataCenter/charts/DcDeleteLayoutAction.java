package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 删除布局Action.
 *
 * @author wang.geng
 * @date 13-12-10 下午4:19
 * @check @liu.zhu wang.geng 2013-12-18 #7192
 * @check @xie.deng li.jianfei 2014-3-5 #8049
 */
@Beans.Action
@Blueplanet
public class DcDeleteLayoutAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(DcDeleteLayoutAction.class);

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-list-layout.ftl";

    /**
     * 数据中心服务
     */
    @Autowired
    private DataCenterService dataCenterService;

    /**
     * 布局ID
     */
    private String layoutId;

    /**
     * div Id
     */
    private String divId;

    /**
     * siteId站点id
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/blueplanet/dataCenter/charts/deleteLayout/{layoutId}")
    public String deleteLayout() {
        try {
            dataCenterService.deleteLayoutById(layoutId);
            deletePicturesByLayoutId(layoutId);
        } catch (Exception e) {
            log.error("数据中心(图表组件化),删除布局", e);
        }
        log("数据中心(图表组件化)", "删除布局");
        return Results.redirect("/blueplanet/dataCenter/charts/listLayout");
    }

    @Route("/blueplanet/dataCenter/charts/{divId}/deleteItem.json")
    public String deleteItem() {
        try {
            dataCenterService.deleteConditionByItemId(divId);
            dataCenterService.deleteItemById(divId);
        } catch (Exception e) {
            log.error("数据中心(图表组件化),删除布局控件与图表查询条件", e);
        }
        log("数据中心(图表组件化)", "删除布局控件与图表查询条件");
        return Results.json().asRoot(true).done();
    }

    /**
     * 删除布局的时候调用，删除服务器上该布局相关的所有图片
     *
     * @param layoutId 布局ID
     */
    private void deletePicturesByLayoutId(String layoutId){
        String configPath = UpLoadFileUtil.getUploadPath(File.separator+"blueplanet") + File.separator+"images"+File.separator+"dataCenterConf";
        String slidePath = UpLoadFileUtil.getUploadPath(File.separator+"blueplanet") + File.separator+"images"+File.separator+"slide";
        File configFile = new File(configPath);
        File slideFile = new File(slidePath);

        if (configFile.exists() && configFile.isDirectory()) {
            File[] configFileList = configFile.listFiles();
            if(configFileList != null){
                for (File configF : configFileList) {
                    if (configF.getName().equals(layoutId)) {
                        configF.delete();
                    }
                }
            }
        }

        if (slideFile.exists() && slideFile.isDirectory()) {
            File[] slideFileList = slideFile.listFiles();
            if(slideFileList != null){
                for (File slideF : slideFileList) {
                    if (slideF.getName().startsWith(layoutId)) {
                        slideF.delete();
                    }
                }
            }
        }

    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public String getDivId() {
        return divId;
    }

    public void setDivId(String divId) {
        this.divId = divId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
