package com.microwise.cybertron.service;

import com.microwise.cybertron.bean.Record;

import java.util.List;

/**
 * 档案 Service
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public interface RecordService {
    /**
     * 查询站点下指定卷的档案列表
     *
     * @param siteId     站点ID
     * @param volumeCode 卷代码
     * @return 档案集合
     */
    public List<Record> findRecordList(String siteId, int volumeCode);

    /**
     * 查询档案信息
     *
     * @param id 档案ID(UUID)
     * @return 档案
     */
    public Record findRecordById(String id);

    /**
     * 添加档案
     *
     * @param record
     */
    public void addRecord(Record record);

    /**
     * 编辑档案
     *
     * @param record
     */
    public void updateRecord(Record record);

    /**
     * 删除档案
     *
     * @param recordUuid 文档ID
     */
    public void deleteRecord(String recordUuid);

    /**
     * 判断档号是否存在
     *
     * @param recordCode
     * @return
     */
    public boolean isRecordCodeExist(String recordCode, String siteId);
}
