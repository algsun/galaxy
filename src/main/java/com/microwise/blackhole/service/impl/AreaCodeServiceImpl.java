package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.dao.AreaCodeDao;
import com.microwise.blackhole.service.AreaCodeService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地区行政 service 实现
 *
 * @author xubaoji
 * @date 2012-11-29
 * @check 2012-12-05 zhangpeng svn:642
 */
@Service
@Transactional
@Blackhole
public class AreaCodeServiceImpl implements AreaCodeService {

    /**
     * 注入地区行政 dao
     */
    @Autowired
    private AreaCodeDao areaCodeDao;

    @Override
    public List<AreaCodeCN> findAllArea() {
        return areaCodeDao.findAllArea();
    }

    @Override
    public List<AreaCodeCN> findAreaListByAreaCode(int areaCode) {
        return areaCodeDao.findAreaListByAreaCode(areaCode);
    }

}
