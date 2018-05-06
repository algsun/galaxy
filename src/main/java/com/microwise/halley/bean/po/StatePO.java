package com.microwise.halley.bean.po;

import java.util.Date;

/**
 * 外展状态
 *
 * @author wanggeng
 * @date 13-9-25 下午3:53
 */
public class StatePO {

    /** 序列号id */
    private int id;

    /** 关联的外展ID h_exhibition.id */
    private int exhibitionId;

    /** 外展状态 */
    private int state;

    /** 变更时间 */
    private Date changeTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
}
