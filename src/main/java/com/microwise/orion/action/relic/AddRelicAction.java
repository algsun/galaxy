package com.microwise.orion.action.relic;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.QiniuUtil;
import com.microwise.orion.bean.Photo;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicProperty;
import com.microwise.orion.service.PhotoService;
import com.microwise.orion.service.RelicPropertyService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.orion.util.ResourcePaths;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 文物入藏
 *
 * @author Wang rensong
 * @time 13-5-21
 */
@Beans.Action
@Orion
public class AddRelicAction extends OrionLoggerAction {

    /**
     * 文物信息
     */
    @Autowired
    private RelicService relicService;

    /**
     * 照片信息 service
     */
    @Autowired
    private PhotoService photoService;

    /**
     * 文物属性信息
     */
    @Autowired
    private RelicPropertyService relicPropertyService;

    /**
     * 获得siteId 当前站点编号
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    // Input
    /**
     * 文物基本信息
     */
    private Relic relic;

    private List<RelicProperty> relicPropertyList;

    /**
     * 接受依赖注入的属性
     */
    private String savePath;

    /**
     * 上传文件域
     */
    private File image;

    /**
     * 上传文件名
     */
    private String imageFileName;
    /**
     * 上传文件类型
     */
    private String imageContentType;

    /**
     * 文物入藏
     *
     * @return
     */
    public String execute() {

        try {
            //1.文物基础属性保存

            relic.setState(Relic.STATE_IN);       //文物状态：0、在库 ;1、待出库；2、出库
            relic.setHasTag(false);      //是否有电子标签：0：没有 1： 有
            relic.setSiteId(siteId);
            if("".equals(relic.getTotalCode().trim())){
                relic.setTotalCode(null);
            }
            System.out.println(relic.getTotalCode());
            // zone 不为空，但 zone.id 为空，则将 zone 置为 null(否则数据库外键会出现意外)
            if (relic.getZone() != null && Strings.isNullOrEmpty(relic.getZone().getId())) {
                relic.setZone(null);
            }

            relicService.addRelic(relic);
            log("藏品管理", "添加藏品");

            //2.文物扩展属性保存
            // 如果扩展属性为空, 则去掉不添加
            for (Iterator<RelicProperty> it = relicPropertyList.iterator(); it.hasNext(); ) {
                RelicProperty relicProperty = it.next();
                if (Strings.isNullOrEmpty(relicProperty.getPropertyValue())) {
                    it.remove();
                    continue;
                }

                if (relicProperty.getPropertyValue().trim().isEmpty()) {
                    it.remove();
                }
            }

            for (RelicProperty pro : relicPropertyList) {
                pro.setRelic(relic);
            }
            relicPropertyService.saveOrUpdateRelicProperty(relicPropertyList);

            //3.文物图片保存
            //动态创建文件夹
            File imagePath = new File(ResourcePaths.imagesDirOfRelic(siteId, relic.getId()));
            if (!imagePath.exists()) {
                imagePath.mkdirs();
            }
            if (image != null) {
                int position = imageFileName.lastIndexOf(".");

                //设置新的照片名称,处理不同文件类型的文件名 ,文件名为：photo_+当前的毫秒数
                String newImageName = "photo_" + System.currentTimeMillis() + imageFileName.substring(position).toLowerCase();
                try {
                    Files.copy(image, new File(imagePath, newImageName));

                    //将相关信息添加到照片表中
                    Photo photo = new Photo();
                    photo.setPath(newImageName);
                    photo.setPhotoDate(new Date());
                    photo.setRelic(relic);
                    photoService.addPhoto(photo);

                    ConfigFactory.Configs config = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
                    boolean isUploadToCloud = Boolean.parseBoolean(config.get(Constants.Config.RESOURCE_IS_UPLOAD_TO_CLOUD));
                    if (isUploadToCloud) {
                        String pictureDirPath = imagePath.getAbsolutePath();
                        String absolutePath = pictureDirPath.substring(pictureDirPath.lastIndexOf("orion"));
                        QiniuUtil.upload(new File(imagePath, newImageName), absolutePath + File.separator + newImageName);
                    }
                } catch (IOException e) {
                    logFailed("复制文物失败", "文物入藏成功，但图片保存失败");
                    e.printStackTrace();
                    ActionMessage.createByAction().fail("文物入藏成功，但图片保存失败");
                    return Action.SUCCESS;
                }
            }


            ActionMessage.createByAction().success("文物入藏成功！");
        } catch (Exception e) {
            ActionMessage.createByAction().success("文物入藏失败！");
            logFailed("添加文物", "添加文物失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public RelicService getRelicService() {
        return relicService;
    }

    public void setRelicService(RelicService relicService) {
        this.relicService = relicService;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public List<RelicProperty> getRelicPropertyList() {
        return relicPropertyList;
    }

    public void setRelicPropertyList(List<RelicProperty> relicPropertyList) {
        this.relicPropertyList = relicPropertyList;
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

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getSavePath() {
        return ServletActionContext.getServletContext().getRealPath(savePath);
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

}

