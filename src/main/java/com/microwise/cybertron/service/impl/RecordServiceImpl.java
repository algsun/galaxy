package com.microwise.cybertron.service.impl;

import com.microwise.blackhole.dao.UserDao;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Record;
import com.microwise.cybertron.dao.RecordDao;
import com.microwise.cybertron.service.RecordService;
import com.microwise.cybertron.sys.Cybertron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 档案 Service 实现
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@Beans.Service
@Transactional
@Cybertron
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDao recordDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void addRecord(Record record) {
        recordDao.addRecord(record);
    }

    @Override
    public void deleteRecord(String recordUuid) {
        recordDao.deleteRecord(recordUuid);
    }

    @Override
    public Record findRecordById(String id) {
        return recordDao.findRecordById(id);
    }

    @Override
    public List<Record> findRecordList(String siteId, int volumeCode) {
        return recordDao.findRecordList(siteId, volumeCode);
    }

    @Override
    public boolean isRecordCodeExist(String recordCode, String siteId) {
        return recordDao.isRecordCodeExist(recordCode, siteId);
    }

    @Override
    public void updateRecord(Record record) {
        recordDao.updateRecord(record);
    }
}
