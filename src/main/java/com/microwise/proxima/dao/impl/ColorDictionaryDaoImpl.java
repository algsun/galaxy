package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.ColorDictionaryBean;
import com.microwise.proxima.dao.ColorDictionaryDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * <pre>
 * 色轮字典的数据库访问层实现
 * </pre>
 * 
 * @author li.jianfei
 * @date 2012-09-03
 */
@Dao
@Proxima
public class ColorDictionaryDaoImpl extends BaseDaoImpl<ColorDictionaryBean>
		implements ColorDictionaryDao {

	public ColorDictionaryDaoImpl() {
		super(ColorDictionaryBean.class);
	}

	@Override
	public List<ColorDictionaryBean> findAll() {
		Session session = super.getSession();
		Query query = session
				.createQuery("select cd from ColorDictionaryBean as cd order by cd.colorIndex");
		List<ColorDictionaryBean> list = query.list();
		return list;
	}

	@Override
	public int getCount() {
		Session session=super.getSession();
		Query query=session.createQuery("Select count(cd.id) count from ColorDictionaryBean as cd");
		int count=(Integer) query.uniqueResult();
		return count;
	}
	
	

}
