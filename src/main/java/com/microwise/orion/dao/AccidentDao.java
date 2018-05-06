package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Accident;

/**
 * 文物事故记录 dao
 * 
 * @author xubaoji
 * @date 2013-5-16
 *
 * @check 2013-06-05 zhangpeng svn:4075
 */
public interface AccidentDao extends BaseDao<Accident> {

	/**
	 * 根据文物id 查询最近一条 事故记录
	 * 
	 * @param relicId
	 *            文物id
	 * 
	 * @author 许保吉
	 * @date 2013-5-16
	 * 
	 * @return Accident 文物事故记录对象
	 */
	public Accident findLatestAccident(Integer relicId);

}
