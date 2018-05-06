package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.dao.SiteDao;
import com.microwise.blackhole.service.SiteService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 站点 Service 实现
 *
 * @author li.jianfie
 * @date 2014-10-09
 */
@Beans.Service
@Transactional
@Blackhole
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteDao siteDao;

    @Override
    public List<Site> findAll() {
        return siteDao.findAll();
    }
}
