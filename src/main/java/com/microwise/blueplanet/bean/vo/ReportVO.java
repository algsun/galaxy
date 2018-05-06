package com.microwise.blueplanet.bean.vo;

/**
 * 报表vo
 * 
 * @author xubaoji
 * @date 2013-6-13
 */
public class ReportVO {

	/** 传感量标识 */
	private Integer sensorPhysicalId;

	/** 传感量 单位 */
	private String units;

	/** 传感量中文名 */
	private String sensorPhysicalName;

	/** 传感量最大值 （不知为何，此处使用 maxValue mybatis 中sql 语句无法执行） */
	private Double maValue;

	/** 传感量最小值 */
	private Double minValue;

	/** 传感量波动值 */
	private Double waveValue;

	public Integer getSensorPhysicalId() {
		return sensorPhysicalId;
	}

	public void setSensorPhysicalId(Integer sensorPhysicalId) {
		this.sensorPhysicalId = sensorPhysicalId;
	}

	public String getSensorPhysicalName() {
		return sensorPhysicalName;
	}

	public void setSensorPhysicalName(String sensorPhysicalName) {
		this.sensorPhysicalName = sensorPhysicalName;
	}

	public Double getMaValue() {
		return maValue;
	}

	public void setMaValue(Double maValue) {
		this.maValue = maValue;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getWaveValue() {
		return waveValue;
	}

	public void setWaveValue(Double waveValue) {
		this.waveValue = waveValue;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

}
