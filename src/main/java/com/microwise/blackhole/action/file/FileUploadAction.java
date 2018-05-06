package com.microwise.blackhole.action.file;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.LoggingAction;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.Md5;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 上传用户照片
 *
 * @author li.jianfei
 * @date 2012-11-20
 * @check @gaohui #429 12-11-29 16:47
 */

@Beans.Action
@Blackhole
public class FileUploadAction extends LoggingAction {


    /**
     * 用户信息 service
     */
    @Autowired
    private UserService userService;

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

    public String execute() {
        try {
            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/user";
            // 从 session 取出用户信息
            Sessions sessions = new Sessions(ActionContext.getContext());
            User user = sessions.currentUser();

            //获取资源路径
            String path = ResourcePaths.galaxyResourcesDir("blackhole/images/user");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            // 处理不同文件类型的文件名
            imageFileName = Md5.getMd5("" + user.getId()) + Constants.CONTENT_TYPE_MAP.get(getImageContentType());
            FileUtils.copyFile(image, new File(file, imageFileName));

            userService.updateUserPhotoById(user.getId(), imageFileName);

            // 更新 session 中的用户信息
            user = userService.findUserById(user.getId());
            sessions.setCurrentUser(user);

            log("个人信息", "上传照片");

        } catch (Exception e) {
            logFailed("个人信息", "上传照片");
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

}
