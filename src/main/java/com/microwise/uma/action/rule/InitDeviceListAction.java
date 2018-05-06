package com.microwise.uma.action.rule;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.service.DeviceService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 初始化 激发器设备 action
 *
 * @author xubaoji
 * @date 2013-4-22
 *
 * @check @wang yunlong 2013-5-6 #2862
 * @check @li.jianfei 2013-06-04 #3238
 */
@Beans.Action
@Uma
public class InitDeviceListAction  extends UmaLoggerAction{

    /**
     * 设备service
     */
    @Autowired
    private DeviceService deviceService;

    /**
     * 激发器列表
     */
    private List<DeviceBean> deviceList;

    /**
     * 初始化 激发器设备
     * @return
     */
    public String execute() {
        try {
            deviceList = deviceService.findAllExciter();
        } catch (Exception e) {
            logFailed("初始化所有激发器 出错！", "初始化激发器设备失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public List<DeviceBean> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceBean> deviceList) {
        this.deviceList = deviceList;
    }

}
