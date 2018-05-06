package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.Task;
import com.microwise.blackhole.dao.TaskDao;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.sys.Phoenix;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * 任务 dao 实现
 *
 * @author xubaoji
 * @date 2013-6-8
 * @check @duan.qixin 2013年7月29日 #4713
 */
@Dao
@Phoenix
public class TaskDaoImpl extends BlackholeBaseDao<Task> implements TaskDao {

    public TaskDaoImpl() {
        super(Task.class);
    }

    @Override
    public Task findById(Integer taskId) {
        String hql = "from Task t left outer join fetch t.releaser r left outer join fetch t.designees d  where t.id = :taskId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("taskId", taskId);
        return (Task) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> findTasks(int logicGroupId, int releaserId, List<Integer> taskIds,
                                Integer index, Integer size, int state) {
        StringBuilder hql = new StringBuilder(
                " from Task t left outer join fetch t.releaser r left outer join fetch t.designees d  where t.logicGroupId = :logicGroupId  ");
        if (taskIds.size() > 0) {
            hql.append(" and ( t.id in :taskIds  or t.releaser.id = :releaserId )");
        } else {
            hql.append(" and t.releaser.id = :releaserId ");
        }
        if (state == 0) {
            hql.append(" and t.state = false ");
        }
        hql.append(" order by t.releaseDate DESC ");
        Query query = getSession().createQuery(hql.toString());
        query.setParameter("logicGroupId", logicGroupId);
        if (taskIds.size() > 0) {
            query.setParameterList("taskIds", taskIds);
        }
        query.setParameter("releaserId", releaserId);
        query.setFirstResult((index - 1) * size);
        query.setMaxResults(size);
        return query.list();
    }

    @Override
    public Integer findTaskCount(int logicGroupId, int releaserId, List<Integer> taskIds, int state) {
        StringBuilder hql = new StringBuilder(
                "select count(t.id) from Task t  where t.logicGroupId = :logicGroupId  ");
        if (taskIds.size() > 0) {
            hql.append(" and ( t.id in :taskIds  or t.releaser.id = :releaserId )");
        } else {
            hql.append(" and t.releaser.id = :releaserId ");
        }
        if (state != -1) {
            hql.append(" and t.state = false ");
        }
        Query query = getSession().createQuery(hql.toString());
        query.setParameter("logicGroupId", logicGroupId);
        if (taskIds.size() > 0) {
            query.setParameterList("taskIds", taskIds);
        }
        query.setParameter("releaserId", releaserId);
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public void deleteTask(Integer taskId) {
        String hql = " delete from Task where id = :taskId";
        Query query = getSession().createQuery(hql);
        query.setParameter("taskId", taskId);
        query.executeUpdate();
    }

    @Override
    public void closeOrOpenTask(Integer taskId, boolean state) {
        String hql = " update Task set state = :state where id = :taskId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("taskId", taskId);
        query.setParameter("state", state);
        query.executeUpdate();
    }

    @Override
    public void updateTask(Integer completeStatus, int state, int taskId) {
        StringBuilder sql = new StringBuilder("UPDATE t_task SET ");
        sql.append(" state= :state ");
        if (completeStatus != null) {
            sql.append(" ,completeStatus = :completeStatus ");
        }
        sql.append(" where id = :taskId");
        SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("state", state);
        if (completeStatus != null) {
            sqlQuery.setParameter("completeStatus", completeStatus);
        }
        sqlQuery.setParameter("taskId", taskId);
        sqlQuery.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findTaskByDesignee(Integer designee) {
        String sql = "Select td.taskId from t_task_designee td where td.designee = :designee";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("designee", designee);
        return sqlQuery.list();
    }

    @Override
    public void addTaskDesignees(Integer taskId, List<Integer> designees) {
        String sql = " INSERT INTO t_task_designee (taskId,designee) VALUES(:taskId,:designee)";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("taskId", taskId);
        for (Integer designee : designees) {
            sqlQuery.setParameter("designee", designee);
            sqlQuery.executeUpdate();
        }
    }

    @Override
    public void deleteTaskDesignees(Integer taskId) {
        String sql = "DELETE  FROM t_task_designee WHERE taskId = :taskId ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("taskId", taskId);
        sqlQuery.executeUpdate();
    }

}
