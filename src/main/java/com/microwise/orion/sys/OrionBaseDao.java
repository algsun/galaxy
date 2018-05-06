package com.microwise.orion.sys;

import com.microwise.common.sys.hibernate.BaseDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 文物管理 baseDao 基本dao  基于hibernate 
 * 
 * @author xubaoji
 * @date 2013-05-15
 */
public class OrionBaseDao<T> extends BaseDaoImpl<T> {

	public OrionBaseDao(Class<T> clazz) {
		super(clazz);
	}

	/**
	 * 通过spring定义的sessionFactory的BeanName获取
	 */
	@Autowired
	@Qualifier("hibernate-sessionFactory")
	@Override
	protected void inject(SessionFactory sessionFactory) {
		initSessionFactory(sessionFactory);
	}

}
