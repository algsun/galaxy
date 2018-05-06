package com.microwise.halley.bean.vo;

import java.util.Date;

/**
 * 报警实体 Bean
 *
 * @author li.jianfei
 * @date 2014-05-19
 */
public class AlarmVO {

    /**
     * 自增ID
     */
    private int id;

    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 报警内容
     */
    private String content;

    /**
     * 联系人(短信通知人)
     */
    private int contact;// t_user.id

    /**
     * 短信发送时间
     */
    private Date sendTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
