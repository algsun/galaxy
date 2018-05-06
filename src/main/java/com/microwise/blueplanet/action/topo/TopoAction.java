package com.microwise.blueplanet.action.topo;

import com.bastengao.struts2.freeroute.Results;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.TopoViewVO;
import com.microwise.blueplanet.service.TopoService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络拓扑图
 *
 * @author liuzhu
 * @date 13-9-27
 * @check @xiedeng 2013-10-11 #5895
 */
@Beans.Action
@Blueplanet
public class TopoAction extends BlueplanetLoggerAction {

    /**
     * 内容页面
     */
    private static final String _pagePath = "topo.ftl";

    /**
     * 拓扑图Service
     */
    @Autowired
    private TopoService topoService;

    // input
    /**
     * 设备id
     */
    private String nodeId;

    /**
     * 站点下所有设备
     */
    private List<TopoViewVO> topoNodes;

    /**
     * 拓扑图数据
     */
    private String topoData = "";

    /**
     * 拓扑图关系数据
     */
    private String topoReferencesData = "";

    /**
     * 网关数量
     */
    private int gatewaySum;

    /**
     * 中继数量
     */
    private int relaySum;

    /**
     * 节点数量
     */
    private int nodeSum;

    /**
     * 主模块数量
     */
    private int mainModuleSum;

    /**
     * 从模块数量
     */
    private int childModuleSum;

    /**
     * 控制模块数量
     */
    private int controlModuleSum;

    /**
     * 设备总数
     */
    private int allDeviceCount;

    /**
     * 拓扑图VO类
     */
    private TopoViewVO topoViewVO;

    /**
     *网页刷新传递数据
     * */
    private Map<Integer, Integer>  deviceCountMap;

    /**
     * 网络拓扑图view
     *
     * @author liuzhu
     * @date 2013-10-08
     */
    public String view() {

        topoNodes = topoService.getDevices(Sessions.createByAction().currentSiteId());
        if (topoNodes.size() != 0) {
            if (Strings.isNullOrEmpty(nodeId)) {
                nodeId = topoNodes.get(0).getNodeId();
            }
            getData(nodeId);
        }
        log("监控", "网络拓扑图");
        return Results.ftl("/blueplanet/pages/topo/layout");

    }

    /**
     * ajax根据nodeId获取设备信息
     *
     * @author liuzhu
     * @date 2013-10-10
     */
    public String getDeviceInfoById() {
        topoViewVO = topoService.getNodeInfoByNodeId(nodeId);
        return Action.SUCCESS;
    }

    /**
     * 页面刷新传递数据
     * @author chenyaofei
     * @date 20160812
     */
    public String refreshShowData() {
        getData(nodeId);
        return Action.SUCCESS;
    }


    /**
     * 组织数据
     *
     * @author chenyaofei
     * @date 20160812
     */
    private void getData(String nodeId){
        List<Object> list = topoService.getTopoData(nodeId);
        dataToJson(list);
        deviceCountMap = topoService.getDeviceCount();
        gatewaySum = deviceCountMap.get(Constants.GATEWAY);
        relaySum = deviceCountMap.get(Constants.RELAY);
        nodeSum = deviceCountMap.get(Constants.NODE);
        mainModuleSum = deviceCountMap.get(Constants.MAIN_MODULE);
        childModuleSum = deviceCountMap.get(Constants.CHILD_MODULE);
        controlModuleSum = deviceCountMap.get(Constants.CONTROL_MODULE);
    }

    /**
     * 将数据封装成json格式
     *
     * @author liuzhu
     * @date 2013-10-08
     */
    private void dataToJson(List<Object> list) {
        Gson gson = new Gson();
        List<TopoViewVO> topoViewVOs = (List<TopoViewVO>) list.get(0);//网络拓扑图数据
        List<Map<String, String>> topoReferencs = (List<Map<String, String>>) list.get(1);//网络拓扑图关系
        allDeviceCount = topoViewVOs.size();
        List topoViewVOList = new ArrayList();
        for (TopoViewVO topoViewVO : topoViewVOs) {
            Map<String, String> dataStringTopoView = new HashMap<String, String>();
            Map<String, Map<String, String>> dataMapTopoView = new HashMap<String, Map<String, String>>();
            dataStringTopoView.put("id", topoViewVO.getNodeId());
            dataStringTopoView.put("name", topoViewVO.getNodeId().substring(8));
            dataStringTopoView.put("weight", "60");
            dataStringTopoView.put("backgroundImage", "images/icon/" + getIconName(topoViewVO.getNodeType(), topoViewVO.getAnomaly()));
            dataStringTopoView.put("faveShape", "roundrectangle");
            dataStringTopoView.put("faveColor", "#FFFFFF");
            dataMapTopoView.put("data", dataStringTopoView);
            topoViewVOList.add(dataMapTopoView);
        }
        topoData = gson.toJson(topoViewVOList);

        List<Object> listReferencs = new ArrayList<Object>();
        for (Map topoReferencsMap : topoReferencs) {
            Map<String, String> dataStringReferencs = new HashMap<String, String>();
            Map<String, Map<String, String>> dataMapReferencs = new HashMap<String, Map<String, String>>();
            dataStringReferencs.put("source", topoReferencsMap.get("source").toString());
            dataStringReferencs.put("target", topoReferencsMap.get("target").toString());
            dataStringReferencs.put("lineColor", topoReferencsMap.get("color").toString());
            dataMapReferencs.put("data", dataStringReferencs);
            listReferencs.add(dataMapReferencs);
        }
        topoReferencesData = gson.toJson(listReferencs);
    }

