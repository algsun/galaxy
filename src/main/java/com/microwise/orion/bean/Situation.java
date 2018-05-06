package com.microwise.orion.bean;

/**
 * 修复记录情况表
 *
 * @author 王耕
 * @date 15-9-23
 */
public class Situation {
    /**
     * 自增主键
     */
    private int id;
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;
    /**
     * 质量
     */
    private String weight;
    /**
     * 修复后质量
     */
    private String repairedWeight;
    /**
     * 尺寸
     */
    private String size;
    /**
     * 修复后尺寸
     */
    private String repairedSize;
    /**
     * 文物修复过程记录综述
     */
    private String summarize;
    /**
     * 技术变更
     */
    private String techChange;
    /**
     * 工艺
     */
    private String craft;
    /**
     * 装裱样式
     */
    private String frameStyle;
    /**
     * 装裱用料
     */
    private String frameMaterial;
    /**
     * 包首
     */
    private String packHead;
    /**
     * 画轴
     */
    private String kakemono;
    /**
     * 种类
     */
    private String spinningType;
    /**
     * 制作工艺
     */
    private String craftsmanship;
    /**
     * 织造工艺
     */
    private String weavingProcess;
    /**
     * 织物组织
     */
    private String fabricPart;
    /**
     * 织物密度-经线
     */
    private String densityLong;
    /**
     * 织物密度-纬线
     */
    private String densityLat;
    /**
     * 丝线颜色-经线
     */
    private String colorLong;
    /**
     * 细线颜色-纬线
     */
    private String colorLat;
    /**
     * 丝线细度
     */
    private String slender;
    /**
     * 丝线捻向
     */
    private String twistDirection;
    /**
     * 丝线捻度
     */
    private String twistDegree;
    /**
     * 位置
     */
    private String position;

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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRepairedWeight() {
        return repairedWeight;
    }

    public void setRepairedWeight(String repairedWeight) {
        this.repairedWeight = repairedWeight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRepairedSize() {
        return repairedSize;
    }

    public void setRepairedSize(String repairedSize) {
        this.repairedSize = repairedSize;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public String getTechChange() {
        return techChange;
    }

    public void setTechChange(String techChange) {
        this.techChange = techChange;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }

    public String getFrameStyle() {
        return frameStyle;
    }

    public void setFrameStyle(String frameStyle) {
        this.frameStyle = frameStyle;
    }

    public String getFrameMaterial() {
        return frameMaterial;
    }

    public void setFrameMaterial(String frameMaterial) {
        this.frameMaterial = frameMaterial;
    }

    public String getPackHead() {
        return packHead;
    }

    public void setPackHead(String packHead) {
        this.packHead = packHead;
    }

    public String getKakemono() {
        return kakemono;
    }

    public void setKakemono(String kakemono) {
        this.kakemono = kakemono;
    }

    public String getSpinningType() {
        return spinningType;
    }

    public void setSpinningType(String spinningType) {
        this.spinningType = spinningType;
    }

    public String getCraftsmanship() {
        return craftsmanship;
    }

    public void setCraftsmanship(String craftsmanship) {
        this.craftsmanship = craftsmanship;
    }

    public String getWeavingProcess() {
        return weavingProcess;
    }

    public void setWeavingProcess(String weavingProcess) {
        this.weavingProcess = weavingProcess;
    }

    public String getFabricPart() {
        return fabricPart;
    }

    public void setFabricPart(String fabricPart) {
        this.fabricPart = fabricPart;
    }

    public String getDensityLong() {
        return densityLong;
    }

    public void setDensityLong(String densityLong) {
        this.densityLong = densityLong;
    }

    public String getDensityLat() {
        return densityLat;
    }

    public void setDensityLat(String densityLat) {
        this.densityLat = densityLat;
    }

    public String getColorLong() {
        return colorLong;
    }

    public void setColorLong(String colorLong) {
        this.colorLong = colorLong;
    }

    public String getColorLat() {
        return colorLat;
    }

    public void setColorLat(String colorLat) {
        this.colorLat = colorLat;
    }

    public String getSlender() {
        return slender;
    }

    public void setSlender(String slender) {
        this.slender = slender;
    }

    public String getTwistDirection() {
        return twistDirection;
    }

    public void setTwistDirection(String twistDirection) {
        this.twistDirection = twistDirection;
    }

    public String getTwistDegree() {
        return twistDegree;
    }

    public void setTwistDegree(String twistDegree) {
        this.twistDegree = twistDegree;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}