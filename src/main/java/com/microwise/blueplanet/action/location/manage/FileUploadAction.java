package com.microwise.blueplanet.action.location.manage;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.Md5;
import com.opensymphony.xwork2.Action;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

/**
 * 文件上传
 *
 * @author liuzhu
 * @date 2015-9-25
 */

@Beans.Action
@Blueplanet
public class FileUploadAction extends BlueplanetLoggerAction {


    public static final Logger logger = LoggerFactory.getLogger(EditLocationAction.class);

    @Autowired
    private LocationService locationService;

    /**
     * 封装上传文件域的属性
     */
    private File image;

    /**
     * 封装上传文件类型的属性
     */
    private String imageContentType;

    /**
     * 封装上传文件名的属性
     */
    private String imageFileName;

    /**
     * 文件在项目中在路径
     */
    private String filePath;

    /**
     * 位置点id
     */
    private String locationId;

    public String execute() {
        try {
            //获取资源路径
            String path = ResourcePaths.galaxyResourcesDir("blueplanet" + File.separator + "images" + File.separator + "location");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            imageFileName = Md5.getMd5("" + new Date()) + Constants.CONTENT_TYPE_MAP.get(getImageContentType());
            FileUtils.copyFile(image, new File(file, imageFileName));
            filePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "blueplanet" + File.separator + "images" + File.separator + "location";
            if (!Strings.isNullOrEmpty(locationId)) {
                LocationVO locationVO = locationService.findLocationById(locationId);
                locationVO.setPhoto(imageFileName);
                locationService.updateLocation(locationVO);
            }
        } catch (Exception e) {
            logger.error("文物实景图上传失败", e);
        }
        return Action.SUCCESS;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
