package com.microwise.halley.dao;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.halley.bean.po.OpticsDVPlacePO;

import java.util.List;

/**
 * 设备 Dao
 *
 * @author li.jianfei
 * @date 2013-11-04
 */
public interface DeviceDao {

    /**
     * 查询外展光学摄像机设备
     *
     * @param exhibitionId 外展ID
     * @return 光学摄像机集合
     */
    public List<OpticsDVPlacePO> findOpticsDVByExhibitionId(int exhibitionId);

    /**
     * 查询外展节点位置点
     *
     * @param exhibitionId 外展ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationListByExhibitionId(int exhibitionId);
}
