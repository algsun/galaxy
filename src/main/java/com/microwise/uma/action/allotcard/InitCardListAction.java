package com.microwise.uma.action.allotcard;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.service.DeviceService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 初始化 电子卡设备 action
 *
 * @author xubaoji
 * @date 2013-4-22
 *
 * @check @wang yunlong 2013-04-28 #2882
 * @check @li.jianfei 2013-06-04 #3097
 */
@Beans.Action
@Uma
public class InitCardListAction extends UmaLoggerAction{

    /**
     * 设备service
     */
    @Autowired
    private DeviceService deviceService;

    /**
     * 电子卡列表
     */
    private List<DeviceBean> deviceList;

    /**
     * 初始化 电子卡设备 action
     * @return
     */
    public String execute() {
        try {
            deviceList = deviceService.findAllCard();
        } catch (Exception e) {
            logFailed("初始化所有未绑定电子卡出错！", "初始化所有未绑定电子卡出错");
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
