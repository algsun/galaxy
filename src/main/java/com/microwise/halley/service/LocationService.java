package com.microwise.halley.service;

import com.microwise.blueplanet.bean.vo.LocationVO;

import java.util.List;

/**
 * 位置点 Service
 *
 * @author xuyuexi
 * @date 2014-08-12
 */
public interface LocationService {

    /**
     * 查询外展节点位置点
     *
     * @param exhibitionId 外展ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationListByExhibitionId(int exhibitionId);
}
