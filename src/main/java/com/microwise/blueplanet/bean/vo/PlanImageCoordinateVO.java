package com.microwise.blueplanet.bean.vo;

/**
 * 平面图坐标 VO
 *
 * @author li.jianfei
 * @date 2014-07-04
 */
public class PlanImageCoordinateVO {

    public static final int ZONE_TYPE = 1;

    public static final int LOCATION_TYPE = 2;

    public static final int DV_PLACE_TYPE = 3;

    /**
     * UUID
     */
    private String id;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 部署到区域上的对象的ID
     */
    private String objectId;

    /**
     * 部署对象类型
     * <p/>
     * 1-区域；2-位置点；3-摄像机
     */
    private int type;

    /**
     * X坐标
     */
    private float coordinateX;

    /**
     * Y坐标
     */
    private float coordinateY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
