package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.RepairRecord;

import java.util.List;

/**
 * Created by Administrator on 15-9-14.
 */
public interface RepairRecordDao extends BaseDao<RepairRecord> {

    public RepairRecord lastRepairRecord();

    public void saveOrUpdate(RepairRecord repairRecord);

    public List<RepairRecord> findBySiteId(String siteId);

    public List<RepairRecord> findRepairRecords(int index,int pageCount,String siteId);

    /**
     * 查询任务记录（携带因由）
     * @param id
     * @return
     */
    public RepairRecord findRepairRecordById(int id);
}
