package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.LocationVO;

/**
 * 控制中心通信service
 *
 * @author xiedeng
 * @date 15-1-23
 */
@Deprecated
public interface ControlCenterCommunicationService {
    /**
     * 上传位置信息
     *
     * @param siteId   站点编号
     * @param location 位置点
     * @param isLocation 是否是位置点
     */
    public void uploadZone(String siteId, String zoneId, LocationVO location, boolean isLocation);

    /**
     * 上传位置信息
     *
     * @param siteId 站点编号
     * @param zoneId 区域编号
     */
    public void uploadZone(String siteId, String zoneId);

}
