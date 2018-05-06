package com.microwise.blueplanet.bean.po;

/**
 * @author 王耕
 * @date 15-6-15
 */
public class DimensionalLocationPO {
    /** 自增id */
    private int id;

    /** 站点ID */
    private String siteId;

    /** 模型id */
    private int dimensionalId;

    /** 位置点id */
    private String locationId;

    /** 3d温湿度场坐标X */
    private float coordinateX;

    /** 3d温湿度场坐标Y */
    private float coordinateY;

    /** 3d温湿度场坐标Z */
    private float coordinateZ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getDimensionalId() {
        return dimensionalId;
    }

    public void setDimensionalId(int dimensionalId) {
        this.dimensionalId = dimensionalId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public float getCoordinateZ() {
        return coordinateZ;
    }

    public void setCoordinateZ(float coordinateZ) {
        this.coordinateZ = coordinateZ;
    }
}
