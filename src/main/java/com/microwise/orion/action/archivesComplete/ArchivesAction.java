package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RepairAssessment;
import com.microwise.orion.bean.RepairRecord;
import com.microwise.orion.bean.Situation;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.service.RepairAssessmentService;
import com.microwise.orion.service.RepairRecordService;
import com.microwise.orion.service.SituationService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * 档案填写-档案列表
 *
 * @author 王耕
 * @date 15-9-14
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class ArchivesAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(ArchivesAction.class);

    @Autowired
    private RepairRecordService repairRecordService;

    @Autowired
    private RelicService relicService;
    @Autowired
    private UserService userService;
    @Autowired
    private SituationService situationService;
    @Autowired
    private RepairAssessmentService repairAssessmentService;

    //input
    /**
     * 修复记录ID
     */
    private int repairRecordId;

    /**
     * 文物ID
     */
    private int relicId;

    //output
    /**
     * 单位ID
     */
    private int institutionId;

    /**
     * 收藏单位id
     */
    private int institutionRoomId;

    /**
     * 文物修复材质
     */
    private String textureEnName;

    /**
     * 用户
     */
    private List<User> users;

    /**
     * 默认进入基础信息表
     */
    private String actionName;
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;
    /**
     * 用户id
     */
    private int userId;

    private RepairAssessment repairAssessment;

    @Route("index")
    public String execute() {
        try {
            ActionMessage.createByAction().consume();
            Situation dbSituation = situationService.findByRepairRecordId(repairRecordId);
            repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
            String[] secondaryUserIds = repairRecord.getSecondaryUserId().split(",");
            List<String> secondaryUserName = new ArrayList<>();
            for (String id : secondaryUserIds) {
                int userId = Integer.parseInt(id.trim());
                User user = userService.findUserById(userId);
                secondaryUserName.add(user.getUserName());
            }
            repairRecord.setSecondaryUserName(secondaryUserName);
            repairRecord.setSituation(dbSituation);
            Relic relic = relicService.findRelicByRelicId(repairRecord.getRelic().getId(), Sessions.createByAction().currentSiteId());
            repairRecord.setRelic(relic);
            institutionId = repairRecord.getInstitution().getId();
            if(relic.getRelicPropertyMap().get("storehouse") !=null) {
                institutionRoomId = Integer.parseInt(relic.getRelicPropertyMap().get("storehouse").getPropertyValue());
            }
            textureEnName = relic.getTexture().getEnName();
            userId = Sessions.createByAction().currentUser().getId();
            //根据文物修复的材质的不同划分为四种不同的基础信息表
            //TODO ftl页面判断
            if(Strings.isNullOrEmpty(actionName)){
                if ("paper".equals(textureEnName) || "metal".equals(textureEnName) || "ceramicPain".equals(textureEnName)) {
                    actionName = "base-info-typea";
                } else if ("painting".equals(textureEnName)) {
                    actionName = "base-info-typeb";
                } else if ("spinning".equals(textureEnName) || "fabrics".equals(textureEnName)) {
                    actionName = "base-info-typec";
                } else if ("mural".equals(textureEnName)) {
                    actionName = "base-info-typed";
                }
            }

            users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());

            repairAssessment = repairAssessmentService.findByRepairRecordId(repairRecordId);
            log("档案填写", "文物修复");
        } catch (Exception e) {
            log.error("档案填写", e);
        }

        return Results.ftl("orion/pages/archivesComplete/archives-complete.ftl");
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

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getTextureEnName() {
        return textureEnName;
    }

    public void setTextureEnName(String textureEnName) {
        this.textureEnName = textureEnName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public RepairAssessment getRepairAssessment() {
        return repairAssessment;
    }

    public void setRepairAssessment(RepairAssessment repairAssessment) {
        this.repairAssessment = repairAssessment;
    }
}
