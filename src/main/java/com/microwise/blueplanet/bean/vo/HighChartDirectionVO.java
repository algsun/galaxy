package com.microwise.blueplanet.bean.vo;

/**
 * 风向玫瑰图但方向相关信息数据对象
 * 
 * @author zhangpeng
 * @date 2013-3-6
 * 
 * @check 2013-03-11 xubaoji svn:2014
 */
public class HighChartDirectionVO {

	/** 方向标识  (例如 N,S.....等风向)*/
	private String directionId;

	/** 风向频率 */
	private Double windFrequency;

	/** 平均风速 */
	private Double windSpeed;

	public String getDirectionId() {
		return directionId;
	}

	public void setDirectionId(String directionId) {
		this.directionId = directionId;
	}

	public Double getWindFrequency() {
		return windFrequency;
	}

	public void setWindFrequency(Double windFrequency) {
		this.windFrequency = windFrequency;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

}
