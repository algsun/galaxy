package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Comment;

/**
 * 专家点评dao
 *
 * @author bai.weixing
 * @since 2017/6/22.
 */
public interface CommentDao extends BaseDao<Comment>{
    public Comment findByRepairRecordId(int repairRecordId);
}
