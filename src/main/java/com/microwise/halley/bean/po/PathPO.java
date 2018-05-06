package com.microwise.halley.bean.po;

/**
 * 外展预设路线实体类
 *
 * @author wanggeng
 * @date 13-9-25 下午4:01
 */
public class PathPO {

    /**
     * 序列号id
     */
    private int id;

    /**
     * 关联的外展ID h_exhibition.id
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

    /**
     * 点类型 1-停靠点 0-非停靠点
     */
    private int dataType;

    /**
     * 目的地
     */
    private String destinationName;

    private int pathType;

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

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getPathType() {
        return pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }
}
