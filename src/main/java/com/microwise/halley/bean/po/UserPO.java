package com.microwise.halley.bean.po;

/**
 * 外展报警通知人员实体类
 *
 * @author wanggeng
 * @date 13-9-25 下午3:45
 */
public class UserPO {

    /** 序列号id */
    private int id;

    /** 关联的外展ID h_exhibition.id */
    private int exhibitionId;

    /** 人员ID */
    private int userId;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
