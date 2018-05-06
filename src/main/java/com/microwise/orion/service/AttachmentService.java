package com.microwise.orion.service;

import com.microwise.orion.bean.Attachment;

/**
 * 文物挂接文档 service
 * 
 * @author xubaoji
 * @date 2013-5-22
 *
 * @check 2013-06-04 zhangpeng svn:4016
 */
public interface AttachmentService {

	/**
	 * 添加文物 挂接文档信息
	 * 
	 * @param attachment
	 *            挂接文档信息
	 * 
	 * @author 许保吉
	 * @date 2013-5-22
	 * 
	 * @return void
	 */
	public void addAttachment(Attachment attachment);

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

	/**
	 * 通过id查询一个 文档挂接信息
	 * 
	 * @param id
	 *            编号
	 * 
	 * @author 许保吉
	 * @date 2013-6-3
	 * 
	 * @return Attachment 挂接文档实体
	 */
	public Attachment findById(Integer id);
}
