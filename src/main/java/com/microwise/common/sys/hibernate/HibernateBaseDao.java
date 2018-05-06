package com.microwise.common.sys.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * hibernate base dao
 * 
 * @author gaohui
 * @date 2012-5-23
 */
public abstract class HibernateBaseDao {

	protected SessionFactory sessionFactory;

	/**
	 * 初始化 sessionFactory
	 * 
	 * @param sessionFactory
	 */
	protected void initSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	abstract protected void inject(SessionFactory sessionFactory);

	/**
	 * 获取session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return currentSession();
	}

	private Session currentSession() {
		// 事务必须是开启的，否则获取不到
		return sessionFactory.getCurrentSession();
	}

}