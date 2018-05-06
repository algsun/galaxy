package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Level;

import java.util.List;

/**
 * 文物级别 dao
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3519
 */
public interface LevelDao extends BaseDao<Level> {

	/**
	 * 查询所有 文物级别
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 * 
	 * @return List<Level> 级别列表
	 */
	public List<Level> findAllLevel();

    void deleteByName(String name);

    boolean isLevelExist(String name);

    public int findIdByName(String name);
}
