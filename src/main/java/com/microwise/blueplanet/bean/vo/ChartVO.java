package com.microwise.blueplanet.bean.vo;

import com.microwise.common.sys.Constants.ChartConstants;
import org.apache.fop.render.afp.modca.ObjectAreaDescriptor;
import org.apache.struts2.json.annotations.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图表原始数据类
 *
 * @author zhangpeng
 * @date 2013-2-27
 * @check 2013-03-11 xubaoji svn:2014
 */
public class ChartVO {

    /**
     * 监测指标标识
     */
    protected Integer sensorPhysicalId;

    /**
     * 监测指标名称
     */
    protected String cnName;

    /**
     * 监测指标名称
     */
    protected String enName;

    /**
     * 监测指标单位
     */
    protected String units;

    /**
     * 监测指标精度
     */
    protected int sensorPrecision;

    /**
     * 当前返回数据集是否有数据
     */
    protected boolean hasData;

    /**
     * 图表原始数据，map：key-时间、value-值
     */
    private List<Map<String, Object>> chartData;

    /**
     * 当前数据集最大值
     */
    protected Map<String, Object> maxValue;

    /**
     * 当前数据集最小值
     */
    protected Map<String, Object> minValue;

    /**
     * 当前数据集统计合
     */
    protected double sumValue;

    protected String dateString;

    public ChartVO() {
    }

    public ChartVO(Integer sensorPhysicalId, String cnName, String enName,
                   String units, Integer sensorPrecision) {
        this.sensorPhysicalId = sensorPhysicalId;
        this.cnName = cnName;
        this.enName = enName;
        this.units = units;
        this.sensorPrecision = sensorPrecision;
    }


    public Integer getSensorPhysicalId() {
        return sensorPhysicalId;
    }

    public void setSensorPhysicalId(Integer sensorPhysicalId) {
        this.sensorPhysicalId = sensorPhysicalId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getSensorPrecision() {
        return sensorPrecision;
    }

    public void setSensorPrecision(int sensorPrecision) {
        this.sensorPrecision = sensorPrecision;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    // toJson时禁止序列表
    @JSON(serialize = false)
    public List<Map<String, Object>> getChartData() {
        return chartData;
    }

    public void setChartData(List<Map<String, Object>> chartData) {
        this.chartData = chartData;
        this.hasData = chartData != null && chartData.size() > 0;
        if (this.hasData) {
            calculate();
        }
    }

    /**
     * 计算最大最小及合计值
     */
    private void calculate() {
        double maxValue = 0.0d;
        Date maxTime = new Date();
        double minValue = 0.0d;
        Date minTime = new Date();
        double sum = 0.0;
        for (Map<String, Object> map : chartData) {
            Object data = map.get(ChartConstants.CHARTDATA_KEY_DATA);
            // 异常数据
            if (data == null) continue;

            Date time = (Date) map.get(ChartConstants.CHARTDATA_KEY_TIME);
            double value = Double.valueOf(data.toString());
            if (maxValue == 0 && minValue == 0) {
                maxValue = value;
                minValue = value;
                maxTime = time;
                minTime = time;
            }

            sum = sum + value;
            if (value > maxValue) {
                maxTime = time;
                maxValue = value;
            } else if (value < minValue) {
                minTime = time;
                minValue = value;
            }
        }
        Map<String, Object> max = new HashMap<String, Object>();
        max.put(ChartConstants.CHARTDATA_KEY_TIME, maxTime);
        max.put(ChartConstants.CHARTDATA_KEY_DATA, maxValue);
        Map<String, Object> min = new HashMap<String, Object>();
        min.put(ChartConstants.CHARTDATA_KEY_TIME, minTime);
        min.put(ChartConstants.CHARTDATA_KEY_DATA, minValue);

        setMaxValue(max);
        setMinValue(min);
        setSumValue(sum);
    }

    public Map<String, Object> getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Map<String, Object> maxValue) {
        this.maxValue = maxValue;
    }

    public Map<String, Object> getMinValue() {
        return minValue;
    }

    public void setMinValue(Map<String, Object> minValue) {
        this.minValue = minValue;
    }

    public double getSumValue() {
        return sumValue;
    }

    public void setSumValue(double sumValue) {
        this.sumValue = sumValue;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public String toString() {
        return "ChartVO [sensorPhysicalId=" + sensorPhysicalId + ", cnName="
                + cnName + ", enName=" + enName + ", units=" + units
                + ", sensorPrecision=" + sensorPrecision + ", hasData="
                + hasData + ", chartData=" + chartData + ", maxValue="
                + maxValue + ", minValue=" + minValue + ", sumValue="
                + sumValue + ", dateString=" + dateString + "]";
    }


}
