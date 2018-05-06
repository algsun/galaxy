package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Appraisal;

/**
 * 文物鉴定信息dao
 *
 * @author xubaoji
 * @date 2013-5-16
 * @check 2013-06-05 zhangpeng svn:4075
 */
public interface AppraisalDao extends BaseDao<Appraisal> {

    /**
     * 根据文物id 查询最近一条 文物鉴定信息
     *
     * @param relicId 文物id
     * @return Appraisal 文物鉴定信息对象
     * @author 许保吉
     * @date 2013-5-16
     */
    public Appraisal findLatestAppraisal(Integer relicId);

}
