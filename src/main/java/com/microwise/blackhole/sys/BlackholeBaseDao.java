/**
 *
 */
package com.microwise.blackhole.sys;

import com.microwise.common.sys.hibernate.BaseDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * blackhole的baseDao
 *
 * @author zhangpeng
 * @date 2012-11-05
 */
public class BlackholeBaseDao<T> extends BaseDaoImpl<T> {

    public BlackholeBaseDao(Class<T> clazz) {
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
