package com.microwise.phoenix.sys;

import com.microwise.common.sys.mybatis.MybatisBaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/***
 * phoenix连接数据库baseDao
 *
 * @author xu.baoji
 * @date 2013-7-4
 */
public class PhoenixBaseDao extends MybatisBaseDao {

    protected static SqlSessionFactory sqlSessionFactory;

    @Autowired
    @Qualifier("phoenix-sqlSessionFactory")
    @Override
    protected void setSqlSessionFactoryForAutowire(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
        this.sqlSessionFactory = sqlSessionFactory;
    }

}
