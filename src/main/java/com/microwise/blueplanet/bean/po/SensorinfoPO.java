package com.microwise.blueplanet.bean.po;

import java.io.Serializable;

/**
 * 数据对象
 * 
 * @since 2013-01-07
 */
public class SensorinfoPO implements Serializable {
    // 显示类型枚举
    public static final int SHOW_TYPE_DEFAULT = 0;
    public static final int SHOW_TYPE_WIND = 1;
    public static final int SHOW_TYPE_GPS = 2;

	private static final long serialVersionUID = 135754419065640807L;

	/**
	 * column m_sensorinfo.id uuid
	 */
	protected Integer id;

	/**
	 * column m_sensorinfo.sensorPhysicalid 传感量标识
	 */
	protected Integer sensorPhysicalid;

	/**
	 * column m_sensorinfo.en_name 传感量缩写
	 */
	protected String enName;

	/**
	 * column m_sensorinfo.cn_name 监测量中文名
	 */
	protected String cnName;

	/**
	 * column m_sensorinfo.sensorPrecision 传感量精度
	 */
	protected Integer sensorPrecision;

	/**
	 * column m_sensorinfo.units 计量单位
	 */
	protected String units;

	/**
	 * column m_sensorinfo.positions 显示位
	 */
	protected Integer positions;

	/**
	 * column m_sensorinfo.isActive 是否有效 1：有效 0：无效
	 */
	protected Integer isActive;

	/**
	 * column m_sensorinfo.showType
	 * 0默认，1风向类；该字段用于呈现判断，风向类在实时数据、历史数据中需要展示为方向标识，而在图表中需要具体数值，考虑扩展性，加入此标识
	 */
	protected Integer showType;

    /**
     * 上限
     */
    protected float maxValue;

    /**
     * 下限
     */
    protected float minValue;

    protected int rangeType;

	public SensorinfoPO() {
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

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public Integer getSensorPrecision() {
		return sensorPrecision;
	}

	public void setSensorPrecision(Integer sensorPrecision) {
		this.sensorPrecision = sensorPrecision;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Integer getPositions() {
		return positions;
	}

	public void setPositions(Integer positions) {
		this.positions = positions;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public int getRangeType() {
        return rangeType;
    }

    public void setRangeType(int rangeType) {
        this.rangeType = rangeType;
    }

    @Override
	public String toString() {
		return "SensorinfoPO [id=" + id + ", sensorPhysicalid="
				+ sensorPhysicalid + ", enName=" + enName + ", cnName="
				+ cnName + ", sensorPrecision=" + sensorPrecision + ", units="
				+ units + ", positions=" + positions + ", isActive=" + isActive
				+ ", showType=" + showType + "]";
	}

}