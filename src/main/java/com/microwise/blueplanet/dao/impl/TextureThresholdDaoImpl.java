package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.vo.TextureThresholdVO;
import com.microwise.blueplanet.dao.TextureThresholdDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.List;

/**
 * 质地阈值dao实现
 */

@Beans.Dao
@Blueplanet
public class TextureThresholdDaoImpl extends BlueplanetBaseDao implements TextureThresholdDao {

    @Override
    public List<String> findBindLocationId(int textureId) {
        return getSqlSession().selectList("blueplanet.textureThresholdDao.findBindLocationId", textureId);
    }

    @Override
    public void insertTextureThreshold(TextureThresholdVO textureThresholdVO) {
        getSqlSession().insert("blueplanet.textureThresholdDao.insertTextureThreshold", textureThresholdVO);
    }

    @Override
    public TextureThresholdVO findTextureThreshold(TextureThresholdVO textureThresholdVO) {
        return getSqlSession().selectOne("blueplanet.textureThresholdDao.findTextureThreshold", textureThresholdVO);
    }

    @Override
    public List<TextureThresholdVO> findTextureThresholds(int textureThresholdId) {
        return getSqlSession().selectList("blueplanet.textureThresholdDao.findTextureThresholds", textureThresholdId);
    }

    @Override
    public void deleteTextureThreshold(int id) {
        getSqlSession().update("blueplanet.textureThresholdDao.deleteTextureThreshold",id);
    }
}
