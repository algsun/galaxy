package com.microwise.blueplanet.bean.vo;

import com.microwise.common.sys.Constants.ChartConstants;

import java.util.List;

/**
 * highChart 风向玫瑰图 数据封装vo 对象
 * 
 * @author zhangpeng
 * @date 2013-3-6
 * 
 * @check 2013-03-11 xubaoji svn:2014
 */
@SuppressWarnings("unused")
public class HighChartWindRoseVO {

	/** 风向频率标题 */
	private String windFrequencyText;

	/** 平均风速标题 */
	private String windSpeedText;

	/** 风向频率单位 */
	private String windFrequencyUnits;

	/** 平均风速单位 */
	private String windSpeedUnits;

	/** 设备id */
	private String deviceId;

	/** 设备名称 */
	private String deviceName;

	/** 静风率 */
	private Double windcalm;

	/** 图表标题 */
	private String text;

	/** 当前返回数据集是否有数据 */
	protected boolean hasData;

	/** 方向信息对象列表 */
	private List<HighChartDirectionVO> directionList;

	public HighChartWindRoseVO() {
	}

	public HighChartWindRoseVO(boolean hasData) {
		this.hasData = hasData;
	}

	public HighChartWindRoseVO(String deviceId, String deviceName,
			Double windcalm, List<HighChartDirectionVO> directionList,
			boolean hasData) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.windcalm = windcalm;
		this.directionList = directionList;
		this.hasData = hasData;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Double getWindcalm() {
		return windcalm;
	}

	public void setWindcalm(Double windcalm) {
		this.windcalm = windcalm;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isHasData() {
		return hasData;
	}

	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}

	public List<HighChartDirectionVO> getDirectionList() {
		return directionList;
	}

	public void setDirectionList(List<HighChartDirectionVO> directionList) {
		this.directionList = directionList;
	}

	public String getWindFrequencyText() {
		return ChartConstants.WIND_FREQUENCY_TEXT;
	}

	public void setWindFrequencyText(String windFrequencyText) {
		this.windFrequencyText = windFrequencyText;
	}

	public String getWindSpeedText() {
		return ChartConstants.WIND_SPEED_TEXT;
	}

	public void setWindSpeedText(String windSpeedText) {
		this.windSpeedText = windSpeedText;
	}

	public String getWindFrequencyUnits() {
		return ChartConstants.WIND_FREQUENCY_UNITS;
	}

	public void setWindFrequencyUnits(String windFrequencyUnits) {
		this.windFrequencyUnits = windFrequencyUnits;
	}

	public String getWindSpeedUnits() {
		return ChartConstants.WIND_SPEED_UNITS;
	}

	public void setWindSpeedUnits(String windSpeedUnits) {
		this.windSpeedUnits = windSpeedUnits;
	}

}
