package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Comment;
import com.microwise.orion.dao.CommentDao;
import com.microwise.orion.service.CommentService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 专家点评serviceImpl
 *
 * @author bai.weixing
 * @since 2017/6/22.
 */
@Beans.Service
@Orion
@Transactional
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentDao commentDao;

    @Override
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public Comment findByRepairRecordId(int repairRecordId) {
        return commentDao.findByRepairRecordId(repairRecordId);
    }
}
