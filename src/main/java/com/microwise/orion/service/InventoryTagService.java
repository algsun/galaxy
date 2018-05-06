package com.microwise.orion.service;

import java.util.List;

/**
 * 盘点扫描 标签数据service
 *
 * @author xubaoji
 * @date 2013-5-27
 */
public interface InventoryTagService {

    /**
     * 批量添加盘点扫描标签数据
     *
     * @param siteId      站点编号
     * @param relicIdList 文物id列表
     * @return void
     * @author 许保吉
     * @date 2013-5-27
     */
    public void addInventoryTags(String siteId, List<Integer> relicIdList);


}
