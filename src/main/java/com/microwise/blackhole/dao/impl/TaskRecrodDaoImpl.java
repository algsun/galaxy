package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.TaskRecord;
import com.microwise.blackhole.dao.TaskRecordDao;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.sys.Phoenix;
import org.hibernate.Query;

import java.util.List;

/**
 * 任务回复记录 dao 实现
 *
 * @author xubaoji
 * @date 2013-6-8
 * @check @duan.qixin 2013年7月29日 #4661
 */
@Dao
@Phoenix
public class TaskRecrodDaoImpl extends BlackholeBaseDao<TaskRecord> implements TaskRecordDao {

    public TaskRecrodDaoImpl() {
        super(TaskRecord.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TaskRecord> findTaskRecordByTaskId(int taskId) {
        String hql = " from TaskRecord tr left outer join fetch tr.replier left outer join fetch tr.task where tr.task.id = :taskId order by tr.recordDate ";
        Query query = getSession().createQuery(hql);
        query.setParameter("taskId", taskId);
        return query.list();
    }

    @Override
    public void deleteTaskRecordByTaskId(int taskId) {
        String hql = " delete from TaskRecord  where task.id = :taskId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("taskId", taskId);
        query.executeUpdate();
    }

}
