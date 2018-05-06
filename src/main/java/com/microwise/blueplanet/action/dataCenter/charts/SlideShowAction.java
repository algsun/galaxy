package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCSlidePO;
import com.microwise.blueplanet.bean.vo.NodeSensorVO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 幻灯片action
 *
 * @author liuzhu
 * @date 14-2-11
 * @check li.jianfei 2014-3-5 #8049
 */
@Beans.Action
@Blueplanet
public class SlideShowAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(SlideShowAction.class);

    /**
     * 信息发布service
     */
    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private LocationService locationService;

    //input
    /**
     * layoutId
     */
    private String layoutId;

    /**
     * 幻灯片id
     */
    private int id;

    //output
    /**
     * 幻灯片 map（key：属性，value：值）
     */
    private Map<String, Object> slideShowVOListMap;

    /**
     * 幻灯片map集合（key：幻灯片id，object:每个片子的id）
     */
    private Map<String, Object> listMap;

    @Route("/blueplanet/dataCenter/slideShow/{layoutId}")
    public String findSlideShow() {
        try {
            listMap = dataCenterService.findSlideShowId(layoutId);
        } catch (Exception e) {
            log.error("查询所有幻灯片失败", e);
        }
        log("幻灯片概览", "信息发布");
        return Results.json().root("listMap").done();
    }

    @Route("/blueplanet/dateCenter/refreshSlideShow/{id}")
    public String refreshSlideShow() {
        try {
            DCSlidePO dcSlidePO = dataCenterService.findSlideShowById(id);
            String locationId = dcSlidePO.getLocationId();
            String nodeId = locationService.findLocationById(locationId).getNodeId();
            List<NodeSensorVO> nodeSensorVOList = dataCenterService.findNodeSensorVO(nodeId);
            slideShowVOListMap = new HashMap<String, Object>();
            String resourcesRul = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "blueplanet" + File.separator + "images" + File.separator + "slide" + File.separator;
            dcSlidePO.setUrl(resourcesRul + dcSlidePO.getUrl());
            slideShowVOListMap.put("slideShow", dcSlidePO);
            slideShowVOListMap.put("nodeSensor", nodeSensorVOList);
            String stampTime = getLastStampTime(nodeSensorVOList);
            slideShowVOListMap.put("stampTime", stampTime);
        } catch (Exception e) {
            log.error("查询单个幻灯片失败", e);
        }
        return Results.json().root("slideShowVOListMap").done();
    }

    /**
     * 获取本次刷新中，最后一次刷新的监测指标的采样时间
     *
     * @param nodeSensorVOList
     * @return
     */
    private String getLastStampTime(List<NodeSensorVO> nodeSensorVOList) {
        SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lastMills = 1l;
        for (NodeSensorVO nodeSensorVO : nodeSensorVOList) {
            if (nodeSensorVO == null || nodeSensorVO.getStamp() == null) {
                continue;
            }
            long stampMills = nodeSensorVO.getStamp().getTime();
            if (stampMills > lastMills) {
                lastMills = stampMills;
            }
        }
        return FULL_FORMAT.format(new Date(lastMills));
    }

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public Map<String, Object> getSlideShowVOListMap() {
        return slideShowVOListMap;
    }

    public void setSlideShowVOListMap(Map<String, Object> slideShowVOListMap) {
        this.slideShowVOListMap = slideShowVOListMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Object> getListMap() {
        return listMap;
    }

    public void setListMap(Map<String, Object> listMap) {
        this.listMap = listMap;
    }
}
