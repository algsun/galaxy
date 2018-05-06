package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Comment;
import com.microwise.orion.bean.RepairAssessment;
import com.microwise.orion.dao.CommentDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 专家点评daoImpl
 *
 * @author bai.weixing
 * @since 2017/6/22.
 */
@Beans.Dao
@Orion
public class CommentDaoImpl extends OrionBaseDao<Comment> implements CommentDao {

    public CommentDaoImpl() {
        super(Comment.class);
    }

    @Override
    public Comment findByRepairRecordId(int repairRecordId) {
        String hql = " from Comment c where c.repairRecordId = :repairRecordId";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId", repairRecordId);
        return (Comment) query.uniqueResult();
    }
}
