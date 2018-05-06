package com.microwise.uma.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * 人员实体
 *
 * @author xubaoji
 * @date 2013-4-15
 */
@JsonIgnoreProperties({"email", "photo", "sex", "cardSn", "voltage", "lastTime"})
public class PersonBean {

    // 人员编号
    private Integer id;

    // 人员名称
    private String personName;

    // 邮箱
    private String email;

    // 照片
    private String photo;

    // 人员性别 1：女 2： 男
    private Integer sex;

    // 人员 佩戴电子 卡 编号
    private String cardSn;

    // 电子卡电量
    private float voltage;

    // 最后一次出现时间
    private Date lastTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
