package com.microwise.proxima.service;


/**
 * 系统配置Service
 * 
 * @author zhangpeng
 * @date 2013-3-26
 */
public interface SystemConfigService {

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
