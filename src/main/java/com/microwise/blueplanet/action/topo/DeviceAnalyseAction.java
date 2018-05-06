package com.microwise.blueplanet.action.topo;

import com.bastengao.struts2.freeroute.Results;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.TopoViewVO;
import com.microwise.blueplanet.service.TopoService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 设备分析
 *
 * @author liuzhu
 * @date 13-9-27
 * @check @xiedeng 2013-10-11 #5895
 */

@Beans.Action
@Blueplanet
public class DeviceAnalyseAction extends BlueplanetLoggerAction {


    /**
     * 内容页面
     */
    private static final String _pagePath = "device-analyse.ftl";

    /**
     * 设备分析service
     */
    @Autowired
    private TopoService topoService;

    // output
    /**
     * 设备负载量
     */
    private List<TopoViewVO> loadDeviceList;

    /**
     * 设备信号质量
     */
    private List<TopoViewVO> rssiList;

    // input
    /**
     * 设备显示的数量（默认为10）
     */
    private int dataSize = 10;

    /**
     * 设备状态(0-全部，1-正常)
     */
    private int anomaly = 0;

    /**
     * 设备分析view
     *
     * @author liuzhu
     * @date 2013-10-10
     */
    public String view() {
        String siteId = Sessions.createByAction().currentSiteId();
        loadDeviceList = topoService.getLoadDevices(siteId, anomaly);
        rssiList = topoService.getRssi(siteId, anomaly);
        if (loadDeviceList.size() >= dataSize) {
            loadDeviceList = loadDeviceList.subList(0, dataSize);
        }
        if (rssiList.size() >= dataSize) {
            rssiList = rssiList.subList(0, dataSize);
        }
        log("监控", "网络拓扑图");
        return Results.ftl("/blueplanet/pages/topo/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<TopoViewVO> getLoadDeviceList() {
        return loadDeviceList;
    }

    public void setLoadDeviceList(List<TopoViewVO> loadDeviceList) {
        this.loadDeviceList = loadDeviceList;
    }

    public List<TopoViewVO> getRssiList() {
        return rssiList;
    }

    public void setRssiList(List<TopoViewVO> rssiList) {
        this.rssiList = rssiList;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(int anomaly) {
        this.anomaly = anomaly;
    }
}
