package com.microwise.orion.bean;

import java.util.Date;

/**
 * TODO
 * 文物修复相关图片
 *
 * @author 王耕
 * @date 15-10-22
 */
public class RepairPhoto {
    /**
     * 现状相关图像
     */
    public static final int QUO_PHOTO = 1;

    /**
     * 修复过程图像
     */
    public static final int REPAIR_PHOTO = 2;

    /**
     * 主键ID
     */
    private int id;
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;
    /**
     * 图片描述
     */
    private String description;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 类型,1:现状图片;2:修复记录图片
     */
    private int type;
    /**
     * 图片上传时间
     */
    private Date stamp;

    // base64
    private String base64;

    // 显示宽度和高度，需要计算，非数据库字段
    private double width;
    private double height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public int getType() {
        return type;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
