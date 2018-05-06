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
 * 超时设备
 *
 * @author liuzhu
 * @date 13-9-27
 * @check @xiedeng 2013-10-11 #5895
 */

@Beans.Action
@Blueplanet
public class TimeoutDeviceAction extends BlueplanetLoggerAction {
    /**
     * 页面类容
     */
    private static final String _pagePath = "timeout-device.ftl";

    /**
     * 超时设备service
     */
    @Autowired
    private TopoService topoService;

    // output
    /**
     * 超时设备voList
     */
    private List<TopoViewVO> topoViewVOList;

    /**
     * 站点下所有设备
     */
    private List<TopoViewVO> topoNodes;

    /**
     * 超时设备view
     *
     * @author liuzhu
     * @date 2013-10-08
     */
    public String view() {
        topoViewVOList = topoService.getTimeoutDevice(Sessions.createByAction().currentSiteId());
        log("监控", "网络拓扑图");
        return Results.ftl("/blueplanet/pages/topo/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<TopoViewVO> getTopoViewVOList() {
        return topoViewVOList;
    }

    public void setTopoViewVOList(List<TopoViewVO> topoViewVOList) {
        this.topoViewVOList = topoViewVOList;
    }

    public List<TopoViewVO> getTopoNodes() {
        return topoNodes;
    }

    public void setTopoNodes(List<TopoViewVO> topoNodes) {
        this.topoNodes = topoNodes;
    }
}
