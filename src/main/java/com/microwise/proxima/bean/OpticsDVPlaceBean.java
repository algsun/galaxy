package com.microwise.proxima.bean;

/**
 * 
 * 光学摄像机点位
 * 
 * @author gaohui
 * @date 2012-7-6
 */
public class OpticsDVPlaceBean extends DVPlaceBean {

	/**
	 * 远程摄像机管理界面访问 端口
	 */
	private int dvPort;

	/**
	 * IO模块版本
	 */
	private int ioVersion;

	/**
	 * 远程IO模块IP
	 */
	private String ioIp;

	/**
	 * 远程IO模块端口
	 */
	private int ioPort;

	/**
	 * 拍照前开灯时间 (单位毫秒)
	 */
	private int lightOnTime;

	/**
	 * 拍照后开灯时间(单位毫秒)
	 */
	private int lightOffTime;

	/**
	 * 拍照时间
	 */
	private int photographTime;

	/**
	 * 是否外部控制
	 */
	private boolean ioOn;

	/**
	 * 是否开灯
	 */
	private boolean lightOn;

	/** 灯路数 */
	private Integer ioLightRoute;

	/** 摄像机路数 */
	private Integer ioDvRoute;

	public int getIoVersion() {
		return ioVersion;
	}

	public void setIoVersion(int ioVersion) {
		this.ioVersion = ioVersion;
	}

	public String getIoIp() {
		return ioIp;
	}

	public void setIoIp(String ioIp) {
		this.ioIp = ioIp;
	}

	public int getIoPort() {
		return ioPort;
	}

	public void setIoPort(int ioPort) {
		this.ioPort = ioPort;
	}

	public int getLightOnTime() {
		return lightOnTime;
	}

	public void setLightOnTime(int lightOnTime) {
		this.lightOnTime = lightOnTime;
	}

	public int getLightOffTime() {
		return lightOffTime;
	}

	public void setLightOffTime(int lightOffTime) {
		this.lightOffTime = lightOffTime;
	}

	public int getPhotographTime() {
		return photographTime;
	}

	public void setPhotographTime(int photographTime) {
		this.photographTime = photographTime;
	}

	public int getDvPort() {
		return dvPort;
	}

	public void setDvPort(int dvPort) {
		this.dvPort = dvPort;
	}

	public boolean isIoOn() {
		return ioOn;
	}

	public void setIoOn(boolean ioOn) {
		this.ioOn = ioOn;
	}

	public boolean isLightOn() {
		return lightOn;
	}

	public void setLightOn(boolean lightOn) {
		this.lightOn = lightOn;
	}

	public Integer getIoLightRoute() {
		return ioLightRoute;
	}

	public void setIoLightRoute(Integer ioLightRoute) {
		this.ioLightRoute = ioLightRoute;
	}

	public Integer getIoDvRoute() {
		return ioDvRoute;
	}

	public void setIoDvRoute(Integer ioDvRoute) {
		this.ioDvRoute = ioDvRoute;
	}

}
