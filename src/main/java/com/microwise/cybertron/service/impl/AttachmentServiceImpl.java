package com.microwise.cybertron.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Attachment;
import com.microwise.cybertron.dao.AttachmentDao;
import com.microwise.cybertron.service.AttachmentService;
import com.microwise.cybertron.sys.Cybertron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * 附件 Service 实现
 *
 * @author xuyuexi
 * @date 2014-07-17
 */
@Beans.Service
@Transactional
@Cybertron
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentDao attachmentDao;

    @Override
    public void addAttachment(Attachment attachment) {
        attachmentDao.addAttachment(attachment);
    }

    @Override
    public void deleteAttachment(String uuid) {
        Attachment attachment =findAttachment(uuid);
        attachmentDao.deleteAttachment(uuid);
        File file = new File(attachment.getPath());
        file.delete();
    }

    @Override
    public Attachment findAttachment(String uuid) {
        return attachmentDao.findAttachment(uuid);
    }

}
