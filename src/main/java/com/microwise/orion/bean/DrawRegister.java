package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microwise.blackhole.bean.User;

import java.util.Date;

/**
 * 绘图登记bean
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class DrawRegister {

    /**
     * 自增id
     */
    private int id;

    /**
     * 关联记录id
     */
    @JsonIgnore
    private RepairRecord repairRecord;

    /**
     * 图纸类型
     */
    private String drawingType;

    /**
     * 简述
     */
    private String description;

    /**
     * 绘图人
     */
    private User drawingPerson;

    /**
     * 创建时间
     */
    private Date stamp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片路径
     */
    private String imgPath;


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

    public String getDrawingType() {
        return drawingType;
    }

    public void setDrawingType(String drawingType) {
        this.drawingType = drawingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getDrawingPerson() {
        return drawingPerson;
    }

    public void setDrawingPerson(User drawingPerson) {
        this.drawingPerson = drawingPerson;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
