package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Attachment;
import com.microwise.orion.dao.AttachmentDao;
import com.microwise.orion.service.AttachmentService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文物挂接文档 service 实现
 * 
 * @author xubaoji
 * @date 2013-5-22
 *
 * @check 2013-06-04 zhangpeng svn:4016
 */
@Service
@Orion
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

	/** 文物 挂接文档dao */
	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	public void addAttachment(Attachment attachment) {
        attachmentDao.save(attachment);
	}

	@Override
	public void deleteAttachment(Integer id) {
		attachmentDao.deleteAttachment(id);
	}
	
	@Override
	public Attachment findById(Integer id) {
		return attachmentDao.findById(id);
	}

}
