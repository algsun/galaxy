/**
 * 
 */
package com.microwise.common.sys.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * mybatis Base Dao
 * 
 * @author zhangpeng
 * @date 2012-1-4
 */
public abstract class MybatisBaseDao extends SqlSessionDaoSupport {

	/**
	 * 子系统的baseDao必须重写这个方法，指定SqlSessionFactory
	 * 
	 * @param sqlSessionFactory
	 *            mybtist映射对象
	 * 
	 * @author zhangpeng
	 * @date 2013-1-4
	 */
	abstract protected void setSqlSessionFactoryForAutowire(SqlSessionFactory sqlSessionFactory);

}
