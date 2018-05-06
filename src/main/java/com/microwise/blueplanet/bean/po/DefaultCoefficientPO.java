package com.microwise.blueplanet.bean.po;

import java.io.Serializable;

/**
 * 数据对象
 * 
 * @since 2013-01-07
 */
public class DefaultCoefficientPO implements Serializable {

	private static final long serialVersionUID = 135754419007852543L;

	/**
	 * column m_defaultcoefficient.id 自增唯一标识
	 */
	private Integer id;

	/**
	 * column m_defaultcoefficient.sensorPhysicalid 传感量标识
	 */
	private Integer sensorPhysicalid;

	/**
	 * column m_defaultcoefficient.paramName 计算公式系数
	 */
	private String paramName;

	/**
	 * column m_defaultcoefficient.defaultValue 默认系数值
	 */
	private String defaultValue;

	public DefaultCoefficientPO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSensorPhysicalid() {
		return sensorPhysicalid;
	}

	public void setSensorPhysicalid(Integer sensorPhysicalid) {
		this.sensorPhysicalid = sensorPhysicalid;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "DefaultCoefficientDO [id=" + id + ", sensorPhysicalid="
				+ sensorPhysicalid + ", paramName=" + paramName
				+ ", defaultValue=" + defaultValue + "]";
	}

}