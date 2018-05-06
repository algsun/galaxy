package com.microwise.halley.bean.po;

/**
 * 外展设备实体类
 *
 * @author wanggeng
 * @date 13-9-25 下午3:58
 */
public class DevicePO {

    /**
     * 序列号id
     */
    private int id;

    /**
     * 关联的车辆ID h_car.id
     */
    private int carId;

    /**
     * 设备类型
     */
    private int deviceType;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 摄像机名称
     */
    private String dvName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDvName() {
        return dvName;
    }

    public void setDvName(String dvName) {
        this.dvName = dvName;
    }

}
