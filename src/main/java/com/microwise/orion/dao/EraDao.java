package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Era;

import java.util.List;

/**
 * 文物时代 dao
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3519
 */
public interface EraDao extends BaseDao<Era> {

	/**
	 * 查询所有 文物时代
	 *
	 * @author 许保吉
	 * @date 2013-5-17
	 *
	 * @return List<Era> 时代列表
	 */
	public List<Era> findAllEra();

    void deleteByName(String name);

    boolean isEraExist(String name);

    public int findIdByName(String name);
}
