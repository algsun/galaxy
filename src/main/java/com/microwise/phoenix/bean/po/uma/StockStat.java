package com.microwise.phoenix.bean.po.uma;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 藏品管理：盘点统计实体
 *
 * @author xu.yuexi
 * @date 2014-9-18
 */
public class StockStat {

    /**
     * 统计年份
     */
    private Integer year;

    /**
     * 本年盘点次数
     */
    private Integer count;

    /**
     * 单次盘点识别率
     */
    private List<Map<Date, Double>> singleRecognition = new ArrayList<Map<Date, Double>>();

    /**
     * 年度盘点识别率
     */
    private double yearRecognition;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Map<Date, Double>> getSingleRecognition() {
        return singleRecognition;
    }

    public void setSingleRecognition(List<Map<Date, Double>> singleRecognition) {
        this.singleRecognition = singleRecognition;
    }

    public double getYearRecognition() {
        return yearRecognition;
    }

    public void setYearRecognition(double yearRecognition) {
        this.yearRecognition = yearRecognition;
    }
}
