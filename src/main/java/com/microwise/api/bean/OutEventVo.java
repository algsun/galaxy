package com.microwise.api.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microwise.orion.bean.OutEvent;

import java.util.Date;
import java.util.List;

/**
 * Created by xie.deng on 2017/7/13.
 */
public class OutEventVo {


    /**
     * id 编号
     */
    private String id;


    private String taskId;

    /**
     * 站点编号
     */
    private String siteId;

    /**
     * 出库体用目的
     */
    private String useFor;

    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 申请人或单位
     */
    private String applicant;

    /**
     * 出库单状态
     * <p/>
     * 0. 申请中
     * 1.出库
     * 2.回库
     */
    private int state;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 出库类型
     * <p/>
     * 1.外出借展
     * 2.科研修复
     */
    private int eventType;

    /**
     * 是否出馆
     */
    private boolean outBound;

    private String eventState;

    private List<EventAction> Actions;

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public List<EventAction> getActions() {
        return Actions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public boolean isOutBound() {
        return outBound;
    }

    public void setOutBound(boolean outBound) {
        this.outBound = outBound;
    }

    public void setActions(List<EventAction> actions) {
        Actions = actions;
    }

    public static OutEventVo castEvent2Vo(OutEvent outEvent) {
        OutEventVo outEventVo = new OutEventVo();
        outEventVo.setId(outEvent.getId());
        outEventVo.setApplicant(outEvent.getApplicant());
        outEventVo.setBeginDate(outEvent.getBeginDate());
        outEventVo.setEndDate(outEvent.getEndDate());
        outEventVo.setEventType(outEvent.getEventType());
        outEventVo.setState(outEvent.getState());
        outEventVo.setOutBound(outEvent.isOutBound());
        outEventVo.setRemark(outEvent.getRemark());
        outEventVo.setSiteId(outEvent.getSiteId());
        outEventVo.setUseFor(outEvent.getUseFor());
        return outEventVo;
    }
}
