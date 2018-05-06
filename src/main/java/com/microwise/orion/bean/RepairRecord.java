package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microwise.blackhole.bean.User;

import java.util.Date;
import java.util.List;

/**
 * 任务bean
 *
 * @author liuzhu
 * @date 2015-11-10
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RepairRecord {

    public static final int unAllocation = 1;

    public static final int unAccepted = 2;

    public static final int repairing = 3;

    public static final int unCheck = 4;

    public static final int unReviews = 5;

    public static final int completed = 6;

    /**
     * 站点id
     */
    private int id;

    /**
     * 序号
     */
    private int identifier;

    /**
     * 修复因由
     */
    private RepairReason repairReason;

    /**
     * 关联文物
     */
    private Relic relic;

    /**
     * 提取时间
     */
    private Date extractDate;

    /**
     * 归还时间
     */
    private Date returnDate;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 任务状态:1-待分配;2-待接受;3-修复中;4-待审核;5-待点评;6-完成
     */
    private int state;

    /**
     * 修复内容
     */
    private String repairInfo;

    /**
     * 方案
     */
    private Scheme scheme;

    /**
     * 设计单位
     */
    private Institution institution;

    private Situation situation;

    /**
     * 负责人
     */
    private User mainUser;

    /**
     * 协助人
     */
    private String secondaryUserId;

    private List<String> secondaryUserName;

    /**
     * 绘图登记表
     */
    private List<DrawRegister> drawRegisters;

    /**
     * 审核不通过原因
     */
    private String notByReason;

    /**
     * 审核人id
     */
    private User checkUser;

    /**
     * 审核时间
     */
    private Date checkDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public Date getExtractDate() {
        return extractDate;
    }

    public void setExtractDate(Date extractDate) {
        this.extractDate = extractDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public RepairReason getRepairReason() {
        return repairReason;
    }

    public void setRepairReason(RepairReason repairReason) {
        this.repairReason = repairReason;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRepairInfo() {
        return repairInfo;
    }

    public void setRepairInfo(String repairInfo) {
        this.repairInfo = repairInfo;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    public String getSecondaryUserId() {
        return secondaryUserId;
    }

    public void setSecondaryUserId(String secondaryUserId) {
        this.secondaryUserId = secondaryUserId;
    }

    public List<String> getSecondaryUserName() {
        return secondaryUserName;
    }

    public void setSecondaryUserName(List<String> secondaryUserName) {
        this.secondaryUserName = secondaryUserName;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public List<DrawRegister> getDrawRegisters() {
        return drawRegisters;
    }

    public void setDrawRegisters(List<DrawRegister> drawRegisters) {
        this.drawRegisters = drawRegisters;
    }

    public String getNotByReason() {
        return notByReason;
    }

    public void setNotByReason(String notByReason) {
        this.notByReason = notByReason;
    }

    public User getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}
