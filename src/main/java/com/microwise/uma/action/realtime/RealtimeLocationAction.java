package com.microwise.uma.action.realtime;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.ZoneBean;
import com.microwise.uma.service.ZoneService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 实时定位
 *
 * @author li.jianfei
 * @date 2013-4-12
 *
 * @check @wang yunlong 2013-04-28 #3038
 * @check @hou.xiaocheng 2013-06-04 #3796
 */
@Beans.Action
@Uma
public class RealtimeLocationAction extends UmaLoggerAction {

    /**
     * 区域 Service 接口
     */
    @Autowired
    private ZoneService zoneService;

    // Input
    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 查询类型
     */
    private int queryType;

    // Output
    /**
     * 区域列表(包含人员信息)
     */
    private List<ZoneBean> zoneList;

    // Input && Output
    /**
     * 记录关闭的区域
     */
    private String closedZoneIds;

    /**
     * 时间范围
     */
    private int hourInterval;

    /**
     * 查询实时人员分布统计信息
     *
     * @return
     */
    public String execute() {
        try {
            zoneList();
            ActionMessage.createByAction().consume();
            log("实时人员分布","按规则统计");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端出错").consume();
            logFailed("查询区域人员实时信息", "查询区域人员实时信息失败");
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    /**
     * 区域人员详情
     *
     * @return
     */
    public String detail() {
        try {
            zoneDetail();
            ActionMessage.createByAction().consume();
            log("实时人员分布","查询人员分布详情");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端出错").consume();
            logFailed("查询区域人员详细信息", "查询区域人员详细信息失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * Ajax 请求实时人员统计信息
     *
     * @return
     */
    public String ajaxExecute() {

        try {
            zoneList();
        } catch (Exception e) {
            logFailed("查询区域人员实时信息", "查询区域人员实时信息失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * Ajax 请求区域人员详情
     *
     * @return
     */
    public String ajaxDetail() {
        try {
            zoneDetail();
        } catch (Exception e) {
            logFailed("查询区域人员详细信息", "查询区域人员详细信息失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 获取实时人员分布统计信息
     */
    private void zoneList() {
        String siteId = Sessions.createByAction().currentSiteId();
        if (queryType == 0) {   // 按规则统计
            zoneList = zoneService.countGoTimesWithZoneId(siteId, 1, getClosedZoneIds());
        } else if (queryType == 1) {           // 按设备统计
            zoneList = new ArrayList<ZoneBean>();
            hourInterval = (hourInterval == 0 ? 1 : hourInterval);
            zoneService.findZoneTree(zoneList, siteId, null, 0, hourInterval, getClosedZoneIds());
        }
    }

    /**
     * 获取区域详情信息(人员)
     */
    private void zoneDetail() {
        String siteId = Sessions.createByAction().currentSiteId();
        if (queryType == 0) {   // 按规则统计
            zoneList = zoneService.findPeopleInOneZone(siteId, zoneId);
        } else if (queryType == 1) {    // 按设备统计
            zoneList = zoneService.findPeopleInZone(siteId, zoneId, hourInterval);
        }
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<ZoneBean> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<ZoneBean> zoneList) {
        this.zoneList = zoneList;
    }

    public List<String> getClosedZoneIds() {
        List<String> closedList = new ArrayList<String>();
        if (closedZoneIds != null) {
            String temp[] = closedZoneIds.split(",");
            Collections.addAll(closedList, temp);
        }
        return closedList;
    }

    public void setClosed(String closedZoneIds) {
        this.closedZoneIds = closedZoneIds;
    }

    public int getHourInterval() {
        return hourInterval;
    }

    public void setHourInterval(int hourInterval) {
        this.hourInterval = hourInterval;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }
}

