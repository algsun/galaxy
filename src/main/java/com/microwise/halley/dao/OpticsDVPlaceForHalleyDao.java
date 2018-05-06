package com.microwise.halley.dao;

import com.microwise.halley.bean.po.OpticsDVPlacePO;

import java.util.List;

/**
 * 哈雷光学摄像机点位查询
 * <p/>
 * TODO 接口函数是否合理？？？
 *
 * @author wang.geng
 * @date 13-9-30
 * @check @li.jianfei #5806 2013-10-10
 */
public interface OpticsDVPlaceForHalleyDao {

    /**
     * 查询所有光学摄像机，不分页，没有区域信息
     *
     * @param siteId 站点ID
     * @return 摄像机点位列表
     * @author wang.geng
     * @date 2013-9-30
     */
    public List<OpticsDVPlacePO> findAllOpticsDV(String siteId);

    /**
     * 查询区域下的光学摄像机
     *
     * @param zoneId 区域ID
     * @return 摄像机点位列表
     * @author wang.geng
     * @date 2013-9-30
     */
    public List<OpticsDVPlacePO> findOpticsDVByZoneId(String zoneId, String siteId);


}
