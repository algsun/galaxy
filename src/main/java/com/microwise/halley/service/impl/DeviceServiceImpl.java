package com.microwise.halley.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.OpticsDVPlacePO;
import com.microwise.halley.dao.DeviceDao;
import com.microwise.halley.service.DeviceService;
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
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public List<OpticsDVPlacePO> findOpticsDVByExhibitionId(int exhibitionId) {
        return deviceDao.findOpticsDVByExhibitionId(exhibitionId);
    }

}
