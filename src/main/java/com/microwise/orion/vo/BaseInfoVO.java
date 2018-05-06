package com.microwise.orion.vo;

import com.microwise.orion.bean.Photo;

import java.util.Set;

/**
 * 文物基本信息表
 *
 * @author 王耕
 * @date 15-9-16
 */
public class BaseInfoVO {

    private String BEAN_TYPE = "BASE_INFO";
    /**
     * 文物名称
     */
    private String biRelicName;
    /**
     * 文物编号
     */
    private String biRelicNum;
    /**
     * 年代
     */
    private String era;
    /**
     * 级别
     */
    private String level;
    /**
     * 质地
     */
    private String texture;
    /**
     * 收藏单位
     */
    private String collectionUnit;
    /**
     * 来源
     */
    private String source;
    /**
     * 收藏库房
     */
    private String collectionRoom;
    /**
     * 修复人员
     */
    private String repairPerson;
    /**
     * 修复因由
     */
    private String repairReason;
    /**
     * 修复内容
     */
    private String repairContent;
    /**
     * 提取时间
     */
    private String extractionTime;
    /**
     * 归还时间
     */
    private String returnTime;
    /**
     * 尺寸(cm)
     */
    private String size;
    /**
     * 质量(g)
     */
    private String weight;
    /**
     * 修复后尺寸(cm)
     */
    private String repairedSize;
    /**
     * 修复后质量(g)
     */
    private String repairedWeight;
    /**
     * 照片
     */
    private Set<Photo> photos;
    /**
     * 工艺
     */
    private String craft;
    /**
     * 文物描述
     */
    private String description;
    /**
     * 相关文献
     */
    private String document;
    /**
     * 备注
     */
    private String remark;

    public String getBiRelicName() {
        return biRelicName;
    }

    public void setBiRelicName(String biRelicName) {
        this.biRelicName = biRelicName;
    }

    public String getBiRelicNum() {
        return biRelicNum;
    }

    public void setBiRelicNum(String biRelicNum) {
        this.biRelicNum = biRelicNum;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getCollectionUnit() {
        return collectionUnit;
    }

    public void setCollectionUnit(String collectionUnit) {
        this.collectionUnit = collectionUnit;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCollectionRoom() {
        return collectionRoom;
    }

    public void setCollectionRoom(String collectionRoom) {
        this.collectionRoom = collectionRoom;
    }

    public String getRepairPerson() {
        return repairPerson;
    }

    public void setRepairPerson(String repairPerson) {
        this.repairPerson = repairPerson;
    }

    public String getRepairReason() {
        return repairReason;
    }

    public void setRepairReason(String repairReason) {
        this.repairReason = repairReason;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getExtractionTime() {
        return extractionTime;
    }

    public void setExtractionTime(String extractionTime) {
        this.extractionTime = extractionTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRepairedSize() {
        return repairedSize;
    }

    public void setRepairedSize(String repairedSize) {
        this.repairedSize = repairedSize;
    }

    public String getRepairedWeight() {
        return repairedWeight;
    }

    public void setRepairedWeight(String repairedWeight) {
        this.repairedWeight = repairedWeight;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBEAN_TYPE() {
        return BEAN_TYPE;
    }

    public void setBEAN_TYPE(String BEAN_TYPE) {
        this.BEAN_TYPE = BEAN_TYPE;
    }
}
