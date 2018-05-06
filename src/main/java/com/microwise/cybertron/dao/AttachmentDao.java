package com.microwise.cybertron.dao;

import com.microwise.cybertron.bean.Attachment;

/**
 * 文档 Dao
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public interface AttachmentDao {

    public void addAttachment(Attachment attachment);

    public void deleteAttachment(String uuid);

    public Attachment findAttachment(String uuid);

}
