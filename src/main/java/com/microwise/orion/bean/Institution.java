package com.microwise.orion.bean;

import java.util.Date;

/**
 * 单位bean
 *
 * @author liuzhu
 * @date 2015-11-10
 */
public class Institution {

    public static final int DESIGN_UNIT = 0;
    public static final int COLLECTION_UNIT = 1;
    public static final int REPAIR_UNIT = 2;


    /**
     * 自增id
     */
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 所在地
     */
    private String seat;

    /**
     * 通讯地址
     */
    private String mailing;

    /**
     * 邮编
     */
    private int zipcode;

    /**
     * 资质证书
     */
    private String qualification;

    /**
     * 代号
     */
    private String code;

    /**
     * 单位类型
     */
    private int institutionType;

    /**
     * 创建时间
     */
    private Date createDate;

    private boolean hasRoom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getMailing() {
        return mailing;
    }

    public void setMailing(String mailing) {
        this.mailing = mailing;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(int institutionType) {
        this.institutionType = institutionType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public boolean isHasRoom() {
        return hasRoom;
    }

    public void setHasRoom(boolean hasRoom) {
        this.hasRoom = hasRoom;
    }
}
