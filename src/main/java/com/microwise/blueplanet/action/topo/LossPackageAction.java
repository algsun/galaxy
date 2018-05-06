package com.microwise.blueplanet.action.topo;

import com.bastengao.struts2.freeroute.Results;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.TopoViewVO;
import com.microwise.blueplanet.service.TopoService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 丢包率
 *
 * @author liuzhu
 * @date 13-9-27
 * @check @xiedeng 2013-10-11 #5895
 */

@Beans.Action
@Blueplanet
public class LossPackageAction extends BlueplanetLoggerAction {

    /**
     * 内容页面
     */
    private static final String _pagePath = "loss-package.ftl";

    /**
     * 丢包率service
     */
    @Autowired
    private TopoService topoService;

    // input
    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 数据显示的数量
     */
    private int dataSize = 10;

    // output
    /**
     * 丢包率voList
     */
    private List<TopoViewVO> lossPackageList;

    /**
     * 丢包率json数据
     */
    private String lossPageageListJson;

    public String view() {
        initDate();
        lossPackageList = topoService.getLoseRate(startDate, endDate, Constants.FIND_TYPE_DAY , Sessions.createByAction().currentSiteId());
        if (lossPackageList.size() >= dataSize) {
            lossPackageList = lossPackageList.subList(0, dataSize);
        }
        lossPageageListJson = new Gson().toJson(lossPackageList);
        log("监控", "网络拓扑图");
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



    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public String getLossPageageListJson() {
        return lossPageageListJson;
    }

    public void setLossPageageListJson(String lossPageageListJson) {
        this.lossPageageListJson = lossPageageListJson;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<TopoViewVO> getLossPackageList() {
        return lossPackageList;
    }

    public void setLossPackageList(List<TopoViewVO> lossPackageList) {
        this.lossPackageList = lossPackageList;
    }
}
