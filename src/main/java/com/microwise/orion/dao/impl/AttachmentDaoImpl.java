package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Attachment;
import com.microwise.orion.dao.AttachmentDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 文物挂接文档 dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-22
 *
 * @check 2013-06-04 zhangpeng svn:3608
 */
@Dao
@Orion
public class AttachmentDaoImpl extends OrionBaseDao<Attachment> implements
		AttachmentDao {

	public AttachmentDaoImpl() {
		super(Attachment.class);
	}

	@Override
	public void deleteAttachment(Integer id) {
		String hql = "delete from Attachment where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		query.executeUpdate();
	}
}
