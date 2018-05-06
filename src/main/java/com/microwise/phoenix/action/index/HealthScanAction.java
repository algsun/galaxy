package com.microwise.phoenix.action.index;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Inventory;
import com.microwise.orion.service.InventoryService;
import com.microwise.phoenix.bean.vo.HealthCheckItem;
import com.microwise.phoenix.bean.vo.healthCheck.Device;
import com.microwise.phoenix.bean.vo.healthCheck.FTP;
import com.microwise.phoenix.service.HealthCheckService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.microwise.uma.bean.PersonBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 健康扫描
 *
 * @author duan.qixin
 * @date 2013-07-25
 * @check @wanggeng 2013年7月29日 #4703
 */
@Beans.Action
@Phoenix
public class HealthScanAction extends PhoenixLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(HealthScanAction.class);

    public static final String _pagePath = "../index/health-scan.ftl";

    @Autowired
    private HealthCheckService service;

    /**
     * 盘点相关 service
     */
    @Autowired
    private InventoryService inventoryService;

    //input
    /**
     * 监测项目标示
     */
    private String checkFlag;

    //output
    /**
     * 项目数据
     */
    private List<HealthCheckItem> items;
    /**
     * 项目监测数量
     */
    private int itemCount;
    /**
     * 返回json数据
     */
    private List<Integer> jsonCount;

    /**
     * 异常详细数据
     */
    private List<String[]> errorData;


    //获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();
    //博物馆名称
    private String groupName = Sessions.createByAction().currentLogicGroup().getLogicGroupName();

    /**
     * 健康指数扫描页面请求
     *
     * @return
     */
    @Route("/phoenix/index/health")
    public String view() {
        items = service.findHealthCheckItem();
        itemCount = service.findHealthCheckItemCount();
        log("数据分析", "健康指数");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 异常信息总数统计
     *
     * @return
     */
    @Route("/phoenix/index/count.json")
    public String getCountData() {
        //总检测项
        itemCount = service.findHealthCheckItemCount();
        if (itemCount == 0) {
            itemCount = 1;
        }
        int num = (int) 100 / itemCount;

        if ("device".equals(checkFlag)) {//设备检测
            jsonCount = service.blueplanetDeviceCheck(siteId, num);
        } else if ("ftp".equals(checkFlag)) {//ftp服务器检测
            jsonCount = service.proximaFtpCheck(siteId, num);
        } else if ("inventory".equals(checkFlag)) {//盘点检测
            jsonCount = service.orionLastInventoryCheck(siteId, num);
            /**}else if("reader".equals(checkFlag)) {//读卡器检测*/
        } else if ("ecard".equals(checkFlag)) {//电子卡检测
            jsonCount = service.umaUserCardCheck(siteId, num);
        }
        log("数据分析", "健康指数");
        return Results.json().root("jsonCount").done();
    }

    /**
     * 具体异常信息加载
     *
     * @return
     */
    @Route("/phoenix/index/item.json")
    public String getData() {
        if ("device".equals(checkFlag)) {//设备检测
            List<Device> device = service.blueplanetFindErrorDevices(siteId);
            //JSON数据封装
            if (device != null && device.size() > 0) {
                errorData = new ArrayList<String[]>();
                String[] th = {"设备名称", "设备类型", "设备隶属区域名", "设备异常状态"};
                errorData.add(th);
                for (Device d : device) {
                    String[] td = {
                            getNodeName(d),
                            getNodeType(d.getNodeType()),
                            getNodeZoneName(d),
                            getAnomaly(d.getAnomaly())
                    };
                    errorData.add(td);
                }
            }
        } else if ("ftp".equals(checkFlag)) {//ftp服务器检测
            List<FTP> ftps = service.proximaFindErrorFtps(siteId);
            //JSON数据封装
            if (ftps != null && ftps.size() > 0) {
                errorData = new ArrayList<String[]>();
                String[] th = {"名称", "主机(IP)", "端口", "FTP异常信息"};
                errorData.add(th);
                for (FTP f : ftps) {
                    String[] td = {f.getName(), f.getHost(), String.valueOf(f.getPort()), f.getErrorInfo()};
                    errorData.add(td);
                }
            }
        } else if ("inventory".equals(checkFlag)) {//盘点检测
            Inventory inventory = inventoryService.findLastInventory(siteId);
            errorData = new ArrayList<String[]>();
            String[] th = {"截止时间", "总数", "在库", "出库", "标签", "扫描", "异常", "操作"};
            String[] td = {inventory.getDeadlineDate().toString(), inventory.getSumCount().toString(), inventory.getStockInCount().toString(),
                    inventory.getStockOutCount().toString(), inventory.getTagCount().toString(), inventory.getScanCount().toString(),
                    inventory.getErrorCount().toString(), "<a class='btn btn-mini' " +
                    "href='../orion/inventoryCheckInfo.action?id=" + inventory.getId() + "'>" +
                    "详情</a>"};
            errorData.add(th);
            errorData.add(td);
        } else if ("ecard".equals(checkFlag)) {//电子卡检测
            List<PersonBean> pbs = service.umaFindErrorUserCard(siteId);
            //JSON数据封装
            if (pbs != null && pbs.size() > 0) {
                errorData = new ArrayList<String[]>();
                String[] th = {"人员名称", "邮箱", "电子卡编号", "电子卡电量", "最后一次出现时间"};
                errorData.add(th);
                String lastTime = "暂无数据";
                for (PersonBean p : pbs) {
                    if (p.getLastTime() != null) {
                        lastTime = new SimpleDateFormat("yyyy-MM-dd").format(p.getLastTime());
                    }
                    String[] td = {p.getPersonName(), p.getEmail(), p.getCardSn(), String.valueOf(p.getVoltage()), lastTime};
                    errorData.add(td);
                }
            }
        }
        log("数据分析", "健康指数");
        return Results.json().root("errorData").done();
    }

    /**
     * 返回设备名称, 如果没有返回设备ID
     *
     * @param device
     * @return
     */
    public String getNodeName(Device device) {
        if (Strings.isNullOrEmpty(device.getNodeName())) {
            return device.getNodeId();
        } else {
            return device.getNodeName() + "(" + device.getNodeId() + ")";
        }
    }

    /**
     * 返回设备所在区域名称
     *
     * @param device
     * @return
     */
    public String getNodeZoneName(Device device) {
        if (Strings.isNullOrEmpty(device.getRoomName())) {
            return "（暂无）";
        }

        return device.getRoomName();
    }

    /**
     * 获得设备类型
     *
     * @param flag
     * @return
     */
    private String getNodeType(int flag) {
        String type = "";
        //1：节点 2：中继 3:节点-主模块 4:节点-从模块 7：网关
        switch (flag) {
            case 1:
                type = "节点";
                break;
            case 2:
                type = "中继";
                break;
            case 3:
                type = "节点";
                break;
            case 4:
                type = "节点";
                break;
            case 7:
                type = "网关";
                break;
        }
        return type;
    }

    /**
     * 获得设备异常状态
     *
     * @param flag
     * @return
     */
    private String getAnomaly(int flag) {
        String type = "";
        //-1、超时, 0、正常, 1、低电压, 2、掉电
        switch (flag) {
            case -1:
                type = "超时";
                break;
            case 0:
                type = "正常";
                break;
            case 1:
                type = "低电压";
                break;
            case 2:
                type = "掉电";
                break;
        }
        return type;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    //getter setter
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<HealthCheckItem> getItems() {
        return items;
    }

    public void setItems(List<HealthCheckItem> items) {
        this.items = items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public List<Integer> getJsonCount() {
        return jsonCount;
    }

    public void setJsonCount(List<Integer> jsonCount) {
        this.jsonCount = jsonCount;
    }

    public List<String[]> getErrorData() {
        return errorData;
    }

    public void setErrorData(List<String[]> errorData) {
        this.errorData = errorData;
    }
}
