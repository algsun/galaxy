package com.microwise.saturn.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.Affair;
import com.microwise.saturn.dao.AffairDao;
import com.microwise.saturn.service.AffairService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lijianfei on 15-3-19.
 */
@Beans.Service
@Transactional
@Saturn
public class AffairServiceImpl implements AffairService {

    @Autowired
    private AffairDao affairDao;

    @Override
    public void save(Affair affair) {
        affairDao.save(affair);
    }

    @Override
    public void update(Affair affair) {
        affairDao.update(affair);
    }

    @Override
    public List<Affair> findAll(String siteId) {
        return affairDao.findAll(siteId);
    }

    @Override
    public Affair findById(int affairId) {
        return affairDao.findById(affairId);
    }

    @Override
    public void delete(int affairId) {
        affairDao.delete(affairId);
    }

    @Override
    public List<Affair> findAllByTypeAndYear(String siteId, Integer type, Integer year, Integer quarterNum) {
        if (type == null) {
            type = affairDao.findMaxSizeType(siteId, year, quarterNum);
        }
        return affairDao.findAllByTypeAndYear(type, year, quarterNum);
    }

    @Override
    public List<Affair> findAffairChart(String siteId, Integer year, Integer quarterNum) {
        List<Affair> affairs = affairDao.findAffairType(siteId, year, quarterNum);
        for (Affair affair : affairs) {
            int totalCount = affairDao.findAffairTypeCount(siteId, affair.getType(), year, quarterNum);
            affair.setTotalCount(totalCount);
        }
        return affairs;
    }
}
