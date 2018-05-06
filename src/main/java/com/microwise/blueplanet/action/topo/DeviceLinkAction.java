package com.microwise.blueplanet.action.topo;

import com.bastengao.struts2.freeroute.Results;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.TopoViewVO;
import com.microwise.blueplanet.service.TopoService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 设备链路
 *
 * @author liuzhu
 * @date 2013-10-14
 */

@Beans.Action
@Blueplanet
public class DeviceLinkAction extends BlueplanetLoggerAction {
    /**
     * 页面类容
     */
    private static final String _pagePath = "device-link.ftl";

    /**
     * 超时设备service
     */
    @Autowired
    private TopoService topoService;


    // input
    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 初始页（分页）
     */
    private Integer index = 1;

    /**
     * 总页数
     */
    private Integer pageCount;

    /**
     * 每页显示的条数
     */
    private Integer pageSize = 10;

    /**
     * 总计数
     */
    private Integer count;

    // output
    /**
     * 超时设备voList
     */
    private List<TopoViewVO> deviceLinkList;

    /**
     * 所有设备信息
     */
    private List<TopoViewVO> topoNodes;

    /**
     * 设备id
     */
    private String nodeId;

    /**
     * 设备丢包率对象
     */
    private  TopoViewVO topoLossPackage;


    /**
     *
     * 设备链路view
     *
     * @author liuzhu
     * @date 2013-10-08
     */
    public String view() {
        initDate();
        topoNodes = topoService.getDevices(Sessions.createByAction().currentSiteId());
        if (topoNodes.size() != 0) {
            if (Strings.isNullOrEmpty(nodeId)) {
                nodeId = topoNodes.get(0).getNodeId();
            }
            topoLossPackage =  topoService.getLoseRate(startDate,endDate,nodeId);
            count = topoService.getHistoryRouteCount(nodeId, startDate, endDate);
            pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            deviceLinkList = topoService.getHistoryRoute(nodeId, startDate, endDate, (index - 1) * pageSize, pageSize);
            log("监控", "设备链路");
        }
        return Results.ftl("/blueplanet/pages/topo/layout");
    }

    /**
     * 初始化开始结束时间
     */
    private void initDate() {
        if (startDate == null || endDate == null) {
            startDate = DateTime.now().withTimeAtStartOfDay().toDate();
            endDate = DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<TopoViewVO> getDeviceLinkList() {
        return deviceLinkList;
    }

    public void setDeviceLinkList(List<TopoViewVO> deviceLinkList) {
        this.deviceLinkList = deviceLinkList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public TopoViewVO getTopoLossPackage() {
        return topoLossPackage;
    }
}
