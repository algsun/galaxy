/**
 * 
 */
package com.microwise.proxima.sys;

import com.microwise.common.sys.hibernate.BaseDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * proxima的baseDao
 * 
 * @author zhangpeng
 * @date 2012-10-29
 */
public class ProximaBaseDao<T> extends BaseDaoImpl<T> {

	public ProximaBaseDao(Class<T> clazz) {
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
