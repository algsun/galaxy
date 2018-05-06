package com.microwise.halley.service;


import com.microwise.halley.bean.po.DevicePO;
import com.microwise.uma.bean.DeviceBean;

import java.util.List;

/**
 * 哈雷子项目设备查询服务.
 *
 * @author wang.gneg
 * @date 13-10-8 上午10:44
 * @check @li.jianfei #5821 2013-10-10
 */
public interface NodeInfoService {

    /**
     * 根据nodeType查询设备
     *
     * @param nodeType 设备类型:1.摄像机设备;2.传感设备
     * @return 设备List
     */
    public List<DevicePO> findDeviceByDeviceType(int nodeType, int exhibitionId);

    /**
     * 更新nodeInfo设备类型
     *
     * @param deviceId
     * @param deviceType
     */
    void updateNodeInfo(String deviceId, int deviceType);
}
