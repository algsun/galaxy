package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Texture;
import com.microwise.orion.dao.TextureDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 文物 质地 dao 实现
 *
 * @author xubaoji
 * @date 2013-5-17
 * @check 2013-06-04 zhangpeng svn:3608
 */
@Dao
@Orion
public class TextureDaoImpl extends OrionBaseDao<Texture> implements TextureDao {

    public TextureDaoImpl() {
        super(Texture.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Texture> findAllTexture() {
        String hql = " from Texture t";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public boolean isTextureExist(String name) {
        return findAllTextureByName(name).size() > 0;
    }

    public List<Texture> findAllTextureByName(String name) {
        String hql = " from Texture t where  t.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    public void delete(String name) {
        String hql = "delete from Texture t where  t.name=:name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public int findIdByName(String name) {
        return findAllTextureByName(name).get(0).getId();
    }
}
