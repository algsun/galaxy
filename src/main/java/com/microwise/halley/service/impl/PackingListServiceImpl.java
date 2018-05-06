package com.microwise.halley.service.impl;

import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.bean.vo.PackingListVO;
import com.microwise.halley.bean.vo.PackingRelicVO;
import com.microwise.halley.dao.CarDao;
import com.microwise.halley.dao.PackingListDao;
import com.microwise.halley.service.PackingListService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.microwise.common.sys.annotation.Beans.Service;

/**
 * 装箱单 Service 实现
 *
 * @author li.jianfei
 * @date 2013-09-26
 * @check @wang.geng #5765 2013-10-10
 */
@Service
@Transactional
@Halley
public class PackingListServiceImpl implements PackingListService {

    /**
     * 车辆 Dao
     */
    @Autowired
    private CarDao carDao;

    /**
     * 装箱单 Dao
     */
    @Autowired
    private PackingListDao packingListDao;

    @Override
    public Map<CarPO, List<PackingListVO>> findPackingListByExhibitionId(int exhibitionId) {

        // 查询外展车辆信息
        List<CarVO> carList = carDao.findCarsByExhibitionId(exhibitionId);
        Map<CarPO, List<PackingListVO>> result = new LinkedHashMap<CarPO, List<PackingListVO>>();

        for (CarPO car : carList) {
            // 查询车辆装箱单信息
            List<PackingListVO> packingLists = packingListDao.findPackingListByCarId(car.getId());

            // 填充装箱单文物列表
            List<PackingRelicVO> relicList;
            for (PackingListVO packingList : packingLists) {
                relicList = packingListDao.findPackingRelicListByPackingId(packingList.getId());
                packingList.setRelicList(relicList);
            }
            result.put(car, packingLists);
        }

        return result;
    }

    @Override
    public List<PackingListVO> findPackingList(int exhibitionId) {
        // 查询外展车辆信息
        List<CarVO> carList = carDao.findCarsByExhibitionId(exhibitionId);
        List<PackingListVO> packingListList = new ArrayList<PackingListVO>();
        if (carList != null) {
            for (CarPO car : carList) {
                // 查询车辆装箱单信息
                List<PackingListVO> packingLists = packingListDao.findPackingListByCarId(car.getId());
                packingListList.addAll(packingLists);
            }
        }
        return packingListList;
    }
}