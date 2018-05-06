package com.microwise.saturn.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.TextureVO;
import com.microwise.saturn.dao.TextureDao;
import com.microwise.saturn.service.TextureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */

@Beans.Service
@Transactional
public class TextureServiceImpl implements TextureService {

    @Autowired
    private TextureDao textureDao;

    @Override
    public void insert(String name) {
        textureDao.insert(name);
    }

    @Override
    public List<TextureVO> findAll() {
        return textureDao.findAll();
    }

    @Override
    public void deleteById(int id) {
        textureDao.deleteById(id);
    }

    @Override
    public void updateById(int id, String name) {
        textureDao.updateById(id, name);
    }

    @Override
    public boolean isExits(String name) {
        return textureDao.isExits(name);
    }

}
