package com.microwise.uma.action.device;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.bean.ZoneBean;
import com.microwise.uma.service.DeviceService;
import com.microwise.uma.service.ZoneService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 查询设备
 *
 * @author li.jianfei
 * @date 2013-4-11
 *
 * @check @wang yunlong 2013-04-28 #2778
 * @check @hou.xiaoping 2013-06-04 #4019
 */
@Beans.Action
@Uma
public class QueryDeviceAction  extends UmaLoggerAction {

    /**
     * 区域 服务层
     */
    @Autowired
    private ZoneService zoneService;

    /**
     * 设备 服务层
     */
    @Autowired
    private DeviceService deviceService;

    // INPUT
    /**
     * 设备类型(0-激发器；1-读卡器)
     */
    private int deviceType;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 设备状态
     * 0-已激活  1-全部
     */
    private int deviceState;

    // OUTPUT
    /**
     * 拥有激发器的区域即可
     */
    private List<ZoneBean> zoneList;

    /**
     * 设备列表
     */
    private List<DeviceBean> deviceList;

    // INPUT && OUTPUT
    /**
     * 当前页
     */
    private int index;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 查询设备
     * @return
     */
    public String execute() {

        try {
            // 获取当前站点ID
            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();

            // 查询当前站点下拥有激发器设备的区域集合
            zoneList = zoneService.findZonesHasDevice(siteId);

            // 默认查询激发器
            deviceType = (deviceType == 0 ? 2 : deviceType);

            // 计算总页数
            pageCount = PagingUtil.pagesCount(deviceService.findDeviceListCount(deviceType, zoneId, deviceState == 1), Constants.SIZE_PER_PAGE);
            // 分页查询设备列表
            index = (index == 0 ? 1 : index);
            deviceList = deviceService.findDeviceList(deviceType, zoneId, deviceState == 1, index, Constants.SIZE_PER_PAGE);

            ActionMessage.createByAction().consume();
            log("设备管理","查询设备");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端出错").consume();
            logFailed("查询摄像机点位", "查询摄像机点位");
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    public int getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(int deviceState) {
        this.deviceState = deviceState;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
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

    public List<DeviceBean> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceBean> deviceList) {
        this.deviceList = deviceList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
