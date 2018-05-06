package com.microwise.uma.bean;

import java.util.Date;
import java.util.List;

/**
 * 区域实体对象
 * 
 * @author li.jianfei
 * @date 2013-4-18
 */
public class ZoneBean {

	/**
	 * 区域ID
	 */
	private String zoneId;

	/**
	 * 父区域ID
	 */
	private String parentId;

	/**
	 * 区域名称
	 */
	private String zoneName;

	/**
	 * 区域级别
	 */
	private int level;

	/**
	 * 是否有子区域
	 */
	private boolean hasChildren;

	/**
	 * 当前人员总数
	 */
	private int numberOfPeople;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户
	 */
	private String userName;

	/**
	 * 实时位置信息表发生时间
	 */
	private Date occurrenceTime;

    /**
     * 区域平面图
     */
    private String planImage;

	/**
	 * 是否展开子区域
	 */
	private boolean showSub;

	/**
	 * 实时定位中是否隐藏
	 */
	private boolean hide;

	/**
	 * 间隔时间
	 */
	private String timeStr;

	/**
	 * 子区域集合（用于实时定位统计人数）
	 */
	private List<ZoneBean> subZoneList;

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Object occurrenceTime) {
		if (occurrenceTime instanceof Long) {
			this.occurrenceTime = new Date(((Long) occurrenceTime).longValue());
		} else if (occurrenceTime instanceof Date) {
			this.occurrenceTime = (Date) occurrenceTime;
		}
		if(this.getOccurrenceTime() != null){
			long sTimes = System.currentTimeMillis();
			long oTimes = this.getOccurrenceTime().getTime();
			long hour = (sTimes - oTimes) / (1000 * 60 * 60);
			hour = hour < 1 ? 0 : hour;
			long mi = (sTimes - oTimes - hour * (1000 * 60 * 60)) / (1000 * 60);
			if (hour >= 1) {
				timeStr = hour + "小时前";
			} else if (mi >= 1) {
				timeStr = mi + "分钟前";
			} else
				timeStr = (sTimes - oTimes) / 1000 + "秒前";

		}
	}

    public String getPlanImage() {
        return planImage;
    }

    public void setPlanImage(String planImage) {
        this.planImage = planImage;
    }

    public List<ZoneBean> getSubZoneList() {
		return subZoneList;
	}

	public void setSubZoneList(List<ZoneBean> subZoneList) {
		this.subZoneList = subZoneList;
	}

	public boolean isShowSub() {
		return showSub;
	}

	public void setShowSub(boolean showSub) {
		this.showSub = showSub;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimeStr() {

		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	/**
	 * 浅克隆
	 * @param another
	 */
	public void simpleClone(ZoneBean another) {
		this.setNumberOfPeople(another.getNumberOfPeople());
		this.setOccurrenceTime(another.getOccurrenceTime());
		this.setUserId(another.getUserId());
		this.setUserName(another.getUserName());
	}
}
