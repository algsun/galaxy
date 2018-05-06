package com.microwise.uma.bean;

import java.util.Date;
import java.util.List;

/**
 * 区域实时定位 实体
 * @author xubaoji
 * @date  2013-4-15 
 */
public class RealtimeBean {

	private String zoneId;
	
	private String zoneName;
	
	private Integer  personSum;
	
	private Integer  personId;
	
	private String  personName;
	
	private  Date time;
	
	private  List<RealtimeBean> children;

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getPersonSum() {
		return personSum;
	}

	public void setPersonSum(Integer personSum) {
		this.personSum = personSum;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<RealtimeBean> getChildren() {
		return children;
	}

	public void setChildren(List<RealtimeBean> children) {
		this.children = children;
	}
	
	
}
