package com.microwise.proxima.dao;

import com.microwise.proxima.dao.base.BaseDao;

/**
 * 系统配置Dao层
 * 
 * @author zhangpeng
 * @date 2013-3-26
 */
public interface SystemConfigDao extends BaseDao<Object> {

	/**
	 * 查询摄像机编码索引
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 * 
	 * @return 摄像机编码索引
	 */
	public int findDvPlaceCodeIndex();

	/**
	 * 更新摄像机编码索引
	 * 
	 * @author index 摄像机编码索引
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 * 
	 * @return void
	 */
	public void updateDvPlaceCodeIndex(int index);

}
