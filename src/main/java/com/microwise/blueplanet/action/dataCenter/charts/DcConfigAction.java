package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCConfigPO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import com.microwise.common.util.UpLoadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 信息发布配置Action
 *
 * @author wang.geng
 * @date 14-1-22 上午10:29
 * @check @xie.deng li.jianfei 2014-3-5 #8053
 */
@Beans.Action
@Blueplanet
public class DcConfigAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(DcConfigAction.class);


    /**
     * 数据中心服务
     */
    @Autowired
    private DataCenterService dataCenterService;

    //output
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-config.ftl";

    /**
     * 背景图片路径
     */
    private String url;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    //初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "blueplanet"+File.separator+"images"+File.separator+"dataCenterConf";
    }

    //input
    /**
     * 布局ID
     */
    private String layoutId;

    /**
     * 上传图片
     */
    private File srcUploadFile;

    /**
     * siteId站点id
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/blueplanet/dataCenter/charts/toConfig/{layoutId}")
    public String execute() {
        try {
            ActionMessage.createByAction().consume();
            DCConfigPO dcConfigPO = dataCenterService.findDCConfig(layoutId);

            url = dcConfigPO != null ? dcConfigPO.getUrl() : "";

        } catch (Exception e) {
            logFailed("信息发布", "信息发布配置");
            log.error("信息发布配置", e);
        }
        log("信息发布", "信息发布配置");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    @Route("/blueplanet/dataCenter/charts/uploadBgImage")
    public String uploadBgImage() {
        String path = UpLoadFileUtil.getUploadPath(File.separator+"blueplanet") +File.separator+ "images"+File.separator+"dataCenterConf";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String newPath = path + "/" + layoutId + ".jpg";
        File destImageFile = new File(newPath);
        if (destImageFile.isFile() && destImageFile.exists()) {
            destImageFile.delete();
        }
        String isUpload = UpLoadFileUtil.fileUpload(srcUploadFile, destImageFile);
        if ("true".equals(isUpload)) {

            addPicToDB(layoutId);
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.action.dataCenter.photoSuccess"));
            log("藏品信息", "图片名称：" + layoutId + ".jpg");

        }
        return Results.redirect(String.format("/blueplanet/dataCenter/charts/toConfig/%s", layoutId));
    }

    @Route("/blueplanet/dataCenter/charts/reset/{layoutId}")
    public String reset() {
        try {
            dataCenterService.deleteDCCondig(layoutId);
        } catch (Exception e) {
            logFailed("信息发布", "重置图片");
            log.error("信息发布，充值图片", e);
        }
        log("信息发布", "重置图片");
        return Results.redirect(String.format("/blueplanet/dataCenter/charts/toConfig/%s", layoutId));
    }

    /**
     * 保存图片信息到数据库
     *
     * @param layoutId 布局ID
     */
    private void addPicToDB(String layoutId) {
        DCConfigPO dcConfigPO = new DCConfigPO();
        dcConfigPO.setRelated_layoutId(layoutId);
        dcConfigPO.setUrl(layoutId + ".jpg");
        dataCenterService.saveDCConfig(dcConfigPO);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public File getSrcUploadFile() {
        return srcUploadFile;
    }

    public void setSrcUploadFile(File srcUploadFile) {
        this.srcUploadFile = srcUploadFile;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
