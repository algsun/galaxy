package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Attachment;

/**
 * 文物挂接 文档 dao
 * 
 * @author xubaoji
 * @date 2013-5-22
 *
 * @check 2013-06-04 zhangpeng svn:4016
 */
public interface AttachmentDao extends BaseDao<Attachment> {

	/**
	 * 删除 文物挂接文档
	 * 
	 * @param id
	 *            挂接文档id编号
	 * 
	 * @author 许保吉
	 * @date 2013-5-22
	 * 
	 * @return void
	 */
	public void deleteAttachment(Integer id);

}
