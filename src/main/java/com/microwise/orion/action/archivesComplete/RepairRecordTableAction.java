package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.microwise.blackhole.bean.User;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 修复过程记录表
 *
 * @author 王耕
 * @date 15-10-9
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class RepairRecordTableAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(RepairRecordTableAction.class);

    @Autowired
    private RepairLogService repairLogService;
    @Autowired
    private SituationService situationService;
    @Autowired
    private RepairPhotoService repairPhotoService;
    @Autowired
    private RelicService relicService;
    @Autowired
    private UserService userService;
    @Autowired
    private RepairRecordService repairRecordService;


    //input
    /**
     * 日志实体
     */
    private RepairLog repairLog;
    private int relicId;
    private int repairRecordId;
    private Situation situation;
    private String content;
    private File repairRecordImage;
    private RepairPhoto repairPhoto;
    private int repairPhotoId;

    //output
    private List<RepairLog> repairLogs;
    private List<RepairPhoto> repairPhotos;
    private RepairRecord repairRecord;
    /**
     * 图片显示路径
     */
    private String picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + "/orion/images";
    private String siteId = Sessions.createByAction().currentSiteId();
    private String actionName = "repair-record";

    @Route(value = "saveRepairLog")
    public String saveRepairLog() {
        try {
            repairLogService.saveRepairLog(repairLog);
            ActionMessage.createByAction().success("保存修复记录日志成功");
            log("修复记录日志保存", "文物修复");
        } catch (Exception e) {
            ActionMessage.createByAction().success("保存修复记录日志失败");
            log.error("修复记录日志保存", e);
        }
        return Results.redirect("index?repairRecordId=" + repairLog.getRepairRecord().getId() + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    @Route(value = "saveRepairProcessRecord")
    public String saveRepairProcessRecord() {
        try {
            Situation dbSituation = situationService.findByRepairRecordId(repairRecordId);
            if (dbSituation == null) {
                dbSituation = situation;
            } else {
                dbSituation.setSummarize(content);
                dbSituation.setTechChange(situation.getTechChange());
            }
            situationService.saveOrUpdate(dbSituation);
            ActionMessage.createByAction().success("保存修复过程记录的综述与技术变更信息成功");
            log.info("保存修复过程记录的综述与技术变更信息成功");
        } catch (Exception e) {
            ActionMessage.createByAction().success("保存修复过程记录的综述与技术变更信息失败");
            log.error("保存修复过程记录的综述与技术变更信息", e);
        }
        return Results.redirect("index?repairRecordId=" + repairRecordId + "&relicId=" + relicId + "&actionName=" + actionName);
    }


    @Route(value = "repair-record.json")
    public String getJSON() {
        try {
            repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
            repairLogs = repairLogService.findRepairLogs(repairRecordId);
            situation = situationService.findByRepairRecordId(repairRecordId);
            for (RepairLog log : repairLogs) {
                log.setRepairRecord(null);
                if (!Strings.isNullOrEmpty(log.getRepairPerson())) {
                    String[] userIds = log.getRepairPerson().split(",");
                    List<String> userNames = new ArrayList<String>();
                    for (String id : userIds) {
                        int userId = Integer.parseInt(id.trim());
                        User users = userService.findUserById(userId);
                        userNames.add(users.getUserName());
                    }
                    log.setRepairPersonName(userNames);
                }
            }
            repairPhotos = repairPhotoService.findByRepairRecordId(repairRecordId, 2);
            Relic relic = relicService.findRelicByRelicId(relicId, siteId);
            RepairRecord rr = new RepairRecord();
            rr.setRelic(relic);
            situation.setRepairRecord(rr);
            for (RepairPhoto p : repairPhotos) {
                p.setRepairRecord(null);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate4Module());
            Map<String, Object> data = Maps.newHashMap();
            data.put("actionName", actionName);
            data.put("repairPhotos", repairPhotos);
            data.put("repairLogs", repairLogs);
            data.put("situation", situation);
            data.put("repairRecordId", repairRecordId);
            data.put("picturesBasePath", picturesBasePath);
            data.put("repairRecord", repairRecord);
            data.put("siteId", siteId);
            String jsonString = objectMapper.writeValueAsString(data);

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF8");
            response.getWriter().print(jsonString);
            log("修复过程记录表", "文物修复");
        } catch (Exception e) {
            log.error("修复过程记录表", e);
        }
        /*//TODO 不知道 “” 是什么意思
        return Results.json().excludeProperties("").done();*/
        return null;
    }

    @Route(value = "uploadRepairRecordImage")
    public String uploadRepairRecordImage() {

        //TODO /换成 File.separator
        String path = UpLoadFileUtil.getUploadPath("/orion") + "/images/" + siteId + "/" + relicId;
        long currentStr = System.currentTimeMillis();

        //TODO上传什么名字就存什么名字
        String photoName = "repair_record_" + currentStr + ".jpg";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String newPath = path + "/" + photoName;
        File destImageFile = new File(newPath);
        String isUpload = UpLoadFileUtil.fileUpload(repairRecordImage, destImageFile);
        if ("true".equals(isUpload)) {
            addPhotoToDB(photoName, repairPhoto);
            ActionMessage.createByAction().success("照片上传成功");
            log("藏品信息", "图片名称：" + photoName);

        }
        return Results.redirect("index?repairRecordId=" + repairPhoto.getRepairRecord().getId() + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    private void addPhotoToDB(String photoName, RepairPhoto repairPhoto) {
        try {
            repairPhoto.setPath(photoName);
            repairPhoto.setStamp(new Date());
            //TODO 改常量
            repairPhoto.setType(2);
            repairPhotoService.saveRepairPhoto(repairPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Route(value = "deleteRepairRecordPhoto")
    public String deleteRepairRecordPhoto() {
        try {
            //TODO siteId 抽取变量
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

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public RepairLog getRepairLog() {
        return repairLog;
    }

    public void setRepairLog(RepairLog repairLog) {
        this.repairLog = repairLog;
    }

    public int getRepairRecordId() {
        return repairRecordId;
    }

    public void setRepairRecordId(int repairRecordId) {
        this.repairRecordId = repairRecordId;
    }

    public List<RepairLog> getRepairLogs() {
        return repairLogs;
    }

    public void setRepairLogs(List<RepairLog> repairLogs) {
        this.repairLogs = repairLogs;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public File getRepairRecordImage() {
        return repairRecordImage;
    }

    public void setRepairRecordImage(File repairRecordImage) {
        this.repairRecordImage = repairRecordImage;
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

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getRepairPhotoId() {
        return repairPhotoId;
    }

    public void setRepairPhotoId(int repairPhotoId) {
        this.repairPhotoId = repairPhotoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }
}
