package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有表浏览
 *
 * @author 王耕
 * @date 15-10-19
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class AllTableBrowseAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(AllTableBrowseAction.class);

    @Autowired
    private RepairRecordService repairRecordService;
    @Autowired
    private RelicService relicService;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private InstitutionRoomService institutionRoomService;
    @Autowired
    private UserService userService;
    @Autowired
    private SituationService situationService;
    @Autowired
    private StatusQuoService statusQuoService;
    @Autowired
    private DetectionAnalysisService detectionAnalysisService;
    @Autowired
    private RepairLogService repairLogService;
    @Autowired
    private RepairPhotoService repairPhotoService;

    @Autowired
    private DrawRegisterService registerService;

    @Autowired
    private ImageDatumService imageDatumService;

    @Autowired
    private RepairAssessmentService repairAssessmentService;

    //input
    /**
     * 文物修复记录ID
     */
    private int repairRecordId;
    /**
     * 文物ID
     */
    private int relicId;

    //output
    /**
     * 表名标记
     */
    private String actionName;
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;
    /**
     * 材质
     */
    private String textureEnName;
    /**
     * 收藏单位
     */
    private Institution institution;

    private int institutionId;
    /**
     * 收藏库房
     */
    private InstitutionRoom institutionRoom;

    private int institutionRoomId;
    /**
     * 保存现状
     */
    private StatusQuo statusQuo;
    /**
     * 文物检测分析
     */
    private List<DetectionAnalysis> detectionAnalysises;
    /**
     * 修复记录日志
     */
    private List<RepairLog> repairLogs;
    /**
     *
     */
    private List<RepairPhoto> repairPhotos;
    /**
     * 用户
     */
    private List<User> users;
    private String persons;
    private int userId;
    private String siteId;
    private String picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + "/orion/images";
    private List<DrawRegister> drawRegisterList;
    private List<ImageDatum> imageDatums;

    private RepairAssessment repairAssessment;

    @Route(value = "allTableBrowse")
    public String allTableBrowse() {
        siteId = Sessions.createByAction().currentSiteId();
        repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
        String[] secondaryUserIds = repairRecord.getSecondaryUserId().split(",");
        List<String> secondaryUserName = new ArrayList<>();
        for (String id : secondaryUserIds) {
            int userId = Integer.parseInt(id.trim());
            User user = userService.findUserById(userId);
            secondaryUserName.add(user.getUserName());
        }
        repairRecord.setSecondaryUserName(secondaryUserName);
        Situation situation = situationService.findByRepairRecordId(repairRecordId);
        Relic relic = relicService.findRelicByRelicId(repairRecord.getRelic().getId(), siteId);
        relicId = relic.getId();
        textureEnName = relic.getTexture().getEnName();
        institutionId = repairRecord.getInstitution().getId();
        institution = institutionService.findById(institutionId);
        if (relic.getRelicPropertyMap().get("storehouse") != null) {
            institutionRoomId = Integer.parseInt(relic.getRelicPropertyMap().get("storehouse").getPropertyValue());
        }
        institutionRoom = institutionRoomService.findById(institutionRoomId);
        repairRecord.setRelic(relic);
        users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
        repairRecord.setSituation(situation);
        repairLogs = repairLogService.findRepairLogs(repairRecordId);
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
        userId = Sessions.createByAction().currentUser().getId();
        statusQuo = statusQuoService.findStatusQuos(relic.getId());
        if (statusQuo != null) {
            statusQuo.setRelic(relic);
        }
        detectionAnalysises = detectionAnalysisService.findByRepairRecordId(repairRecordId);
        repairPhotos = repairPhotoService.findByRepairRecordId(repairRecordId, RepairPhoto.REPAIR_PHOTO);
        String mainUser = repairRecord.getMainUser().getUserName();
        String[] ids = repairRecord.getSecondaryUserId().split(",");
        StringBuffer sb = new StringBuffer(mainUser);
        for (String userId : ids) {
            int id = Integer.parseInt(userId.trim());
            String name = userService.findUserById(id).getUserName();
            sb.append("," + name);
        }
        persons = sb.toString();

        drawRegisterList = registerService.findDrawRegisters(repairRecordId);
        imageDatums = imageDatumService.findImageDatums(repairRecordId);
        repairAssessment = repairAssessmentService.findByRepairRecordId(repairRecordId);
        actionName = "all-tables";
        return Results.ftl("orion/pages/archivesComplete/archives-complete.ftl");
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

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }

    public String getTextureEnName() {
        return textureEnName;
    }

    public void setTextureEnName(String textureEnName) {
        this.textureEnName = textureEnName;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public InstitutionRoom getInstitutionRoom() {
        return institutionRoom;
    }

    public void setInstitutionRoom(InstitutionRoom institutionRoom) {
        this.institutionRoom = institutionRoom;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public StatusQuo getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(StatusQuo statusQuo) {
        this.statusQuo = statusQuo;
    }

    public List<DetectionAnalysis> getDetectionAnalysises() {
        return detectionAnalysises;
    }

    public void setDetectionAnalysises(List<DetectionAnalysis> detectionAnalysises) {
        this.detectionAnalysises = detectionAnalysises;
    }

    public List<RepairLog> getRepairLogs() {
        return repairLogs;
    }

    public void setRepairLogs(List<RepairLog> repairLogs) {
        this.repairLogs = repairLogs;
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

    public RepairAssessment getRepairAssessment() {
        return repairAssessment;
    }

    public void setRepairAssessment(RepairAssessment repairAssessment) {
        this.repairAssessment = repairAssessment;
    }

    public List<ImageDatum> getImageDatums() {
        return imageDatums;
    }

    public void setImageDatums(List<ImageDatum> imageDatums) {
        this.imageDatums = imageDatums;
    }

    public List<DrawRegister> getDrawRegisterList() {
        return drawRegisterList;
    }

    public void setDrawRegisterList(List<DrawRegister> drawRegisterList) {
        this.drawRegisterList = drawRegisterList;
    }

    public List<RepairPhoto> getRepairPhotos() {
        return repairPhotos;
    }

    public void setRepairPhotos(List<RepairPhoto> repairPhotos) {
        this.repairPhotos = repairPhotos;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public int getInstitutionRoomId() {
        return institutionRoomId;
    }

    public void setInstitutionRoomId(int institutionRoomId) {
        this.institutionRoomId = institutionRoomId;
    }
}
