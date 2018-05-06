package com.microwise.halley.dao.impl;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.OpticsDVPlacePO;
import com.microwise.halley.dao.DeviceDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.List;

/**
 * 设备 Dao 实现
 *
 * @author li.jianfei
 * @date 2013-11-04
 */
@Beans.Dao
@Halley
public class DeviceDaoImpl extends HalleyBaseDao implements DeviceDao {

    @Override
    public List<OpticsDVPlacePO> findOpticsDVByExhibitionId(int exhibitionId) {
        return getSqlSession().selectList("halley.mybatis.DeviceDao.findOpticsDVByExhibitionId", exhibitionId);
    }

    @Override
    public List<LocationVO> findLocationListByExhibitionId(int exhibitionId) {
        return getSqlSession().selectList("halley.mybatis.DeviceDao.findLocationListByExhibitionId", exhibitionId);
    }
}
