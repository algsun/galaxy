package com.microwise.halley.bean.po;

import com.microwise.blackhole.bean.User;

import java.util.Date;

/**
 * 事件 实体
 * 
 * @author xu.yuexi
 * @date 2013-10-24
 *
 */
public class OutEventPO {



	/** id 编号 */
	private String id;

	/** 站点编号 */
	private String siteId;

	/** 出库体用目的 */
	private String useFor;

	/** 开始时间 */
	private Date beginDate;

	/** 结束时间 */
	private Date endDate;

	/** 申请人或单位 */
	private String applicant;

    /**
     * 出库单状态
     *
     * 0. 申请中
     * 1.出库
     * 2.回库
     */
    private int state;

	/** 备注信息 */
	private String remark;

    /**
     * 出库类型
     *
     * 1.外出借展
     * 2.科研修复
     */
    private int eventType;

    /** 是否出馆 */
    private boolean outBound;

	/** 负责人/代办人 */
	private User user;

     private int exhibitionId;

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
}
