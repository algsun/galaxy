package com.microwise.saturn.sys;

import com.microwise.common.sys.mybatis.MybatisBaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * blueplanet连接数据库baseDao
 * 
 * @author zhangpeng
 * @date 2012-10-30
 */
public class SaturnBaseDao extends MybatisBaseDao {

	protected SqlSessionFactory sqlSessionFactory;

    /**
     * 通过spring定义sessionFactory的BeanName获取
     * @param sqlSessionFactory
     */
    @Autowired
    @Qualifier("saturn-sqlSessionFactory")
    @Override
    protected void setSqlSessionFactoryForAutowire(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
        this.sqlSessionFactory = sqlSessionFactory;
    }

}
