package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Rubbing;

/**
 * 文物拓扑 dao
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3587
 */
public interface RubbingDao extends BaseDao<Rubbing> {

	/**
	 * 通过id 删除文物拓扑
	 * 
	 * @param id
	 *            文物拓扑id
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 * 
	 * @return void
	 */
	public void deleteRubbing(Integer id);

}
