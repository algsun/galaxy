package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Lists;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.service.ExhibitionStateService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 新增车辆
 *
 * @author wanggeng
 * @date 13-9-29 下午1:15
 * @check @li.jianfei #5842 2013-10-10
 */
@Beans.Action
@Halley
public class AddCarAction {
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../exhibition/add-car.ftl";

    /**
     * 常量摄像机类型
     */
    private static final int DVPLACE_TYPE = 1;

    /**
     * 常量位置点类型
     */
    private static final int LOCATION_TYPE = 2;


    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 车辆配置服务层对象
     */
    @Autowired
    private CarService carService;
    /**
     * 外展状态服务层对象
     */
    @Autowired
    private ExhibitionStateService exhibitionStateService;
    /**
     * 区域服务层对象
     */
    @Autowired
    private ZoneService zoneService;

    //input
    /**
     * 外展ID
     */
    private int exhibitionId;

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
     * 位置点ID数组
     */
    private String[] locationIds;


    //output
    /**
     * 区域列表
     */
    private List<Zone> zoneList;

    /**
     * 跳转至添加车辆信息页面
     *
     * @return string
     */
    @Route("/halley/exhibition/toAddCar/{exhibitionId}")
    public String execute() {
        try {
            // 获取当前站点id
            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();
            // 获取当前站点下的所有区域
            zoneList = zoneService.findHasOptics(siteId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 添加车辆信息
     *
     * @return string
     */
    @Route("/halley/exhibition/addCar/{exhibitionId}")
    public String addCar() {
        try {
            //车辆基本信息保存
            CarPO carPO = new CarPO();
            carPO.setExhibitionId(exhibitionId);
            carPO.setPlateNumber(plateNumber);
            carPO.setDirector(director);
            carPO.setDirectorPhone(directorPhone);
            List<CarPO> carPOList = carService.findAllCars();
            //判断车辆是否重复是否在使用
            for (CarPO car : carPOList) {
                //判断车牌是否重复
                if (car.getPlateNumber().equals(carPO.getPlateNumber())) {
                    //判断是否是同一次外展多次添加
                    if (exhibitionId == car.getExhibitionId()) {
                        ActionMessage.createByAction().fail("本次外展已添加过此车辆");
                        return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
                    }
                    //如果重复判断是否在使用
                    ExhibitionStateVO stateVO = exhibitionStateService.findCurrentState(car.getExhibitionId());
                    if (stateVO == null) {
                        ActionMessage.createByAction().fail("车辆正在使用中");
                        return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
                    }
                    int state = stateVO.getState();
                    if (state != 4) {
                        ActionMessage.createByAction().fail("车辆正在使用中");
                        return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
                    }
                }
            }
            carService.saveCarPO(carPO);

            //摄像机设备与传感设备信息保存
            int carId = carPO.getId();

            List<DevicePO> devices = Lists.newArrayList();
            DevicePO device;
            if (dvIds != null) {
                for (String deviceId : dvIds) {
                    device = new DevicePO();
                    device.setDeviceId(deviceId);
                    device.setCarId(carId);
                    device.setDeviceType(DVPLACE_TYPE);
                    devices.add(device);
                }
            }
            if (locationIds != null) {
                for (String deviceId : locationIds) {
                    device = new DevicePO();
                    device.setDeviceId(deviceId);
                    device.setCarId(carId);
                    device.setDeviceType(LOCATION_TYPE);
                    devices.add(device);
                }
            }
            carService.saveDeviceList(devices);

            ActionMessage.createByAction().success("车辆信息添加成功");
            halleyLogger.log("外展管理", "新建车辆");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("车辆信息添加失败");
            halleyLogger.logFailed("外展管理", "新建车辆");
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

    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
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

    public String[] getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(String[] locationIds) {
        this.locationIds = locationIds;
    }
}
