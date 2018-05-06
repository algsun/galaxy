package com.microwise.halley.service;

import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.vo.PackingListVO;

import java.util.List;
import java.util.Map;

/**
 * 装箱单 Service
 *
 * @author li.jianfei
 * @date 2013-09-26
 * @check @wang.geng #5765 2013-10-10
 */
public interface PackingListService {

    /**
     * 查询车辆文物装箱单信息
     *
     * @param exhibitionId 外展ID
     * @return 车辆文物装箱单
     */
    public Map<CarPO, List<PackingListVO>> findPackingListByExhibitionId(int exhibitionId);

    /**
     * 查询车辆文物装箱单信息
     *
     * @param exhibitionId 外展ID
     * @return 车辆所有文物装箱单
     */
    public List<PackingListVO> findPackingList(int exhibitionId);
}
