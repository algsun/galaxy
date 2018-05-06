package com.microwise.halley.bean.vo;

/**
 * GPS点VO
 *
 * @author xu.yuexi
 * @date 2013-11-12
 */
public class PathPointsVO {

    /**
     * 记录ID
     */
    private int id;

    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
