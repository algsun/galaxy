package com.microwise.cybertron.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Attachment;
import com.microwise.cybertron.dao.AttachmentDao;
import com.microwise.cybertron.sys.Cybertron;
import com.microwise.cybertron.sys.CybertronBaseDao;
import org.hibernate.SQLQuery;

/**
 * 文档Dao 实现
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@Beans.Dao
@Cybertron
public class AttachementDaoImpl extends CybertronBaseDao<Attachment> implements AttachmentDao {
    public AttachementDaoImpl() {
        super(Attachment.class);
    }

    @Override
    public void addAttachment(Attachment attachment) {
        save(attachment);
    }

    @Override
    public void deleteAttachment(String uuid) {
        String sql = "delete from cbt_attachement  where uuid =?";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter(0, uuid);
        query.executeUpdate();
    }

    @Override
    public Attachment findAttachment(String uuid) {
        return findById(uuid);
    }

}
