package com.microwise.cybertron.service;

import com.microwise.cybertron.bean.Attachment;

/**
 * 附件 Service
 *
 * @author xuyuexi
 * @date 2014-07-17
 */
public interface AttachmentService {

    public void addAttachment(Attachment attachment);

    public void deleteAttachment(String uuid);

    public Attachment findAttachment(String uuid);

}
