package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑车辆信息Action.
 *
 * @author wang.geng
 * @date 13-10-9  上午9:17
 * @check @li.jianfei #5842 2013-10-10
 */
@Beans.Action
@Halley
public class EditCarInfoAction {
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../exhibition/edit-car.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 区域服务层对象
     */
    @Autowired
    private ZoneService zoneService;

    /**
     * 车辆配置服务层对象
     */
    @Autowired
    private CarService carService;

    //input
    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 车辆ID
     */
    private int carId;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 责任人
     */
    private String director;

    /**
     * 责任人电话
     */
    private String directorPhone;

    /**
     * 摄像机ID数组
     */
    private String[] dvIds;

    /**
     * 设备ID数组
     */
    // TODO 修改页面
    private String[] nodeIds;

    //output

    /**
     * 区域列表
     */
    private List<Zone> zoneList;

    /**
     * 车辆信息业务对象，包含设备信息
     */
    private CarVO carVO;

    /**
     * 已选摄像机设备数目
     */
    private int dvCount;

    /**
     * 已选传感设备数目
     */
    private int nodeCount;

    /**
     * 跳转至车辆信息编辑页面
     *
     * @return string
     */
    @Route("/halley/exhibition/toEditCarInfo/{exhibitionId}/{carId}")
    public String toEditCarInfo() {
        try {
            // 获取当前站点下的所有区域
            zoneList = zoneService.findHasOptics(Sessions.createByAction().currentSiteId());

            //计算摄像机设备与传感设备各自的数量
            List<CarVO> carVOList = carService.findCarsWithDeviceByExhibitionId(exhibitionId);
            for (CarVO car : carVOList) {
                if (car.getId() == carId) {
                    carVO = car;
                    dvCount = carVO.getDevicePOList() != null ? carVO.getDevicePOList().size() : 0;
                    nodeCount = carVO.getLocationVOs() != null ? carVO.getLocationVOs().size() : 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 编辑车辆信息
     *
     * @return string
     */
    @Route("/halley/exhibition/editCarInfo/{exhibitionId}/{carId}")
    public String editCarInfo() {
        try {

            //更新车辆基本信息
            CarPO carPO = new CarPO();
            carPO.setId(carId);
            carPO.setDirector(director);
            carPO.setPlateNumber(plateNumber);
            carPO.setDirectorPhone(directorPhone);
            carService.updateCarPO(carPO);

            //更新摄像机设备与传感设备信息
            List<DevicePO> devices = new ArrayList<DevicePO>();
            if (dvIds == null && nodeIds == null) {
                carService.deleteDeviceByCarId(carId);
                return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
            }

            if (dvIds != null) {
                for (String dvId : dvIds) {
                    DevicePO devicePO = new DevicePO();
                    devicePO.setCarId(carId);
                    devicePO.setDeviceId(dvId);
                    // 摄像机
                    devicePO.setDeviceType(1);
                    devices.add(devicePO);
                }
            }
            if (nodeIds != null) {
                for (String nodeId : nodeIds) {
                    DevicePO devicePO = new DevicePO();
                    devicePO.setCarId(carId);
                    devicePO.setDeviceId(nodeId);
                    // 位置点
                    devicePO.setDeviceType(2);
                    devices.add(devicePO);
                }
            }
            if (devices.size() > 0) {
                carService.updateDeviceList(devices, carId);
            }
            ActionMessage.createByAction().success("车辆信息更新成功");
            halleyLogger.log("外展配置", "编辑车辆信息");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("车辆信息更新失败");
            halleyLogger.logFailed("外展配置", "编辑车辆信息");
            e.printStackTrace();
        }
        return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public CarVO getCarVO() {
        return carVO;
    }

    public void setCarVO(CarVO carVO) {
        this.carVO = carVO;
    }

    public int getDvCount() {
        return dvCount;
    }

    public void setDvCount(int dvCount) {
        this.dvCount = dvCount;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
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

    public String[] getDvIds() {
        return dvIds;
    }

    public void setDvIds(String[] dvIds) {
        this.dvIds = dvIds;
    }

    public String[] getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(String[] nodeIds) {
        this.nodeIds = nodeIds;
    }
}
