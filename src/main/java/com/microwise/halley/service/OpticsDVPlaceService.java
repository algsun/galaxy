package com.microwise.halley.service;

import com.microwise.halley.bean.po.OpticsDVPlacePO;

import java.util.List;

/**
 * 光学摄像机点位 Service
 *
 * @author wang.geng
 * @date 2013-10-8
 * @check @li.jianfei #5806 2013-10-10
 */
public interface OpticsDVPlaceService {
    /**
     * 查询所有光学摄像机，不分页，没有区域信息
     *
     * @param siteId
     * @return
     * @author wang.geng
     * @date 2013-9-30
     */
    public List<OpticsDVPlacePO> findAllOpticsDV(String siteId);

    /**
     * 查询区域下的光学摄像机
     *
     * @param zoneId
     * @return
     * @author wang.geng
     * @date 2013-9-30
     */
    public List<OpticsDVPlacePO> findOpticsDVByZoneId(String zoneId, String siteId);
}
