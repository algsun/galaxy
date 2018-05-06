package com.microwise.blueplanet.sys;

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
public class BlueplanetBaseDao extends MybatisBaseDao {

	protected SqlSessionFactory sqlSessionFactory;

	@Autowired
	@Qualifier("blueplanet-sqlSessionFactory")
	@Override
	protected void setSqlSessionFactoryForAutowire(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
