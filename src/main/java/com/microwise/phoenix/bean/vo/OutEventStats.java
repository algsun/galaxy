package com.microwise.phoenix.bean.vo;

/***
 * 年 出库文物统计
 *
 * @author xu.baoji
 * @date 2013-7-9
 */
public class OutEventStats {

    /**
     * 年 出库次数
     */
    private Integer yearCount;

    /**
     * 年出库文物数量
     */
    private Integer yearSum;

    /**
     * 统计年份
     **/
    private Integer year;

    public Integer getYearCount() {
        return yearCount;
    }

    public void setYearCount(Integer yearCount) {
        this.yearCount = yearCount;
    }

    public Integer getYearSum() {
        return yearSum;
    }

    public void setYearSum(Integer yearSum) {
        this.yearSum = yearSum;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
