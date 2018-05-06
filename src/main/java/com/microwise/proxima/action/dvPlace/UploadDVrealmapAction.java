package com.microwise.proxima.action.dvPlace;

import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.service.DVPlaceService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 上传摄像机实景图
 *
 * @author xu.baoji
 * @date 2013.08.22
 * @check @li.jianfei 2013.09.02 #5270
 */
@Beans.Action
@Proxima
public class UploadDVrealmapAction {
    public static final Logger log = LoggerFactory.getLogger(UploadDVrealmapAction.class);

    /**
     * 缺省的 实景图
     */
    public static final String DEFAULT_REALMAP_URL = "../proxima/images/defaultRealmap.gif";

    private String proximaResources = Sessions.createByAction().getGalaxyResourceURL() + "/proxima/images";

    @Autowired
    private DVPlaceService dvPlaceService;

    // in
    /**
     * 摄像机 id
     */
    private String dvId;

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

    // out
    /**
     * 摄像机名称
     */
    private String dvName;

    /**
     * 实景图url地址
     */
    private String realmapUrl;

    public String view() {
        try {

            DVPlaceBean dvPlaceBean = dvPlaceService.findById(dvId);
            dvName = dvPlaceBean.getPlaceName();
            String realmap = dvPlaceBean.getRealmap();
            if (realmap != null && !realmap.equals("")) {
                realmapUrl = proximaResources + "/realmap/" + realmap;
            } else {
                realmapUrl = DEFAULT_REALMAP_URL;
            }

        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public String upload() {
        try {
            // 获得 资源 目录
            String path = ResourcePaths.galaxyResourcesDir("proxima/images/realmap");

            // 实景图保存名
            String saveFileName = dvId + Constants.CONTENT_TYPE_MAP.get(imageContentType);
            // 创建要 保存的文件
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileUtils.copyFile(image, new File(file, saveFileName));
            // 保存数据库
            dvPlaceService.updateRealmap(dvId, saveFileName);
            realmapUrl = proximaResources + "/realmap/" + saveFileName;
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public String getDvName() {
        return dvName;
    }

    public void setDvName(String dvName) {
        this.dvName = dvName;
    }

    public String getRealmapUrl() {
        return realmapUrl;
    }

    public void setRealmapUrl(String realmapUrl) {
        this.realmapUrl = realmapUrl;
    }

    public String getDvId() {
        return dvId;
    }

    public void setDvId(String dvId) {
        this.dvId = dvId;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
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
