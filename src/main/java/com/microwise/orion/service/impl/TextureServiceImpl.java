package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Texture;
import com.microwise.orion.dao.TextureDao;
import com.microwise.orion.service.TextureService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 文物 质地 service 实现类
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3510
 */
@Service
@Orion
@Transactional
public class TextureServiceImpl implements TextureService {

    /**
     * 文物质地dao
     */
    @Autowired
    private TextureDao textureDao;

    @Override
    public List<Texture> findAllTexture() {
        return textureDao.findAllTexture();
    }


    @Override
    public Integer save(Texture texture) {
        Serializable x = textureDao.save(texture);
        return Integer.parseInt(x.toString());
    }

    @Override
    public boolean isTextureExist(String name) {
        return textureDao.isTextureExist(name);
    }

    @Override
    public Integer delete(String name) {
        int id = textureDao.findIdByName(name);
        textureDao.delete(name);
        return id;
    }
}
