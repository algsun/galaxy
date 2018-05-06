package com.microwise.orion.dao;

import com.microwise.orion.bean.RepairLog;

import java.util.List;

/**
 * 修复记录表dao
 *
 * @author 王耕
 * @date 15-10-9
 */
public interface RepairLogDao {
    /**
     * 查询修复记录的修复日志
     *
     * @param repairRecordId 修复记录ID
     * @return 日志集合
     */
    public List<RepairLog> findRepairLogs(int repairRecordId);

    /**
     * 保存修复记录日志
     *
     * @param repairLog 修复日志实体
     */
    public void saveRepairLog(RepairLog repairLog);
}
