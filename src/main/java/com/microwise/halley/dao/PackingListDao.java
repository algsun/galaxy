package com.microwise.halley.dao;

import com.microwise.halley.bean.vo.PackingListVO;
import com.microwise.halley.bean.vo.PackingRelicVO;

import java.util.List;

/**
 * 装箱单 Dao
 *
 * @author li.jianfei
 * @date 2013-09-26
 * @check @wang.geng #5765 2013-10-10
 */
public interface PackingListDao {

    /**
     * 获取每辆车的装箱单
     *
     * @param carId 车辆ID
     * @return 装箱清单集合
     */
    public List<PackingListVO> findPackingListByCarId(int carId);

    /**
     * 根据文物集装箱ID获取文物列表
     *
     * @param packingId 文物集装箱ID
     * @return 集装箱文物清单
     */
    public List<PackingRelicVO> findPackingRelicListByPackingId(int packingId);
}
