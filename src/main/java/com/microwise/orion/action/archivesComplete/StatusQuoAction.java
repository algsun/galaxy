package com.microwise.orion.action.archivesComplete;

//TODO 删除没有用的包

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.google.common.collect.Maps;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RepairPhoto;
import com.microwise.orion.bean.StatusQuo;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.service.RepairPhotoService;
import com.microwise.orion.service.StatusQuoService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文物保存现状表
 *
 * @author 王耕
 * @date 15-9-28
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class StatusQuoAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(StatusQuoAction.class);

    @Autowired
    private StatusQuoService statusQuoService;
    @Autowired
    private RelicService relicService;
    @Autowired
    private RepairPhotoService repairPhotoService;

    //input
    /**
     * 文物id
     */
    private int relicId;

    /**
     * 修复记录ID
     */
    private int repairRecordId;

    /**
     * 现状图片上传
     */
    private File statusQuoImage;
    /**
     * 删除图片id
     */
    private int repairPhotoId;
    /**
     * 现状图片上传
     */
    private RepairPhoto repairPhoto;

    //output
    /**
     * 图片显示路径
     */
    //TODO File.separator
    private String picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + "/orion/images";
    /**
     * 保存现状
     */
    private StatusQuo statusQuo;

    private List<RepairPhoto> repairPhotos;

    /**
     * 用来切换表格的标记名称
     */
    private String actionName = "keep-status";

    @Route(value = "keep-status.json")
    public String getJSON() {
        try {

            String siteId = Sessions.createByAction().currentSiteId();
            statusQuo = statusQuoService.findStatusQuos(relicId);
            Relic relic = relicService.findRelicByRelicId(relicId, siteId);

            //TODO 改常量
            repairPhotos = repairPhotoService.findByRepairRecordId(repairRecordId, 1);
            for (RepairPhoto p : repairPhotos) {
                p.setRepairRecord(null);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate4Module());
            Map<String, Object> data = Maps.newHashMap();
            data.put("relicId",relicId);
            data.put("repairRecordId",repairRecordId);
            data.put("actionName", actionName);
            data.put("repairPhotos", repairPhotos);
            data.put("statusQuo", statusQuo);
            data.put("relic", relic);
            data.put("picturesBasePath", picturesBasePath);
            String jsonString = objectMapper.writeValueAsString(data);

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF8");
            response.getWriter().print(jsonString);
            log("保存现状获取", "文物修复");
        } catch (Exception e) {
            log.error("文物修复,保存现状获取", e);
        }
        /*return Results.json().excludeProperties("statusQuo.relic.zone.site").done();*/
        return null;
    }

    @Route(value = "saveStatusQuo")
    public String saveStatusQuo() {
        try {
            statusQuo.setQuoDate(new Date());
            statusQuoService.saveOrUpdateStatusQuo(statusQuo);
            ActionMessage.createByAction().success("保存文物现状成功");
            log("保存文物现状", "文物修复");
        } catch (Exception e) {
            ActionMessage.createByAction().success("保存文物现状失败");
            log.error("文物修复,保存文物现状", e);
        }
        return Results.redirect("index?repairRecordId=" + repairRecordId + "&relicId=" + statusQuo.getRelic().getId() + "&actionName=" + actionName);
    }

    @Route(value = "uploadStatusQuoImage")
    public String uploadStatusQuoImage() {
        if (statusQuo.getId() == 0) {
            return Results.redirect("index?repairRecordId=" + repairPhoto.getRepairRecord().getId() + "&relicId=" + relicId + "&actionName=" + actionName);
        }
        String siteId = Sessions.createByAction().currentSiteId();
        //TODO File.separator
        String path = UpLoadFileUtil.getUploadPath("/orion") + File.separator + "images" + File.separator + siteId + File.separator + relicId;

        //上传什么名字，改什么名字
        long currentStr = System.currentTimeMillis();
        String photoName = "status_quo_" + currentStr + ".jpg";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        //TODO File.separator
        String newPath = path + File.separator + photoName;
        File destImageFile = new File(newPath);
        String isUpload = UpLoadFileUtil.fileUpload(statusQuoImage, destImageFile);
        if ("true".equals(isUpload)) {
            addPhotoToDB(photoName, repairPhoto);
            ActionMessage.createByAction().success("照片上传成功");
            log("藏品信息", "图片名称：" + photoName);

        }
        return Results.redirect("index?repairRecordId=" + repairPhoto.getRepairRecord().getId() + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    /**
     * 添加图片信息到数据库
     */
    private void addPhotoToDB(String photoName, RepairPhoto repairPhoto) {
        try {
            repairPhoto.setPath(photoName);
            repairPhoto.setStamp(new Date());
            //TODO 改常量
            repairPhoto.setType(1);
            repairPhotoService.saveRepairPhoto(repairPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Route(value = "deleteStatusQuoPhoto")
    public String deleteStatusQuoPhoto() {
        try {
            String subdirectory = File.separator + "images" + File.separator + Sessions.createByAction().currentSiteId() + File.separator + relicId;
            String photoName = repairPhotoService.findById(repairPhotoId).getPath();
            repairPhotoService.deleteById(repairPhotoId);
            //TODO File.separator
            UpLoadFileUtil.deleteFileByFileName(photoName, subdirectory, "/orion");
            log("删除文物现状图片", "文物修复");
        } catch (Exception e) {
            log.error("删除文物现状图片", e);
        }
        return Results.redirect("index?repairRecordId=" + repairRecordId + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public StatusQuo getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(StatusQuo statusQuo) {
        this.statusQuo = statusQuo;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getRepairRecordId() {
        return repairRecordId;
    }

    public void setRepairRecordId(int repairRecordId) {
        this.repairRecordId = repairRecordId;
    }

    public File getStatusQuoImage() {
        return statusQuoImage;
    }

    public void setStatusQuoImage(File statusQuoImage) {
        this.statusQuoImage = statusQuoImage;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public int getRepairPhotoId() {
        return repairPhotoId;
    }

    public void setRepairPhotoId(int repairPhotoId) {
        this.repairPhotoId = repairPhotoId;
    }

    public RepairPhoto getRepairPhoto() {
        return repairPhoto;
    }

    public void setRepairPhoto(RepairPhoto repairPhoto) {
        this.repairPhoto = repairPhoto;
    }

    public List<RepairPhoto> getRepairPhotos() {
        return repairPhotos;
    }

    public void setRepairPhotos(List<RepairPhoto> repairPhotos) {
        this.repairPhotos = repairPhotos;
    }
}
