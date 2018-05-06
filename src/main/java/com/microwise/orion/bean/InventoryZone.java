package com.microwise.orion.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 按区域 统计盘点信息实体
 *
 * @author xubaoji
 * @date 2013-6-3
 * @check 2013-06-04 zhangpeng svn:4010
 */
public class InventoryZone {

    /**
     * 统计类型 总数 按区域统计 常量标识
     */
    public static final int TYPE_COUNT = 1;

    /**
     * 统计类型 在库 按区域统计 常量标识
     */
    public static final int TYPE_STOCK_IN = 2;

    /**
     * 统计类型 出库 按区域统计 常量标识
     */
    public static final int TYPE_STOCK_OUT = 3;

    /**
     * 统计类型 标签 按区域统计 常量标识
     */
    public static final int TYPE_TAG = 4;

    /**
     * 统计类型 扫描 按区域统计 常量标识
     */
    public static final int TYPE_SCAN = 5;

    /**
     * 统计类型 异常 按区域统计 常量标识
     */
    public static final int TYPE_ERROR = 6;

    /**
     * id 编号
     */
    protected Integer id;

    /**
     * 统计类型：1、总数 2、在库 3、出库 4、标签 5、扫描 6、异常
     */
    protected Integer statisticsType;

    /**
     * 数量
     */
    protected Integer countNo;

    /**
     * 件数
     */
    protected Integer sumNo;

    // 不知为何 sql 查询数据需要如此接收
    protected BigInteger bigCountNo;

    protected BigDecimal bigSumNo;

    /**
     * 区域id
     */
    protected String zoneId;

    /**
     * 盘点id
     */
    protected Integer inventoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(Integer statisticsType) {
        this.statisticsType = statisticsType;
    }

    public Integer getCountNo() {
        return countNo;
    }

    public void setCountNo(Integer countNo) {
        this.countNo = countNo;
    }


    public BigInteger getBigCountNo() {
        return bigCountNo;
    }

    public void setBigCountNo(BigInteger bigCountNo) {
        this.bigCountNo = bigCountNo;
        this.countNo = bigCountNo.intValue();
    }

    public Integer getSumNo() {
        return sumNo;
    }

    public void setSumNo(Integer sumNo) {
        this.sumNo = sumNo;
    }

    public BigDecimal getBigSumNo() {
        return bigSumNo;
    }

    public void setBigSumNo(BigDecimal bigSumNo) {
        this.bigSumNo = bigSumNo;
        this.sumNo = bigSumNo == null ? null : bigSumNo.intValue();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

}
