package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 区域实景图上传
 *
 * @author Wang yunlong
 * @date 13-2-21 下午1:20
 * @check @gaohui 2013-02-25 #1689
 */
@Beans.Action
@Blackhole
public class PlanImageUploadAction {
    private static final Logger log = LoggerFactory.getLogger(PlanImageUploadAction.class);

    /**
     * 准备确定修改的实景图标志
     */
    public static final String PLAN_IMAGE_PATH_PRE = "p";

    //input
    /**
     * 区域id
     */
    private String zoneId;
    /**
     * 区域实景图
     */
    private File image;

    /**
     * 图片名称
     */
    private String imageFileName;

    /**
     * 文件在项目中在路径
     */
    private String imagePath;

    @Route("/blackhole/zone/planimage/upload.json")
    public String execute() {
        Map<String, Object> data = new HashMap<String, Object>();
        if (image != null) {
            try {
                imagePath = uploadImage(image);
            } catch (IOException e) {
                log.error("区域上传图片", e);
            }
        }
        data.put("imagePath", imagePath);
        data.put("imageFileName", imageFileName);
        ActionContext.getContext().put("data", data);
        return Results.json()
                .root("data")
                .contentType("text/html")
                .ignoreHierarchy(false)
                .done();
    }

    /**
     * 过去文件后缀
     *
     * @param fileName
     * @return
     */
    private String getFileFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * 区域实景图上传
     *
     * @param image
     */
    private String uploadImage(File image) throws IOException {

        String resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";
        String path = ResourcePaths.galaxyResourcesDir("blueplanet/images/zonePlanImage");
        imageFileName = zoneId + "." + getFileFormat(imageFileName);
        if (image != null) {

            File file = new File(path);

            if (!file.exists()) {
                file.mkdirs();
            }
            FileUtils.copyFile(image, new File(file, PLAN_IMAGE_PATH_PRE + imageFileName));
        }
        return resourcesPath + "/" + PLAN_IMAGE_PATH_PRE + imageFileName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }


    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
