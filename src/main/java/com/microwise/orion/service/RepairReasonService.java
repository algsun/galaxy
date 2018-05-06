package com.microwise.orion.service;

import com.microwise.orion.bean.RepairReason;

import java.util.List;

/**
 * @author 王耕
 * @date 15-9-9
 */
public interface RepairReasonService {
    /**
     * 新增或修改原因
     *
     * @param repairReason 原因实体
     */
    public void saveOrUpdate(RepairReason repairReason);

    /**
     * 根据ID删除原因
     *
     * @param id 原因id
     */
    public void deleteRepairReason(int id);

    /**
     * 查询所有原因
     *
     * @return 原因集合
     * @Param siteId 站点编号
     */
    public List<RepairReason> findAll(String siteId);

    /**
     * 根据ID查询原因
     *
     * @param id 原因ID
     * @return 原因实体
     */
    public RepairReason findReasonById(int id);

    public RepairReason findReasonByReason(String reason,int id);
}
