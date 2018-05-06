package com.microwise.cybertron.sys;

import com.microwise.common.sys.hibernate.BaseDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Cybertron 的 BaseDao
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public class CybertronBaseDao<T> extends BaseDaoImpl<T> {

    public CybertronBaseDao(Class<T> clazz) {
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
