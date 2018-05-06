package com.microwise.halley.sys;

import com.microwise.common.sys.mybatis.MybatisBaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 文物外展管理 baseDao基本dao
 *
 * @author: wanggeng
 * @date: 13-9-24 上午10:39
 */
public class HalleyBaseDao extends MybatisBaseDao {

    protected static SqlSessionFactory sqlSessionFactory;

    /**
     * 通过spring定义sessionFactory的BeanName获取
     * @param sqlSessionFactory
     */
    @Autowired
    @Qualifier("halley-sqlSessionFactory")
    @Override
    protected void setSqlSessionFactoryForAutowire(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
