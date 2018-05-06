package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.AvgdataPO;

import java.util.Date;

/**
 * 均峰值数据导出实体.
 *
 * @author wang.geng
 * @date 14-4-18 下午3:42
 */
public class AvgPeakExcelBeanVO extends AvgdataPO{

    /**
     * 最大值时刻
     */
    private Date maxDate;


    /**
     * 最小值时刻
     */
    private Date minDate;

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
}
