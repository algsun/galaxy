package com.microwise.halley.bean.vo;

import java.util.Date;

/**
 * 待外展记录
 *
 * @author li.jianfei
 * @date 2013-09-27
 */
public class ExhibitionVO {

    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 事件ID
     */
    private String outEventId;

    /**
     * 提用目的
     */
    private String useFor;

    /**
     * 预计开始时间
     */
    private Date estimatedBeginTime;

    /**
     * 预计结束时间
     */
    private Date estimatedEndTime;

    /**
     * 申请人/单位
     */
    private String applicant;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 外展状态
     */
    private int state;

    /**
     * 是否有目的地
     */
    private boolean hasPathPO;

    /**
     * 是否陈展中
     */
    private boolean onDisplay;

    public boolean isOnDisplay() {
        return onDisplay;
    }

    public void setOnDisplay(boolean onDisplay) {
        this.onDisplay = onDisplay;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    public Date getEstimatedBeginTime() {
        return estimatedBeginTime;
    }

    public void setEstimatedBeginTime(Date estimatedBeginTime) {
        this.estimatedBeginTime = estimatedBeginTime;
    }

    public Date getEstimatedEndTime() {
        return estimatedEndTime;
    }

    public void setEstimatedEndTime(Date estimatedEndTime) {
        this.estimatedEndTime = estimatedEndTime;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isHasPathPO() {
        return hasPathPO;
    }

    public void setHasPathPO(boolean hasPathPO) {
        this.hasPathPO = hasPathPO;
    }
}

