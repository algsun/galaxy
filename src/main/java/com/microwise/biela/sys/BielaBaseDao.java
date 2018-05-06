package com.microwise.biela.sys;

import com.microwise.common.sys.mybatis.MybatisBaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 地图/GIS站点概览 baseDao基本dao.
 *
 * @author wang.geng
 * @date 13-12-31  上午10:25
 */
public class BielaBaseDao extends MybatisBaseDao {

    protected static SqlSessionFactory sqlSessionFactory;

    /**
     * 通过spring定义sessionFactory的BeanName获取
     *
     * @param sqlSessionFactory mybtist映射对象
     */
    @Autowired
    @Qualifier("biela-sqlSessionFactory")
    @Override
    protected void setSqlSessionFactoryForAutowire(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
