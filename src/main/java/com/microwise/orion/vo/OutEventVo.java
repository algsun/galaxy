package com.microwise.orion.vo;

import java.util.Date;
import java.util.List;

/**
 * http 服务 出库单 vo
 *
 * @author xubaoji
 * @date 2013-5-31
 */
public class OutEventVo {

    /**
     * id 编号
     */
    private String id;

    /**
     * 出库体用目的
     */
    private String useFor;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 出库单状态
     */
    private int state;
    // TODO 还原代码
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
     * 时间类型: 1.外出借展 2.科研修复
     */
    private Integer eventType;
    /**
     * 是否出馆: 0 否 1 是
     */
    private Integer outBound;



    /**
     * 出库单文物
     */
    private List<RelicVo> eventRelics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    public List<RelicVo> getEventRelics() {
        return eventRelics;
    }

    public void setEventRelics(List<RelicVo> eventRelics) {
        this.eventRelics = eventRelics;
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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

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

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getOutBound() {
        return outBound;
    }

    public void setOutBound(Integer outBound) {
        this.outBound = outBound;
    }
}
