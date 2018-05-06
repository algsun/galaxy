package com.microwise.halley.dao.impl;

import com.microwise.halley.bean.vo.PackingListVO;
import com.microwise.halley.bean.vo.PackingRelicVO;
import com.microwise.halley.dao.PackingListDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.List;

import static com.microwise.common.sys.annotation.Beans.Dao;

/**
 * 装箱单 Dao 实现
 *
 * @author li.jianfei
 * @date 2013-09-26
 * @check @wang.geng #5765 2013-10-10
 */
@Dao
@Halley
public class PackingListDaoImpl extends HalleyBaseDao implements PackingListDao {
    @Override
    public List<PackingListVO> findPackingListByCarId(int carId) {
        return getSqlSession().selectList("halley.mybatis.PackingListDao.findPackingListByCarId", carId);
    }

    @Override
    public List<PackingRelicVO> findPackingRelicListByPackingId(int packingId) {
        return getSqlSession().selectList("halley.mybatis.PackingListDao.findPackingRelicListByPackingId", packingId);
    }
}
