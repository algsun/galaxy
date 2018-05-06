package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Inscription;
import com.microwise.orion.dao.InscriptionDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 铭文题跋dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3608
 */
@Dao
@Orion
public class InscriptionDaoImpl extends OrionBaseDao<Inscription> implements
		InscriptionDao {

	public InscriptionDaoImpl() {
		super(Inscription.class);
	}

	@Override
	public void deleteInscription(Integer id) {
		String hql = "delete from Inscription i where i.id =:id ";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		query.executeUpdate();
	}

    @Override
    public void deleteInscriptionByRelicId(int relicId){
        String hql = "delete from Inscription where relicId =:relicId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        query.executeUpdate();
    }
}
