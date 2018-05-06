package com.microwise.orion.dao;

import com.microwise.orion.bean.Situation;

/**
 * 修复情况DAO
 *
 * @author 王耕
 * @date 15-9-24
 */
public interface SituationDao {
    /**
     * 根据修复记录id查询修复情况
     *
     * @param repairRecordId 修复记录id
     * @return 修复情况实体
     */
    public Situation findByRepairRecordId(int repairRecordId);

    /**
     * 保存修复记录
     *
     * @param situation 修复记录实体
     */
    public void saveOrUpdate(Situation situation);
}
