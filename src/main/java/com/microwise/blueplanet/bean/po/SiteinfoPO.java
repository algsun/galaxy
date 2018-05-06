package com.microwise.blueplanet.bean.po;

import java.io.Serializable;

/**
 * 数据对象
 * 
 * @since 2013-01-07
 */
public class SiteinfoPO implements Serializable {

	private static final long serialVersionUID = 135754419201530876L;

	/**
	 * column t_siteinfo.siteId
	 */
	private String siteid;

	/**
	 * column t_siteinfo.siteName
	 */
	private String siteName;

	/**
	 * column t_siteinfo.planAddress
	 */
	private String planAddress;

	/**
	 * column t_siteinfo.isCurrentSite 0:默认值，非当前站点 1:是当前站点
	 */
	private Integer isCurrentSite;

	public SiteinfoPO() {
		super();
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPlanAddress() {
		return planAddress;
	}

	public void setPlanAddress(String planAddress) {
		this.planAddress = planAddress;
	}

	public Integer getIsCurrentSite() {
		return isCurrentSite;
	}

	public void setIsCurrentSite(Integer isCurrentSite) {
		this.isCurrentSite = isCurrentSite;
	}

	@Override
	public String toString() {
		return "SiteinfoDO [siteid=" + siteid + ", siteName=" + siteName
				+ ", planAddress=" + planAddress + ", isCurrentSite="
				+ isCurrentSite + "]";
	}

}