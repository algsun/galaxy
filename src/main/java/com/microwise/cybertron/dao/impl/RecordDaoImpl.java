package com.microwise.cybertron.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Record;
import com.microwise.cybertron.dao.RecordDao;
import com.microwise.cybertron.sys.Cybertron;
import com.microwise.cybertron.sys.CybertronBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 文档Dao 实现
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@Beans.Dao
@Cybertron
public class RecordDaoImpl extends CybertronBaseDao<Record> implements RecordDao {
    public RecordDaoImpl() {
        super(Record.class);
    }

    @Override
    public List<Record> findRecordList(String siteId, int volumeCode) {
        String hql = "From Record r Where r.siteId = ? "
                + "And (r.volumeCode = ? OR r.volumeCode IN (SELECT v.volumeCode FROM Volume v WHERE v.parentCode=?)) order by r.volumeCode, r.recordCode";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, siteId);
        query.setParameter(1, volumeCode);
        query.setParameter(2, volumeCode);
        return query.list();
    }

    @Override
    public Record findRecordById(String id) {
        String hql = "From Record r left join fetch r.attachments a Where r.uuid = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, id);
        return (Record)query.uniqueResult();
    }

    @Override
    public void addRecord(Record record) {
        save(record);
    }

    @Override
    public void deleteRecord(String recordUuid) {
        delete(findById(recordUuid));
    }

    @Override
    public boolean isRecordCodeExist(String recordCode,String siteId) {
        String hql = "From Record r Where r.siteId = ? "
                + "And r.recordCode = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, siteId);
        query.setParameter(1, recordCode);
        return query.list().isEmpty();
    }

    @Override
    public void updateRecord(Record record) {
        update(record);
    }
}
