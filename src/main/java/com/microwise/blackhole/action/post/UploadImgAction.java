package com.microwise.blackhole.action.post;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.Md5;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyuexi
 * @date 14-6-9
 * 图片上传
 */

@Beans.Action
@Blackhole
public class UploadImgAction implements ServletResponseAware {
    private static final Logger log = LoggerFactory.getLogger(UploadImgAction.class);

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
     * 注入response
     */
    private HttpServletResponse response = null;

    @Route("/blackhole/post/uploadImage")
    public String upload_img() {

        Map<String, Object> data = null;

        try {
            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images" + File.separator + "post";
            String path = ResourcePaths.galaxyResourcesDir("blackhole" + File.separator + "images" + File.separator + "post");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            imageFileName = Md5.getMd5("" + new Date()) + Constants.CONTENT_TYPE_MAP.get(getImageContentType());
            FileUtils.copyFile(image, new File(file, imageFileName));
            //发送给 KE
            data = new HashMap<String, Object>();
            data.put("error", 0);
            data.put("url", filePath + File.separator + imageFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.json().asRoot(data).done();
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}
