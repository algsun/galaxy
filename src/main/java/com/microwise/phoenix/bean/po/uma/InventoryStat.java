package com.microwise.phoenix.bean.po.uma;

/**
 * 藏品管理：盘点统计实体
 *
 * @author xu.baoji
 * @date 2013-7-22
 */
public class InventoryStat {

    /**
     * 统计年份
     */
    private Integer year;

    /**
     * 本年盘点次数
     */
    private Integer count;

    /**
     * 盘点涉及文物数量
     */
    private Integer sum;

    /**
     * 文物异常次数
     */
    private Integer errorSum;

    /**
     * 文物异常比重
     */
    private float errorProportion;

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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getErrorSum() {
        return errorSum;
    }

    public void setErrorSum(Integer errorSum) {
        this.errorSum = errorSum;
    }

    public float getErrorProportion() {
        return errorProportion;
    }

    public void setErrorProportion(float errorProportion) {
        this.errorProportion = errorProportion;
    }

}
