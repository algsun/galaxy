package com.microwise.saturn.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.TextureVO;
import com.microwise.saturn.dao.TextureDao;
import com.microwise.saturn.sys.SaturnBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

@Beans.Dao
public class TextureDaoImpl extends SaturnBaseDao implements TextureDao {

    @Override
    public void insert(String name) {
        getSqlSession().insert("saturn.Texture.save", name);
    }

    @Override
    public List<TextureVO> findAll() {
        return getSqlSession().selectList("saturn.Texture.findAll");
    }

    @Override
    public void deleteById(int id) {
        getSqlSession().delete("saturn.Texture.deleteById", id);
    }

    @Override
    public void updateById(int id, String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("name", name);
        getSqlSession().update("saturn.Texture.updateById", params);
    }

    @Override
    public boolean isExits(String name) {
        Integer count = getSqlSession().selectOne("saturn.Texture.isExits", name);
        if (count >= 1) {
            return true;
        } else {
            return false;
        }
    }


}
