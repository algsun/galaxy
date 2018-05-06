package com.microwise.halley.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.OpticsDVPlacePO;
import com.microwise.halley.dao.OpticsDVPlaceForHalleyDao;
import com.microwise.halley.service.OpticsDVPlaceService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 光学摄像机点位 Service
 *
 * @author wang.geng
 * @date 2013-10-8
 * @check @li.jianfei #5806 2013-10-10
 */
@Beans.Service
@Transactional
@Halley
public class OpticsDVPlaceServiceImpl implements OpticsDVPlaceService {

    @Autowired
    private OpticsDVPlaceForHalleyDao opticsDVPlaceDao;

    @Override
    public List<OpticsDVPlacePO> findAllOpticsDV(String siteId) {
        return opticsDVPlaceDao.findAllOpticsDV(siteId);
    }

    @Override
    public List<OpticsDVPlacePO> findOpticsDVByZoneId(String zoneId, String siteId) {
        return opticsDVPlaceDao.findOpticsDVByZoneId(zoneId, siteId);
    }

}
