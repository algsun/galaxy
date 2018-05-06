package com.microwise.blueplanet.bean.vo;

import com.microwise.common.sys.Constants.ChartConstants;
import org.apache.struts2.json.annotations.JSON;

import java.util.*;

/**
 * 设计模式一种，名字忘记了，囧
 *
 * @author zhangpeng
 * @date 2013-2-28
 * @check 2013-03-11 xubaoji svn:2014
 */
public class HighChartSonVO extends HighChartVO {

    /**
     * HighChartVO 的父类
     */
    private ChartVO chart;

    /**
     * HighChartSonVO的唯一构造函数
     *
     * @param chart HighChartVO 的父类
     */
    public HighChartSonVO(ChartVO chart) {
        this.chart = chart;
        if (chart != null) {
            initHighChart();
        }
    }

    /**
     * 初始化数据给HighChartVO
     */
    private void initHighChart() {
        if (isHasData()) {
            List<List<Object>> resultList = new ArrayList<List<Object>>();
            List<Map<String, Object>> chartData = chart.getChartData();
            for (Map<String, Object> map : chartData) {
                List<Object> list = new ArrayList<Object>();
                Date time = (Date) map.get(ChartConstants.CHARTDATA_KEY_TIME);
                list.add(time.getTime());
                if (map.get(ChartConstants.CHARTDATA_KEY_DATA) == null) {
                    list.add(null);
                } else {
                    double data = Double.valueOf(map.get(
                            ChartConstants.CHARTDATA_KEY_DATA).toString());
                    list.add(data);
                }
                resultList.add(list);
            }
            this.data = resultList;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ChartConstants.TOOLTIP_YSUFFIX, getUnits());
        // 注释原因：highChart可不取平均值，就不会出现N位问题，这里僵尸代码，说不定以后还用
        // map.put(ChartConstants.TOOLTIP_YDECIMALS, getSensorPrecision());
        this.tooltip = map;
        this.yText = getCnName() + "(" + getUnits() + ")";
        this.hasData = isHasData();
        calculate();
    }

    /**
     * 计算最大最小及合计值
     */
    private void calculate() {
        // getMinValue() 获得的map集合里封装了 时间，和值 所有 map 的size 为2
        if (getMinValue() != null && getMinValue().size() == 2) {
            long minTime = ((Date) getMinValue().get(
                    ChartConstants.CHARTDATA_KEY_TIME)).getTime();
            getMinValue().remove(ChartConstants.CHARTDATA_KEY_TIME);
            getMinValue().put(ChartConstants.CHARTDATA_KEY_TIME, minTime);
            this.minValue = getMinValue();
            long maxTime = ((Date) getMaxValue().get(
                    ChartConstants.CHARTDATA_KEY_TIME)).getTime();
            getMaxValue().remove(ChartConstants.CHARTDATA_KEY_TIME);
            getMaxValue().put(ChartConstants.CHARTDATA_KEY_TIME, maxTime);
            this.minValue = getMaxValue();
        }
    }

    public Integer getSensorPhysicalId() {
        return chart.getSensorPhysicalId();
    }

    public void setSensorPhysicalId(Integer sensorPhysicalId) {
        chart.setSensorPhysicalId(sensorPhysicalId);
    }

    public String getCnName() {
        return chart.getCnName();
    }

    public void setCnName(String cnName) {
        chart.setCnName(cnName);
    }

    public String getEnName() {
        return chart.getEnName();
    }

    public void setEnName(String enName) {
        chart.setEnName(enName);
    }

    public String getUnits() {
        return chart.getUnits();
    }

    public void setUnits(String units) {
        chart.setUnits(units);
    }

    public ChartVO getChart() {
        return chart;
    }

    public void setChart(ChartVO chart) {
        this.chart = chart;
    }

    public boolean isHasData() {
        return chart.isHasData();
    }

    public void setHasData(boolean hasData) {
        chart.setHasData(hasData);
    }

    @JSON(serialize = false)
    public List<Map<String, Object>> getChartData() {
        return chart.getChartData();
    }

    public void setChartData(List<Map<String, Object>> chartData) {
        chart.setChartData(chartData);
    }

    public int hashCode() {
        return chart.hashCode();
    }

    public Map<String, Object> getMaxValue() {
        return chart.getMaxValue();
    }

    public void setMaxValue(Map<String, Object> maxValue) {
        chart.setMaxValue(maxValue);
    }

    public Map<String, Object> getMinValue() {
        return chart.getMinValue();
    }

    public void setMinValue(Map<String, Object> minValue) {
        chart.setMinValue(minValue);
    }

    public double getSumValue() {
        return chart.getSumValue();
    }

    public void setSumValue(double sumValue) {
        chart.setSumValue(sumValue);
    }

    public int getSensorPrecision() {
        return chart.getSensorPrecision();
    }

    public void setSensorPrecision(int sensorPrecision) {
        chart.setSensorPrecision(sensorPrecision);
    }

}
