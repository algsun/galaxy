package com.microwise.halley.bean.po;

/**
 * 车辆实体类
 *
 * @author wanggeng
 * @date 13-9-25 下午3:40
 */
public class CarPO {

    /** 车辆id */
    private int id;

    /** 关联的外展ID h_exhibition.id */
    private int exhibitionId;

    /** 车牌号码(唯一的，但不是主键) */
    private String plateNumber;

    /** 责任人 */
    private String director;

    /** 责任人电话 */
    private String directorPhone;

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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirectorPhone() {
        return directorPhone;
    }

    public void setDirectorPhone(String directorPhone) {
        this.directorPhone = directorPhone;
    }
}
