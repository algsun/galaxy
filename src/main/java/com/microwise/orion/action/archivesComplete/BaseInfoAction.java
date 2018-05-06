package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 基础信息表
 *
 * @author 王耕
 * @date 15-9-21
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class BaseInfoAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(BaseInfoAction.class);

    @Autowired
    private RelicService relicService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;
    @Autowired
    private RelicPropertyService relicPropertyService;
    @Autowired
    private RepairRecordService repairRecordService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private SituationService situationService;

    //input
    /**
     * 修复记录ID
     */
    private int repairRecordId;
    /**
     * 文物id
     */
    private int relicId;
    /**
     * 上传的图片文件
     */
    private File fileImage;
    /**
     * 修复信息保存
     */
    private Situation situation;
    /**
     * 图片id
     */
    private int photoId;

    private String siteId = Sessions.createByAction().currentSiteId();

    //基础信息保存相关属性
    List<RelicProperty> relicProperties;

    //output
    /**
     * 图片显示路径
     */
    private String picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + "/orion/images";
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;
    /**
     * 表类型标记
     */
    private String actionName;

    @Route(value = "base-info.json")
    public String getJSON() {
        try {
            repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
            if (repairRecord == null) {
                return Results.json().asRoot(null).done();
            }

            Relic relic = relicService.findRelicByRelicId(repairRecord.getRelic().getId(), siteId);

            String textureEnName = relic.getTexture().getEnName();
            //根据文物修复的材质的不同划分为四种不同的基础信息表

            //TODO ftl页面做判断
            if ("paper".equals(textureEnName) || "metal".equals(textureEnName) || "ceramicPain".equals(textureEnName)) {
                actionName = "base-info-typea";
            } else if ("painting".equals(textureEnName)) {
                actionName = "base-info-typeb";
            } else if ("spinning".equals(textureEnName) || "fabrics".equals(textureEnName)) {
                actionName = "base-info-typec";
            } else if ("mural".equals(textureEnName)) {
                actionName = "base-info-typed";
            }

            repairRecord.setRelic(relic);
            repairRecord.setDrawRegisters(null);
            log("基础信息表数据获取", "文物修复");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate4Module());
            Map<String, Object> data = Maps.newHashMap();
            data.put("actionName", actionName);
            data.put("repairRecord", repairRecord);
            data.put("picturesBasePath", picturesBasePath);
            String jsonString = objectMapper.writeValueAsString(data);

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF8");
            response.getWriter().print(jsonString);
        } catch (Exception e) {
            log.error("基础信息表数据获取", e);
        }
        return null;
    }

    @Route(value = "saveBaseInfo")
    public String saveBaseInfo() {
        try {
            for (RelicProperty relicProperty : relicProperties) {
                Property property = propertyService.findByEnName(relicProperty.getProperty().getEnName());
                RelicProperty rp = relicPropertyService.findByRelicIdAndPropertyId(relicId, property.getId());
                if (rp != null) {
                    relicProperty.setId(rp.getId());
                    relicProperty.setProperty(rp.getProperty());
                } else {
                    relicProperty.setProperty(property);
                }
            }
            relicPropertyService.saveOrUpdateRelicProperty(relicProperties);

            Situation situationDb = situationService.findByRepairRecordId(repairRecordId);
            if (situationDb != null) {
                situation.setId(situationDb.getId());
            }
            situationService.saveOrUpdate(situation);

            ActionMessage.createByAction().success("保存基础信息成功");
            log("保存基础信息表", "文物修复");
        } catch (Exception e) {
            ActionMessage.createByAction().success("保存基础信息失败");
            log.error("保存基础信息表", e);
        }
        return Results.redirect("index?repairRecordId=" + repairRecordId + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    @Route(value = "repairPerson.json")
    public String repairPerson() {
        StringBuffer sb = new StringBuffer("");
        try {
            RepairRecord repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
            String mainUserName = repairRecord.getMainUser().getUserName();
            if (Strings.isNullOrEmpty(mainUserName)) {
                Results.json().asRoot("").done();
            }
            String userIds[] = repairRecord.getSecondaryUserId().split(",");
            sb.append(mainUserName);
            if (userIds.length > 0) {
                for (String id : userIds) {
                    int userId = Integer.parseInt(id.trim());
                    sb.append(",");
                    sb.append(userService.findUserById(userId).getUserName());
                }
            }
            log("负责人获取", "文物修复");
        } catch (Exception e) {
            log.error("负责人获取", e);
        }
        return Results.json().asRoot(sb.toString()).done();
    }

    @Route(value = "repairedSizeAndWeight.json")
    public String repairedSizeAndWeight() {
        Situation situation = null;
        try {
            situation = situationService.findByRepairRecordId(repairRecordId);
            if (situation == null) {
                situation = new Situation();

                int relicId = repairRecordService.findRepairRecordById(repairRecordId).getRelic().getId();
                RelicProperty weightRelicProperty = relicPropertyService.findByRelicIdAndPropertyId(relicId, 1);
                RelicProperty sizeRelicProperty = relicPropertyService.findByRelicIdAndPropertyId(relicId, 2);
                if (weightRelicProperty != null) {
                    situation.setWeight(weightRelicProperty.getPropertyValue());
                }
                if (sizeRelicProperty != null) {
                    situation.setSize(sizeRelicProperty.getPropertyValue());
                }

                RepairRecord repairRecord = new RepairRecord();
                repairRecord.setId(repairRecordId);
                situation.setRepairRecord(repairRecord);
                situationService.saveOrUpdate(situation);
            }
            situation.setRepairRecord(null);
            log("尺寸重量保存", "文物修复");
        } catch (Exception e) {
            log.error("尺寸重量保存", e);
        }
        return Results.json().asRoot(situation).done();
    }

    @Route(value = "deletePhoto")
    public String deletePhoto() {
        try {
            //删除数据库的图片数据
            String subdirectory = File.separator + "images" + File.separator + Sessions.createByAction().currentSiteId() + File.separator + relicId;
            String fileName = "";
            if (photoId != 0) {
                Photo p = photoService.findById(photoId);
                fileName = p.getPath();
                photoService.delete(p);
                UpLoadFileUtil.deleteFileByFileName(fileName, subdirectory, "/orion");
            }
            //删除服务器上的图片数据
            log("删除文物图片", "文物修复");
        } catch (Exception e) {
            log.error("删除文物图片", e);
        }
        return Results.redirect("index?repairRecordId=" + repairRecordId + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    @Route(value = "uploadPhoto")
    public String uploadPhotoAction() {
        //TODO "/"换成File.separator
        String path = UpLoadFileUtil.getUploadPath(File.separator + "orion") + File.separator + "images" + File.separator + siteId + File.separator + relicId;

        //TODO 上传什么名字就存什么名字
        long currentStr = System.currentTimeMillis();
        String photoName = "photo_" + currentStr + ".jpg";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String newPath = path + File.separator + photoName;
        File destImageFile = new File(newPath);
        String isUpload = UpLoadFileUtil.fileUpload(fileImage, destImageFile);
        if ("true".equals(isUpload)) {
            addPhotoToDB(photoName, relicId);
            ActionMessage.createByAction().success("照片上传成功");
            log("藏品信息", "图片名称：" + photoName);

        }
        return Results.redirect("index?repairRecordId=" + repairRecordId + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    /**
     * 添加图片信息到数据库
     *
     * @param photoName 图片名称
     */
    private void addPhotoToDB(String photoName, int relicId) {
        try {
            Photo p = new Photo();
            p.setPath(photoName);
            p.setPhotoDate(DateTime.now().withTimeAtStartOfDay().toDate());
            Relic relic = new Relic();
            relic.setId(relicId);
            p.setRelic(relic);
            photoService.addPhoto(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRepairRecordId() {
        return repairRecordId;
    }

    public void setRepairRecordId(int repairRecordId) {
        this.repairRecordId = repairRecordId;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public File getFileImage() {
        return fileImage;
    }

    public void setFileImage(File fileImage) {
        this.fileImage = fileImage;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<RelicProperty> getRelicProperties() {
        return relicProperties;
    }

    public void setRelicProperties(List<RelicProperty> relicProperties) {
        this.relicProperties = relicProperties;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
