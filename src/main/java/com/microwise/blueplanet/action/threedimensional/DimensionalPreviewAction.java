package com.microwise.blueplanet.action.threedimensional;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DimensionalLocationPO;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;
import com.microwise.blueplanet.bean.vo.DimensionalLocationVO;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ThreeDimensionalService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王耕
 * @date 15-6-11
 */
@Beans.Action
@Blueplanet
public class DimensionalPreviewAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(BlueplanetLoggerAction.class);

    @Autowired
    private ThreeDimensionalService threeDimensionalService;

    @Autowired
    private LocationService locationService;

    // 内容页面
    private static final String _pagePath = "dimensional-field.ftl";

    /**
     * 模型文件id
     */
    private int dimensionalId;

    /**
     * 温度场或湿度场监测指标ID
     */
    private int sensorId = 33;

    /**
     * 查询场数据时间
     */
    private Date stamp;

    /**
     * 是否是查询按钮提交的请求
     * true:是，false:否
     */
    private boolean submit;

    /**
     * 模型文件实体类
     */
    private ThreeDimensionalPO threeDimensional;

    @Route("/blueplanet/three-dimensional/dimensionalPreview")
    public String dimensionalPreview(){
        if(stamp == null){
            stamp = new Date();
        }
        threeDimensional = threeDimensionalService.findThreeDimenById(dimensionalId);
        String path = Sessions.createByAction().getGalaxyResourceURL()  + "/blueplanet/file/threedimensional";
        threeDimensional.setPath(path+ "/" + threeDimensional.getPath());
        return Results.ftl("/blueplanet/pages/threedimensional/layout");
    }

    @Route("/blueplanet/three-dimensional/disposePoints.json")
    public String disposePoints() {
        try {
            List<Integer> senserIds = new ArrayList<Integer>();

            senserIds.add(sensorId);

            String siteId = Sessions.createByAction().currentSiteId();

            List<DimensionalLocationPO> dimensionalLocationPOs = threeDimensionalService.findDimensionalLocationRelations(siteId, dimensionalId);
            List<DimensionalLocationVO> dimensionalLocationVOs = new ArrayList<DimensionalLocationVO>();

            for(DimensionalLocationPO dimensionalLocation: dimensionalLocationPOs){
                DimensionalLocationVO dimensionalLocationVO = new DimensionalLocationVO();
                dimensionalLocationVO.setCoordinateX(dimensionalLocation.getCoordinateX());
                dimensionalLocationVO.setCoordinateY(dimensionalLocation.getCoordinateY());
                dimensionalLocationVO.setCoordinateZ(dimensionalLocation.getCoordinateZ());

                String locationId = dimensionalLocation.getLocationId();
                LocationDataVO locationDataVO = null;
                if(submit){
                    if(stamp == null){
                        stamp = new Date();
                    }
                    //提交的请求查询离请求时间参数最近的一包数据
                    locationDataVO = locationService.findLocationSensorNearBy(locationId,sensorId,stamp);
                }else{

                    //默认进入时，查询的是位置点的实时数据
                    locationDataVO = locationService.findLocationSensor(locationId,senserIds).get(0);
                }

                dimensionalLocationVO.setLocationDataVO(locationDataVO);
                dimensionalLocationVOs.add(dimensionalLocationVO);
            }


            return Results.json().asRoot(dimensionalLocationVOs).done();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.json().asRoot(null).done();
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getDimensionalId() {
        return dimensionalId;
    }

    public void setDimensionalId(int dimensionalId) {
        this.dimensionalId = dimensionalId;
    }

    public ThreeDimensionalPO getThreeDimensional() {
        return threeDimensional;
    }

    public void setThreeDimensional(ThreeDimensionalPO threeDimensional) {
        this.threeDimensional = threeDimensional;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public boolean isSubmit() {
        return submit;
    }

    public void setSubmit(boolean submit) {
        this.submit = submit;
    }
}
