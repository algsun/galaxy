package com.microwise.orion.vo;

/**
 * 方案设计及保护修复单位信息记录表
 *
 * @author 王耕
 * @date 15-9-17
 */
public class DesignRepairUnitVO {

    private String BEAN_TYPE = "DESIGN_REPAIR_UNIT";
    /**
     * 修复方案名称
     */
    private String schemeName;
    /**
     * 修复方案编号
     */
    private String schemeId;
    /**
     * 批准单位
     */
    private String confirmUnit;
    /**
     * 批准时间
     */
    private String confirmTime;
    /**
     * 批准文号
     */
    private String confirmNum;

    /**
     * 方案设计单位名称
     */
    private String institutionName_design;
    /**
     * 方案设计单位所在地
     */
    private String institutionSeat_design;
    /**
     * 方案设计单位通信地址
     */
    private String institutionMailing_design;
    /**
     * 方案设计单位邮编
     */
    private String institutionZipcode_design;
    /**
     * 方案设计单位资质证书
     */
    private String institutionQualification_design;
    /**
     * 方案设计单位代码
     */
    private String institutionCode_design;

    /**
     * 保护修复单位名称
     */
    private String institutionName_repair;
    /**
     * 保护修复单位所在地
     */
    private String institutionSeat_repair;
    /**
     * 保护修复单位通信地址
     */
    private String institutionMailing_repair;
    /**
     * 保护修复单位邮编
     */
    private String institutionZipcode_repair;
    /**
     * 保护修复单位资质证书
     */
    private String institutionQualification_repair;
    /**
     * 保护修复单位代码
     */
    private String institutionCode_repair;

    /**
     * 提取日期
     */
    private String extractDate;
    /**
     * 提取经办人
     */
    private String extractPerson;
    /**
     * 返还日期
     */
    private String returnDate;
    /**
     * 返还经办人
     */
    private String returnPerson;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getConfirmUnit() {
        return confirmUnit;
    }

    public void setConfirmUnit(String confirmUnit) {
        this.confirmUnit = confirmUnit;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmNum() {
        return confirmNum;
    }

    public void setConfirmNum(String confirmNum) {
        this.confirmNum = confirmNum;
    }

    public String getInstitutionName_design() {
        return institutionName_design;
    }

    public void setInstitutionName_design(String institutionName_design) {
        this.institutionName_design = institutionName_design;
    }

    public String getInstitutionSeat_design() {
        return institutionSeat_design;
    }

    public void setInstitutionSeat_design(String institutionSeat_design) {
        this.institutionSeat_design = institutionSeat_design;
    }

    public String getInstitutionMailing_design() {
        return institutionMailing_design;
    }

    public void setInstitutionMailing_design(String institutionMailing_design) {
        this.institutionMailing_design = institutionMailing_design;
    }

    public String getInstitutionZipcode_design() {
        return institutionZipcode_design;
    }

    public void setInstitutionZipcode_design(String institutionZipcode_design) {
        this.institutionZipcode_design = institutionZipcode_design;
    }

    public String getInstitutionQualification_design() {
        return institutionQualification_design;
    }

    public void setInstitutionQualification_design(String institutionQualification_design) {
        this.institutionQualification_design = institutionQualification_design;
    }

    public String getInstitutionCode_design() {
        return institutionCode_design;
    }

    public void setInstitutionCode_design(String institutionCode_design) {
        this.institutionCode_design = institutionCode_design;
    }

    public String getInstitutionName_repair() {
        return institutionName_repair;
    }

    public void setInstitutionName_repair(String institutionName_repair) {
        this.institutionName_repair = institutionName_repair;
    }

    public String getInstitutionSeat_repair() {
        return institutionSeat_repair;
    }

    public void setInstitutionSeat_repair(String institutionSeat_repair) {
        this.institutionSeat_repair = institutionSeat_repair;
    }

    public String getInstitutionMailing_repair() {
        return institutionMailing_repair;
    }

    public void setInstitutionMailing_repair(String institutionMailing_repair) {
        this.institutionMailing_repair = institutionMailing_repair;
    }

    public String getInstitutionZipcode_repair() {
        return institutionZipcode_repair;
    }

    public void setInstitutionZipcode_repair(String institutionZipcode_repair) {
        this.institutionZipcode_repair = institutionZipcode_repair;
    }

    public String getInstitutionQualification_repair() {
        return institutionQualification_repair;
    }

    public void setInstitutionQualification_repair(String institutionQualification_repair) {
        this.institutionQualification_repair = institutionQualification_repair;
    }

    public String getInstitutionCode_repair() {
        return institutionCode_repair;
    }

    public void setInstitutionCode_repair(String institutionCode_repair) {
        this.institutionCode_repair = institutionCode_repair;
    }

    public String getExtractDate() {
        return extractDate;
    }

    public void setExtractDate(String extractDate) {
        this.extractDate = extractDate;
    }

    public String getExtractPerson() {
        return extractPerson;
    }

    public void setExtractPerson(String extractPerson) {
        this.extractPerson = extractPerson;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnPerson() {
        return returnPerson;
    }

    public void setReturnPerson(String returnPerson) {
        this.returnPerson = returnPerson;
    }

    public String getBEAN_TYPE() {
        return BEAN_TYPE;
    }

    public void setBEAN_TYPE(String BEAN_TYPE) {
        this.BEAN_TYPE = BEAN_TYPE;
    }
}
