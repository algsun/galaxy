package com.microwise.saturn.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.Literature;
import com.microwise.saturn.dao.LiteratureDao;
import com.microwise.saturn.service.LiteratureService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lijianfei on 15-3-16.
 */

@Beans.Service
@Transactional
@Saturn
public class LiteratureServiceImpl implements LiteratureService {

    @Autowired
    private LiteratureDao literatureDao;


    @Override
    public void save(Literature literature) {
        literatureDao.save(literature);
    }

    @Override
    public void update(Literature literature) {
        literatureDao.update(literature);
    }

    @Override
    public List<Literature> findAll(String siteId) {
        return literatureDao.findAll(siteId);
    }

    @Override
    public Literature findById(int literatureId) {
        return literatureDao.findById(literatureId);
    }

    @Override
    public void delete(int literatureId) {
        literatureDao.delete(literatureId);
    }

    @Override
    public List<Literature> findLiteratures(String siteId, String title, String keywords, int index, int pageSize) {
        return literatureDao.findLiteratures(siteId, title, keywords, index, pageSize);
    }

    @Override
    public int countLiteratures(String siteId, String title, String keywords) {
        return literatureDao.countLiteratures(siteId, title, keywords);
    }

}
