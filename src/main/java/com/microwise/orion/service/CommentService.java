package com.microwise.orion.service;

import com.microwise.orion.bean.Comment;

/**
 * 专家点评service
 *
 * @author bai.weixing
 * @since 2017/6/22.
 */
public interface CommentService {

    public void save(Comment comment);

    public Comment findByRepairRecordId(int repairRecordId);
}
