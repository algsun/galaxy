package com.microwise.halley.service.impl;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.dao.LocationDao;
import com.microwise.halley.service.LocationService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备 Service 实现
 *
 * @author li.jianfei
 * @date 2013-11-04
 * @check xu.yuexi 2013-11-08 #6398
 */
@Beans.Service
@Transactional
@Halley
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public List<LocationVO> findLocationListByExhibitionId(int exhibitionId) {
        return locationDao.findLocationListByExhibitionId(exhibitionId);
    }
}
