package com.microwise.blackhole.action.post;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
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
public class UploadMediaAction implements ServletResponseAware {
    private static final Logger log = LoggerFactory.getLogger(UploadMediaAction.class);

    /**
     * 封装上传文件域的属性
     */
    private File media;

    /**
     * 封装上传文件类型的属性
     */
    private String mediaContentType;

    /**
     * 封装上传文件名的属性
     */
    private String mediaFileName;

    /**
     * 文件在项目中在路径
     */
    private String filePath;

    /**
     * 注入response
     */
    private HttpServletResponse response = null;

    @Route("/blackhole/post/uploadMedia")
    public String upload_img() {
        Map<String, Object> data = null;
        try {
            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/medias" + File.separator + "post";
            String path = ResourcePaths.galaxyResourcesDir("blackhole" + File.separator + "medias" + File.separator + "post");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            mediaFileName = Md5.getMd5("" + new Date()) + CONTENT_TYPE_MAP.get(getMediaContentType());
            FileUtils.copyFile(media, new File(file, mediaFileName));
            //发送给 KE
            data = new HashMap<String, Object>();
            data.put("error", 0);
            data.put("url", filePath + File.separator + mediaFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.json().asRoot(data).done();
    }


    /**
     * 文件类型常量
     */
    @SuppressWarnings("serial")
    public static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>() {
        {
            put("video/x-ms-wmv", ".wmv");
            put("video/x-ms-wma", ".wma");
            put("application/x-shockwave-flash", ".swf");
        }
    };

    public File getMedia() {
        return media;
    }

    public void setMedia(File media) {
        this.media = media;
    }

    public String getMediaContentType() {
        return mediaContentType;
    }

    public void setMediaContentType(String mediaContentType) {
        this.mediaContentType = mediaContentType;
    }

    public String getMediaFileName() {
        return mediaFileName;
    }

    public void setMediaFileName(String mediaFileName) {
        this.mediaFileName = mediaFileName;
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
