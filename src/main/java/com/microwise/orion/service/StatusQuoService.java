package com.microwise.orion.service;

import com.microwise.orion.bean.StatusQuo;

/**
 * 保存现状service
 *
 * @author 王耕
 * @date 15-9-28
 */
public interface StatusQuoService {
    /**
     * 查询某文物最近一条保存现状
     *
     * @param relicId 文物ID
     * @return 保存现状集合
     */
    public StatusQuo findStatusQuos(int relicId);

    /**
     * 根据保存现状id查询实体
     *
     * @param id 现状id
     * @return 现状实体
     */
    public StatusQuo findById(int id);

    /**
     * 保存修复记录
     *
     * @param statusQuo 修复记录
     */
    public void saveOrUpdateStatusQuo(StatusQuo statusQuo);

    public void updateStatusQuo(StatusQuo statusQuo);

    /**
     * 删除现状
     *
     * @param statusQuo 实体
     * @author liuzhu
     */
    public void deleteStatusQuo(StatusQuo statusQuo);
}
