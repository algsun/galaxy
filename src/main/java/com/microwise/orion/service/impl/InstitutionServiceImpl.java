package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Institution;
import com.microwise.orion.dao.InstitutionDao;
import com.microwise.orion.service.InstitutionService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单位service
 *
 * @author liuzhu
 * @date 2015-9-8
 */

@Beans.Service
@Orion
@Transactional
public class InstitutionServiceImpl implements InstitutionService{

    @Autowired
    private InstitutionDao institutionDao;

    @Override
    public void save(Institution institution) {
        institutionDao.save(institution);
    }

    @Override
    public void update(Institution institution) {
        institutionDao.update(institution);
    }

    @Override
    public void delete(Institution institution) {
        institutionDao.delete(institution);
    }

    @Override
    public List<Institution> findBySiteId(String siteId) {
        return institutionDao.findBySiteId(siteId);
    }

    @Override
    public List<Institution> findInstitutions(String siteId, int institutionType) {
        return institutionDao.findInstitutions(siteId, institutionType);
    }

    @Override
    public Institution findById(int id) {
        return institutionDao.findById(id);
    }
}
