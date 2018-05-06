package com.microwise.halley.service;

import com.microwise.halley.bean.po.OpticsDVPlacePO;

import java.util.List;

/**
 * 设备 Service
 *
 * @author li.jianfei
 * @date 2013-11-04
 */
public interface DeviceService {

    /**
     * 查询外展光学摄像机设备
     *
     * @param exhibitionId 外展ID
     * @return 光学摄像机集合
     */
    public List<OpticsDVPlacePO> findOpticsDVByExhibitionId(int exhibitionId);

}
