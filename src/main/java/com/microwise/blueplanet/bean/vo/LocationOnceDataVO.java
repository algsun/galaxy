package com.microwise.blueplanet.bean.vo;

/**
 * 位置点一个监测指标的一条数据
 *
 * @author xuyuexi
 * @date 14-7-28
 */
public class LocationOnceDataVO {

    /**
     * 位置点
     */
    private LocationVO location;

    /**
     * 数据
     */
    private LocationDataVO data;

    public LocationOnceDataVO() {
    }

    public LocationOnceDataVO(LocationVO location, LocationDataVO data) {
        this.location = location;
        this.data = data;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public LocationDataVO getData() {
        return data;
    }

    public void setData(LocationDataVO data) {
        this.data = data;
    }
}
