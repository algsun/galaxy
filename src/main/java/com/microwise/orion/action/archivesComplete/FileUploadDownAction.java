package com.microwise.orion.action.archivesComplete;

import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.DrawRegister;
import com.microwise.orion.bean.ImageDatum;
import com.microwise.orion.service.DrawRegisterService;
import com.microwise.orion.service.ImageDatumService;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 文件上传
 *
 * @author liuzhu
 * @date 2015-9-25
 */

@Beans.Action
@Orion
public class FileUploadDownAction {

    /**
     * 用户信息 service
     */
    @Autowired
    private DrawRegisterService drawRegisterService;

    @Autowired
    private ImageDatumService imageDatumService;

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
     * 接受依赖注入的属性
     */
    private String savePath;

    /**
     * 文件在项目中在路径
     */
    private String filePath;

    private int id;

    private String fileName;

    public String execute() {
        try {
            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/orion/images/archives";
            //获取资源路径
            String path = ResourcePaths.galaxyResourcesDir("orion/images/archives");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileUtils.copyFile(image, new File(file, imageFileName));

            DrawRegister draw = drawRegisterService.findById(id);
            draw.setImgPath(imageFileName);
            drawRegisterService.update(draw);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }


    public String imageDatum() {
        try {

            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/orion/images/archives";
            //获取资源路径
            String path = ResourcePaths.galaxyResourcesDir("orion/images/archives");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileUtils.copyFile(image, new File(file, imageFileName));

            ImageDatum imageDatum = imageDatumService.findById(id);
            imageDatum.setFilePath(imageFileName);
            imageDatumService.update(imageDatum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    //TODO 合并为一个下载
    public void downloadFileAction() {
        try {
            filePath = "/images/archives";
            UpLoadFileUtil.fileDownLoad(filePath, fileName, "/orion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imageDatumDownloadFileAction() {
        try {
            filePath = "/images/archives";
            UpLoadFileUtil.fileDownLoad(filePath, fileName, "/orion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
