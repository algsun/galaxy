package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.StatusQuo;

/**
 * 文物现状dao
 *
 * @author xubaoji
 * @date 2013-5-16
 * @check 2013-06-05 zhangpeng svn:4075
 */
public interface StatusQuoDao extends BaseDao<StatusQuo> {

    /**
     * 根据文物总登记号 查询最近一条 文物现状信息
     *
     * @param relicId 文物id
     * @return StatusQuo 文物现状对象
     * @author 许保吉
     * @date 2013-5-16
     */
    public StatusQuo findLatestStatusQuoDao(Integer relicId);

    /**
     * 保存修复记录
     *
     * @param statusQuo 修复记录实体
     * @author 王耕
     * @date 2015-9-28
     */
    public void saveOrUpdateStatusQuo(StatusQuo statusQuo);

    /**
     * 修改现状
     * @param statusQuo 修改条件
     */
    public int updateStatusQuo(StatusQuo statusQuo);

}
