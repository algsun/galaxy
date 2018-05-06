package com.microwise.uma.sys;

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
public class UmaBaseDao extends MybatisBaseDao {

	protected static SqlSessionFactory sqlSessionFactory;

	@Autowired
	@Qualifier("uma-sqlSessionFactory")
	@Override
	protected void setSqlSessionFactoryForAutowire(
			SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
