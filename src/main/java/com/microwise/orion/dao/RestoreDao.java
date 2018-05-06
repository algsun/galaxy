package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Restore;

/**
 * 文物修复信息 dao
 * 
 * @author xubaoji
 * @date 2013-5-16
 *
 * @check 2013-06-05 zhangpeng svn:4075
 */
public interface RestoreDao extends BaseDao<Restore> {

	/**
	 * 根据文物总登记号 查询最近一条 文物修复信息
	 * 
	 * @param relicId
	 *            文物id
	 * 
	 * @author 许保吉
	 * @date 2013-5-16
	 * 
	 * @return Restore 文物修复信息对象
	 */
	public Restore findLatestRestore(Integer relicId);

}
