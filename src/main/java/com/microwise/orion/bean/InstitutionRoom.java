package com.microwise.orion.bean;

/**
 * 单位库房bean
 *
 * @author liuzhu
 * @date 2015-11-10
 */
public class InstitutionRoom {

    /**
     * 自增id
     */
    private int id;

    /**
     * 单位id
     */
    private int institutionId;

    /**
     * 库房名字
     */
    private String roomName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
