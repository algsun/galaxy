package com.microwise.blueplanet.bean.vo;

import java.util.Date;
import java.util.Map;

/**
 * 历史数据vo对象
 * 
 * @author xubaoji
 * @date 2013-1-31
 */
public class HistoryDataVO {

	/** 电压(默认为0)。-1：无电压值 Y=x/10(实际电压，保留小数点1位)其他情况参考协议内容 */
	private float lowvoltage;

	/** -1：超时,0：正常,1：低压,2：掉电 */
	private int anomaly;

	/** 设备实时数据采样时间 采用一组数据中时间最大值 */
	private Date stamp;

	/** 设备下监测指标的实时数据信息 */
	private Map<Integer, DeviceDataVO> sensorinfoMap;

	public float getLowvoltage() {
		return lowvoltage;
	}

	public void setLowvoltage(float lowvoltage) {
		this.lowvoltage = lowvoltage;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public Map<Integer, DeviceDataVO> getSensorinfoMap() {
		return sensorinfoMap;
	}

	public void setSensorinfoMap(Map<Integer, DeviceDataVO> sensorinfoMap) {
		this.sensorinfoMap = sensorinfoMap;
	}

	public int getAnomaly() {
		return anomaly;
	}

	public void setAnomaly(int anomaly) {
		this.anomaly = anomaly;
	}

}