    /**
     * 根据nodeId，rssi返回icon名称
     *
     * @author liuzhu
     * @date 2013-10-08
     */
    private String getIconName(int nodeType, int anomaly) {
        String iconName = "";

        switch (nodeType) {
            case 1:
            case 4:
                iconName = setIconName(anomaly, iconName, "node-24-red.png", "node-24.png", "node-24-yellow.png");
                break;
            case 2:
                iconName = setIconName(anomaly, iconName, "relay-24-red.png", "relay-24.png", "relay-24-yellow.png");
                break;
            case 3:
                iconName = setIconName(anomaly, iconName, "master-module-24-red.png", "master-module-24.png", "master-module-24-yellow.png");
                break;
            case 5:
                iconName = setIconName(anomaly, iconName, "control-module-24-red.png", "control-module-24.png", "control-module-24-yellow.png");
                break;
            case 7:
                iconName = setIconName(anomaly, iconName, "router-24-red.png", "router-24.png", "router-24-yellow.png");
                break;
        }
        return iconName;
    }

    /**
     * 判断设置图片名称
     *
     * @param anomaly  setIconName
     * @param iconName icon图片名称
     * @param red      红色图片
     * @param normal   正常图片
     * @param yellow   黄色图片
     * @return 图片名称
     */
    private String setIconName(int anomaly, String iconName, String red, String normal, String yellow) {
        switch (anomaly) {
            case -1:
                iconName = red;
                break;
            case 0:
                iconName = normal;
                break;
            case 1:
            case 2:
                iconName = yellow;
                break;
        }
        return iconName;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<TopoViewVO> getTopoNodes() {
        return topoNodes;
    }

    public void setTopoNodes(List<TopoViewVO> topoNodes) {
        this.topoNodes = topoNodes;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTopoData() {
        return topoData;
    }

    public void setTopoData(String topoData) {
        this.topoData = topoData;
    }

    public String getTopoReferencesData() {
        return topoReferencesData;
    }

    public void setTopoReferencesData(String topoReferencesData) {
        this.topoReferencesData = topoReferencesData;
    }

    public int getGatewaySum() {
        return gatewaySum;
    }

    public void setGatewaySum(int gatewaySum) {
        this.gatewaySum = gatewaySum;
    }

    public int getRelaySum() {
        return relaySum;
    }

    public void setRelaySum(int relaySum) {
        this.relaySum = relaySum;
    }

    public int getNodeSum() {
        return nodeSum;
    }

    public void setNodeSum(int nodeSum) {
        this.nodeSum = nodeSum;
    }

    public int getMainModuleSum() {
        return mainModuleSum;
    }

    public void setMainModuleSum(int mainModuleSum) {
        this.mainModuleSum = mainModuleSum;
    }

    public int getChildModuleSum() {
        return childModuleSum;
    }

    public void setChildModuleSum(int childModuleSum) {
        this.childModuleSum = childModuleSum;
    }

    public TopoViewVO getTopoViewVO() {
        return topoViewVO;
    }

    public void setTopoViewVO(TopoViewVO topoViewVO) {
        this.topoViewVO = topoViewVO;
    }

    public int getAllDeviceCount() {
        return allDeviceCount;
    }

    public void setAllDeviceCount(int allDeviceCount) {
        this.allDeviceCount = allDeviceCount;
    }

    public int getControlModuleSum() {
        return controlModuleSum;
    }

    public void setControlModuleSum(int controlModuleSum) {
        this.controlModuleSum = controlModuleSum;
    }

    public Map<Integer, Integer> getDeviceCountMap() {
        return deviceCountMap;
    }
}
