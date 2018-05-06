package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Scheme;
import com.microwise.orion.dao.SchemeDao;
import com.microwise.orion.service.SchemeService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王耕
 * @date 15-9-9
 */
@Beans.Service
@Orion
@Transactional
public class SchemeServiceImpl implements SchemeService {

    @Autowired
    private SchemeDao schemeDao;

    @Override
    public void saveOrUpdateScheme(Scheme scheme) {
        schemeDao.saveOrUpdateScheme(scheme);
    }

    @Override
    public void deleteScheme(int id) {
        schemeDao.deleteScheme(id);
    }

    @Override
    public List<Scheme> findAll(String siteId) {
        return schemeDao.findAll(siteId);
    }

    @Override
    public Scheme findSchemeById(int id) {
        return schemeDao.findSchemeById(id);
    }
}
