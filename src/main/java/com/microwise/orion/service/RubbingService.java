package com.microwise.orion.service;

import com.microwise.orion.bean.Rubbing;

/**
 * 文物拓扑 service
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3587
 */
public interface RubbingService {

	/**
	 * 添加文物拓扑
	 * 
	 * @param rubbing
	 *            文物拓扑实体
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 * 
	 * @return void
	 */
	public void addRubbing(Rubbing rubbing);

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

    /**
     * 通过id 查询文物拓扑
     *
     * @param id
     *            文物拓扑id
     *
     * @author 许保吉
     * @date 2013-5-21
     *
     * @return void
     */
    public Rubbing findById(Integer id);
}
