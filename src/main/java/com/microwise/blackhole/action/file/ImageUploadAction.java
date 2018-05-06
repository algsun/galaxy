package com.microwise.blackhole.action.file;

import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.LoggingAction;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.Md5;
import com.opensymphony.xwork2.Action;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 上传用户照片
 *
 * @author xu.yuexi
 * @date 2013-11-25
 */

@Beans.Action
@Blackhole
public class ImageUploadAction extends LoggingAction {


    /**
     * 用户信息 service
     */
    @Autowired
    private LogicGroupService logicGroupService;
    //input
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

    /**
     * 文件类型1：标题图片，2背景图片
     */
    private int flag;

    public String execute() {
        try {
            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/logicGroup";
            //拿到当前logicGroupId
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            //获取资源路径
            String path = ResourcePaths.galaxyResourcesDir("blackhole/images/logicGroup");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            //如果 flag=1表示更改的是标题图片，更新数据库中的标题图片，把标题图片是否自定义改为1024
            if (flag == 1) {
                // 处理不同文件文件名
                imageFileName = Md5.getMd5("" + logicGroupId) + Constants.CONTENT_TYPE_MAP.get(getImageContentType());
                logicGroupService.updateLogicGroupTitleImage(imageFileName, logicGroupId);
                logicGroupService.updateLogicGroupUseTitle(1024, logicGroupId);
                //如果 flag=0表示更改的是背景图片，更新数据库中的背景图片，把背景图片是否自定义改为1024
            } else if (flag == 0) {
                // 处理不同文件文件名
                imageFileName = Md5.getMd5("" + logicGroupId + "bg") + Constants.CONTENT_TYPE_MAP.get(getImageContentType());
                logicGroupService.updateLogicGroupBgImage(imageFileName, logicGroupId);
                logicGroupService.updateLogicGroupUseBg(1024, logicGroupId);
            }
            FileUtils.copyFile(image, new File(file, imageFileName));
            ActionMessage.createByAction().success("文件上传成功");
            log("换肤", "站点换肤");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("文件上传失败");
            logFailed("换肤", "站点换肤");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 返回上传文件的保存位置
     *
     * @return
     */
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
