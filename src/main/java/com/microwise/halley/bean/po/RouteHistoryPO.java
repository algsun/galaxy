package com.microwise.halley.bean.po;

import java.util.Date;

/**
 * 光学摄像机点位
 *
 * @author xu.yuexi
 * @date 2013-11-14
 */
public class RouteHistoryPO {

    /**
     * id
     */
    private int id;

    /**
     * 对应车辆ID
     */
    private int CarId;

    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 时间
     */
    private Date routeTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return CarId;
    }

    public void setCarId(int carId) {
        CarId = carId;
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

    public Date getRouteTime() {
        return routeTime;
    }

    public void setRouteTime(Date routeTime) {
        this.routeTime = routeTime;
    }
}
