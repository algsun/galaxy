package com.microwise.uma.action.device;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.service.DeviceService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaDaemonApiClients;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 编辑设备
 *
 * @author li.jianfei
 * @date 2013-4-11
 *
 * @check @wang yunlong 2013-04-28 #3090
 * @check @hou.xiaocheng 2013-06-04 #4036
 */
@Beans.Action
@Uma
public class UpdateDeviceAction extends UmaLoggerAction {

    /**
     * 设备 服务层
     */
    @Autowired
    private DeviceService deviceService;

    // Input
    /**
     * 设备类型
     */
    private int deviceType;

    /**
     * 设备ID
     */
    private int deviceId;

    /**
     * 启用状态(true-启用)
     */
    private boolean enable;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 设备状态
     * 0-已激活  1-全部
     * 此处用于记录停用启用时的页面状态
     */
    private int deviceState;

    // Output
    /**
     * 设备信息
     */
    private DeviceBean device;

    /**
     * 设备名称是否已被使用
     * ture - 已被使用
     */
    private boolean deviceNameUsed;

    /**
     * 跳转前所在分页页码
     */
    private int currentPageIndex;

    /**
     * 跳转到编辑设备页面
     * @return
     */
    public String view() {
        try {
            device = deviceService.findDeviceById(deviceId);
            ActionMessage.createByAction().consume();
        } catch (Exception e) {
            logFailed("设备更新", "设备更新失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 编辑设备
     * @return
     */
    public String execute() {

        try {
            if (deviceService.isNameUsed(deviceId, deviceName)) {
                ActionMessage.createByAction().fail("指定的设备名已被使用");
                return Action.INPUT;
            } else {
                deviceService.updateDevice(deviceId, deviceName, zoneId);

                // 通知后台程序
                if (!UmaDaemonApiClients.getClient().exciterChanged()) {
                    ActionMessage.createByAction().fail("通知后台失败，但设备编辑成功");
                } else {
                    ActionMessage.createByAction().success("编辑成功");
                }
            }
            log("设备管理","修改设备");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("编辑失败").consume();
            logFailed("编辑设备信息", "编辑设备信息失败");
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    /**
     * 停用/启用设备
     * @return
     */

    public String setEnable() {
        try {
            // 设置设备停用启用状态
            DeviceBean device = deviceService.findDeviceById(deviceId);
            if (enable) {
                deviceService.setEnable(deviceId, enable);
                ActionMessage.createByAction().success("启用成功");
            } else {
                if (deviceService.isCanDisableExciter(deviceId)) {
                    deviceService.setEnable(deviceId, enable);
                    ActionMessage.createByAction().success("停用成功");

                } else {
                    ActionMessage.createByAction().fail("停用失败，该设备已在行为规则中使用");
                }
            }
            // 通知后台程序
            if ((device.getType() == 2 && !UmaDaemonApiClients.getClient().exciterChanged())
                    || (device.getType() == 1 && !UmaDaemonApiClients.getClient().cardReaderChanged())) {
                ActionMessage.createByAction().fail("通知后台服务失败，但设备" + (enable ? "启用" : "停用" + "成功"));
            }
            log("设备管理","停用设备");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端出错").consume();
            logFailed("停用/启用设备", "停用/启用设备失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public boolean isDeviceNameUsed() {
        return deviceNameUsed;
    }

    public void setDeviceNameUsed(boolean deviceNameUsed) {
        this.deviceNameUsed = deviceNameUsed;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public int getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(int deviceState) {
        this.deviceState = deviceState;
    }
}
