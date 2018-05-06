package com.microwise.common.sys.hibernate;

import java.io.Serializable;

/**
 * 基础 dao 实现
 * 
 * @author gaohui
 * @date 2012-5-24
 */
public abstract class BaseDaoImpl<T> extends HibernateBaseDao implements
		BaseDao<T> {

	protected Class<T> clazz;

	public BaseDaoImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	public Serializable save(T t) {
		return getSession().save(t);
	}

	public void delete(T t) {
		getSession().delete(t);
	}

	public void update(T t) {
		getSession().update(t);
	}
}
