package com.microwise.halley.dao;

import com.microwise.blueplanet.bean.vo.LocationVO;

import java.util.List;

/**
 * 设备 Dao
 *
 * @author li.jianfei
 * @date 2013-11-04
 */
public interface LocationDao {


    /**
     * 查询外展节点位置点
     *
     * @param exhibitionId 外展ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationListByExhibitionId(int exhibitionId);
}
